package com.wjrb.tetris

import junit.framework.AssertionFailedError
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
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
            ?.right()
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
            .turn()
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
            .turn()
            ?.left()
            ?.left()
            ?.left()
            ?.left()
            ?.turn()
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
            .turn()
            ?.left()
            ?.left()
            ?.left()
            ?.left()
            ?.left()
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
            ?.drop()
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
            .turn()
            ?.turn()
            ?.drop()
            ?.right()
            ?.right()
            ?.drop()
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
            .left()?.left()?.left()?.drop()
            ?.drop()
            ?.right()?.right()?.right()?.drop()
            ?.turn()?.turn()?.turn()?.right()?.right()?.right()?.right()?.right()?.drop()

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
            .of {Shape.O}
            .left()?.left()?.left()?.left()?.drop()
            ?.left()?.left()?.drop()
            ?.drop()
            ?.right()?.right()?.drop()
            ?.right()?.right()?.right()?.right()?.drop()
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
            .turn()?.turn()?.left()?.left()?.left()?.drop()
            ?.turn()?.turn()?.drop()
            ?.turn()?.turn()?.right()?.right()?.right()?.drop()
            ?.turn()?.turn()?.turn()?.right()?.right()?.right()?.right()?.right()?.drop()

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
            ?.drop()
            ?.drop()
            ?.drop()
            ?.drop()
            ?.drop()
            ?.drop()
            ?.drop()
            ?.drop()
            ?.drop()
            ?.drop()
        assertThat(board, nullValue())
    }

    private fun checkPointsAndRender(maybeBoard: Board?, expectedPoints: Set<Point>) {
        val board = maybeBoard ?: throw AssertionFailedError("unexpected game over")
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
    }
}