package com.wjrb.tetris.bot

import com.wjrb.tetris.Board
import com.wjrb.tetris.Point
import com.wjrb.tetris.Shape
import com.wjrb.tetris.SuccessBoard
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class ShapeOutcomeTest {

    @Test
    fun testShapeOutcomes() {
        val board: Board = Board.of { Shape.T }
        val outcomes = ShapeOutcome.all(board)

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
            .map {
                it.deadPoints
                    .mapIndexed { x, ys ->
                        ys.mapIndexed { y, present ->
                            if (present) Point(x, y) else null
                        }
                    }
                    .flatten()
                    .filterNotNull()
                    .toSet()
            }
            .toSet()

        assertThat(actualOutcomes, equalTo(expectedOutcomes))

        outcomes.forEach {
            val actualResult = it.instructions.fold(board, { b, instruction -> instruction.applyTo(b) }) as SuccessBoard
            assertThat(actualResult.deadPoints, equalTo(it.deadPoints))
        }
    }
}