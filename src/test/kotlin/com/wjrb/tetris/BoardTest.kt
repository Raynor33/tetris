package com.wjrb.tetris

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class BoardTest {

    private val shape = Shape.T
    private val nextShape = { shape }

    @Test
    fun shouldStartWithAShape() {
        val board = Board.of(nextShape)
        checkPointsAndRender(
            board, setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1)
            )
        )
    }

    @Test
    fun shouldMoveTheShapeLeft() {
        val board = Board
            .of(nextShape)
            .left()
        checkPointsAndRender(
            board,
            setOf(
                Point(3, 0),
                Point(2, 1),
                Point(3, 1),
                Point(4, 1)
            )
        )
    }

    @Test
    fun shouldMoveTheShapeRight() {
        val board = Board
            .of(nextShape)
            .right()
        checkPointsAndRender(
            board,
            setOf(
                Point(5, 0),
                Point(4, 1),
                Point(5, 1),
                Point(6, 1)
            )
        )
    }

    @Test
    fun shouldMoveTheShapeRightThenRight() {
        val board = Board
            .of(nextShape)
            .left()
            .right()
        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1)
            )
        )
    }

    @Test
    fun shouldMoveTheShapeDown() {
        val board = Board
            .of(nextShape)
            .down()
        checkPointsAndRender(
            board,
            setOf(
                Point(4, 1),
                Point(3, 2),
                Point(4, 2),
                Point(5, 2)
            )
        )
    }

    @Test
    fun shouldTurnTheShape() {
        val board = Board
            .of(nextShape)
            .rotate()
        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(4, 1),
                Point(4, 2),
                Point(5, 1)
            )
        )
    }

    @Test
    fun shouldDropTheShapeDownAndAddNewShape() {
        val board = Board
            .of(nextShape)
            .drop()
        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1),
                Point(4, 18),
                Point(3, 19),
                Point(4, 19),
                Point(5, 19)
            )
        )
    }

    @Test
    fun shouldNotTurnTheShapeOffTheBoard() {
        val board = Board
            .of(nextShape)
            .rotate()
            .left()
            .left()
            .left()
            .left()
            .rotate()
        checkPointsAndRender(
            board,
            setOf(
                Point(0, 0),
                Point(0, 1),
                Point(0, 2),
                Point(1, 1)
            )
        )
    }

    @Test
    fun shouldNotMoveTheShapeOffTheBoard() {
        val board = Board
            .of(nextShape)
            .rotate()
            .left()
            .left()
            .left()
            .left()
            .left()
        checkPointsAndRender(
            board,
            setOf(
                Point(0, 0),
                Point(0, 1),
                Point(0, 2),
                Point(1, 1)
            )
        )
    }

    @Test
    fun shouldDropTheShapeDownOntoAnotherShape() {
        val board = Board
            .of(nextShape)
            .drop()
            .drop()
        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1),
                Point(4, 16),
                Point(3, 17),
                Point(4, 17),
                Point(5, 17),
                Point(4, 18),
                Point(3, 19),
                Point(4, 19),
                Point(5, 19)
            )
        )
    }

    @Test
    fun shouldDropTheShapeDownOntoASuspendedBlock() {
        val board = Board
            .of(nextShape)
            .rotate()
            .rotate()
            .drop()
            .right()
            .right()
            .drop()
        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1),
                Point(6, 16),
                Point(5, 17),
                Point(6, 17),
                Point(7, 17),
                Point(3, 18),
                Point(4, 18),
                Point(5, 18),
                Point(4, 19)
            )
        )
    }

    @Test
    fun shouldCompleteALine() {
        val board = Board
            .of(nextShape)
            .left().left().left().drop()
            .drop()
            .right().right().right().drop()
            .rotate().rotate().rotate().right().right().right().right().right().drop()

        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1),
                Point(1, 19),
                Point(4, 19),
                Point(7, 19),
                Point(8, 19),
                Point(9, 19),
                Point(9, 18)
            )
        )
    }

    @Test
    fun shouldCompleteMultipleLines() {
        val board = Board
            .of { Shape.O }
            .left().left().left().left().drop()
            .left().left().drop()
            .drop()
            .right().right().drop()
            .right().right().right().right().drop()
        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(5, 0),
                Point(4, 1),
                Point(5, 1)
            )
        )
    }

    @Test
    fun shouldCompleteASuspendedLine() {
        val board = Board
            .of(nextShape)
            .rotate().rotate().left().left().left().drop()
            .rotate().rotate().drop()
            .rotate().rotate().right().right().right().drop()
            .rotate().rotate().rotate().right().right().right().right().right().drop()

        checkPointsAndRender(
            board,
            setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1),
                Point(1, 19),
                Point(4, 19),
                Point(7, 19),
                Point(8, 18),
                Point(9, 18),
                Point(9, 17)
            )
        )
    }

    @Test
    fun shouldEndTheGame() {
        val board = Board
            .of(nextShape)
            .drop()
            .drop()
            .drop()
            .drop()
            .drop()
            .drop()
            .drop()
            .drop()
            .drop()
            .drop()
            .drop()
        assertThat(board.complete(), equalTo(true))
    }

    @Test
    fun shouldBeImmutableWhenMoving() {
        val board = Board
            .of(nextShape)
        board
            .left()
        checkPointsAndRender(
            board, setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1)
            )
        )
    }

    @Test
    fun shouldBeImmutableWhenAddingDeadPoints() {
        val board = Board
            .of(nextShape)
        board
            .drop()
        checkPointsAndRender(
            board, setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1)
            )
        )
    }

    @Test
    fun shouldBeImmutableWhenCompletingLines() {
        val board = Board
            .of(nextShape)
        board
            .left().left().left().drop()
            .drop()
            .right().right().right().drop()
            .rotate().rotate().rotate().right().right().right().right().right().drop()
        checkPointsAndRender(
            board, setOf(
                Point(4, 0),
                Point(3, 1),
                Point(4, 1),
                Point(5, 1)
            )
        )
    }

    @Test
    fun shouldBuildUpPossibleOutcomes() {
        val board: Board = Board.of { Shape.T }
        val outcomes = board.possibleOutcomes().toList()

        val expectedOutcomes = setOf(
            setOf(Point(0, 19), Point(1, 19), Point(2, 19), Point(1, 18)),
            setOf(Point(1, 19), Point(2, 19), Point(3, 19), Point(2, 18)),
            setOf(Point(2, 19), Point(3, 19), Point(4, 19), Point(3, 18)),
            setOf(Point(3, 19), Point(4, 19), Point(5, 19), Point(4, 18)),
            setOf(Point(4, 19), Point(5, 19), Point(6, 19), Point(5, 18)),
            setOf(Point(5, 19), Point(6, 19), Point(7, 19), Point(6, 18)),
            setOf(Point(6, 19), Point(7, 19), Point(8, 19), Point(7, 18)),
            setOf(Point(7, 19), Point(8, 19), Point(9, 19), Point(8, 18)),

            setOf(Point(0, 19), Point(0, 18), Point(0, 17), Point(1, 18)),
            setOf(Point(1, 19), Point(1, 18), Point(1, 17), Point(2, 18)),
            setOf(Point(2, 19), Point(2, 18), Point(2, 17), Point(3, 18)),
            setOf(Point(3, 19), Point(3, 18), Point(3, 17), Point(4, 18)),
            setOf(Point(4, 19), Point(4, 18), Point(4, 17), Point(5, 18)),
            setOf(Point(5, 19), Point(5, 18), Point(5, 17), Point(6, 18)),
            setOf(Point(6, 19), Point(6, 18), Point(6, 17), Point(7, 18)),
            setOf(Point(7, 19), Point(7, 18), Point(7, 17), Point(8, 18)),
            setOf(Point(8, 19), Point(8, 18), Point(8, 17), Point(9, 18)),

            setOf(Point(0, 18), Point(1, 18), Point(2, 18), Point(1, 19)),
            setOf(Point(1, 18), Point(2, 18), Point(3, 18), Point(2, 19)),
            setOf(Point(2, 18), Point(3, 18), Point(4, 18), Point(3, 19)),
            setOf(Point(3, 18), Point(4, 18), Point(5, 18), Point(4, 19)),
            setOf(Point(4, 18), Point(5, 18), Point(6, 18), Point(5, 19)),
            setOf(Point(5, 18), Point(6, 18), Point(7, 18), Point(6, 19)),
            setOf(Point(6, 18), Point(7, 18), Point(8, 18), Point(7, 19)),
            setOf(Point(7, 18), Point(8, 18), Point(9, 18), Point(8, 19)),

            setOf(Point(1, 19), Point(1, 18), Point(1, 17), Point(0, 18)),
            setOf(Point(2, 19), Point(2, 18), Point(2, 17), Point(1, 18)),
            setOf(Point(3, 19), Point(3, 18), Point(3, 17), Point(2, 18)),
            setOf(Point(4, 19), Point(4, 18), Point(4, 17), Point(3, 18)),
            setOf(Point(5, 19), Point(5, 18), Point(5, 17), Point(4, 18)),
            setOf(Point(6, 19), Point(6, 18), Point(6, 17), Point(5, 18)),
            setOf(Point(7, 19), Point(7, 18), Point(7, 17), Point(6, 18)),
            setOf(Point(8, 19), Point(8, 18), Point(8, 17), Point(7, 18)),
            setOf(Point(9, 19), Point(9, 18), Point(9, 17), Point(8, 18))
        )

        val actualOutcomes = outcomes
            .map { outcome ->
                (0 until 10)
                    .flatMap { x ->
                        (0 until 20)
                            .map { y -> if (outcome.filledAt(x, y)) Point(x, y) else null }
                    }
                    .filterNotNull()
                    .toSet()
            }
            .toSet()

        assertThat(actualOutcomes, equalTo(expectedOutcomes))

        outcomes.forEach { outcome ->
            val actualResult = outcome.instructions().fold(board, { b, instruction -> instruction.applyTo(b) })
            checkPointsAndRender(actualResult, outcome)
        }
    }

    private fun checkPointsAndRender(board: Board, shapeOutcome: ShapeOutcome) {
        checkPointsAndRender(
            board,
            (0 until 10).flatMap { x ->
                (0 until 20).map { y ->
                    if (shapeOutcome.filledAt(x, y)) Point(
                        x,
                        y
                    ) else null
                }
            }
                .filterNotNull()
                .toSet() + Shape.T.rotate(0)
        )
    }

    private fun checkPointsAndRender(board: Board, expectedPoints: Set<Point>) {
        var correct = true
        println(" ----------")
        (0 until 20).forEach { y ->
            print("|")
            (0 until 10).forEach { x ->
                if (board.filledAt(x, y)) {
                    if (expectedPoints.contains(Point(x, y))) {
                        print("*")
                    } else {
                        print("a")
                        correct = false
                    }

                } else {
                    if (expectedPoints.contains(Point(x, y))) {
                        print("e")
                        correct = false
                    } else {
                        print(" ")
                    }
                }
            }
            println("|")
        }
        println(" ----------")
        assertThat(correct, equalTo(true))
        assertThat(board.complete(), equalTo(false))
    }
}