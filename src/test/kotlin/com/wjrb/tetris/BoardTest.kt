package com.wjrb.tetris

import com.wjrb.tetris.Instruction.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class BoardTest {

    private val shape = Shape(
        Point(3, 0),
        setOf(
            Point(2, 0),
            Point(3, 0),
            Point(4, 0),
            Point(3, 1)
        )
    )

    @Test
    fun shouldStartWithAShape() {
        val board = Board.make { shape }
        assertThat(board.points(), equalTo(shape.points))
    }

    @Test
    fun shouldMoveTheShapeLeft() {
        val board = Board
            .make { shape }
            .send(LEFT)
        assertThat(board.points(), equalTo(setOf(
            Point(1, 0),
            Point(2, 0),
            Point(3, 0),
            Point(2, 1)
        )))
    }

    @Test
    fun shouldMoveTheShapeRight() {
        val board = Board
            .make { shape }
            .send(RIGHT)
        assertThat(board.points(), equalTo(setOf(
            Point(3, 0),
            Point(4, 0),
            Point(5, 0),
            Point(4, 1)
        )))
    }

    @Test
    fun shouldMoveTheShapeDown() {
        val board = Board
            .make { shape }
            .send(DOWN)
        assertThat(board.points(), equalTo(setOf(
            Point(2, 1),
            Point(3, 1),
            Point(4, 1),
            Point(3, 2)
        )))
    }

    @Test
    fun shouldTurnTheShapeDown() {
        val board = Board
            .make { shape }
            .send(DOWN)
            .send(TURN)
        assertThat(board.points(), equalTo(setOf(
            Point(3, 0),
            Point(3, 1),
            Point(3, 2),
            Point(2, 1)
        )))
    }

    @Test
    fun shouldDropTheShapeDown() {
        val board = Board
            .make { shape }
            .send(DROP)
        assertThat(board.points(), equalTo(setOf(
            Point(2, 18),
            Point(3, 18),
            Point(4, 18),
            Point(2, 19)
        )))
    }

    @Test
    fun shouldNotTurnTheShapeOffTheBoard() {
        val board = Board
            .make { shape }
            .send(TURN)
        assertThat(board.points(), equalTo(setOf(
            Point(2, 0),
            Point(3, 0),
            Point(4, 0),
            Point(3, 1)
        )))
    }

    @Test
    fun shouldNotMoveTheShapeOffTheBoard() {
        val board = Board
            .make { shape }
            .send(LEFT)
            .send(LEFT)
            .send(LEFT)
        assertThat(board.points(), equalTo(setOf(
            Point(0, 0),
            Point(1, 0),
            Point(2, 0),
            Point(1, 1)
        )))
    }
}