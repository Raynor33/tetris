package com.wjrb.tetris

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.Test

class BoardTest {

    private val shape = Shape(
        1,
        listOfNotNull(
            Point.of(2, 0),
            Point.of(3, 0),
            Point.of(4, 0),
            Point.of(3, 1)
        )
    )
    private val nextShape = { shape }

    @Test
    fun shouldStartWithAShape() {
        val board = Board.of(nextShape)
        assertThat(board.points(), equalTo(shape.points.toSet()))
    }

    @Test
    fun shouldMoveTheShapeLeft() {
        val board = Board
            .of(nextShape)
            .left()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(1, 0),
            Point.of(2, 0),
            Point.of(3, 0),
            Point.of(2, 1)
        )))
    }

    @Test
    fun shouldMoveTheShapeRight() {
        val board = Board
            .of(nextShape)
            .right()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(3, 0),
            Point.of(4, 0),
            Point.of(5, 0),
            Point.of(4, 1)
        )))
    }

    @Test
    fun shouldMoveTheShapeDown() {
        val board = Board
            .of(nextShape)
            .down()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(2, 1),
            Point.of(3, 1),
            Point.of(4, 1),
            Point.of(3, 2)
        )))
    }

    @Test
    fun shouldTurnTheShape() {
        val board = Board
            .of(nextShape)
            .down()
            ?.turn()
           assertThat(board?.points(), equalTo(setOf(
            Point.of(3, 0),
            Point.of(3, 1),
            Point.of(3, 2),
            Point.of(2, 1)
        )))
    }

    @Test
    fun shouldDropTheShapeDownAndAddNewShape() {
        val board = Board
            .of(nextShape)
            .drop()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(2, 18),
            Point.of(3, 18),
            Point.of(4, 18),
            Point.of(2, 19)
        ) + shape))
    }

    @Test
    fun shouldNotTurnTheShapeOffTheBoard() {
        val board = Board
            .of(nextShape)
            .turn()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(2, 0),
            Point.of(3, 0),
            Point.of(4, 0),
            Point.of(3, 1)
        )))
    }

    @Test
    fun shouldNotMoveTheShapeOffTheBoard() {
        val board = Board
            .of(nextShape)
            .left()
            ?.left()
            ?.left()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(0, 0),
            Point.of(1, 0),
            Point.of(2, 0),
            Point.of(1, 1)
        )))
    }

    @Test
    fun shouldDropTheShapeDownOntoAnotherShape() {
        val board = Board
            .of(nextShape)
            .drop()
            ?.drop()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(2, 16),
            Point.of(3, 16),
            Point.of(4, 16),
            Point.of(2, 17),
            Point.of(2, 18),
            Point.of(3, 18),
            Point.of(4, 18),
            Point.of(2, 19)
        ) + shape))
    }

    @Test
    fun shouldCompleteALine() {
        val board = Board
            .of(nextShape)
            .left()?.left()
            ?.drop()
            ?.right()
            ?.drop()
            ?.right()?.right()?.right()?.right()
            ?.drop()
            ?.down()?.turn()?.right()?.right()?.right()?.right()?.right()?.right()
            ?.drop()
        assertThat(board?.points(), equalTo(setOf(
            Point.of(1, 19),
            Point.of(4, 19),
            Point.of(7, 19),
            Point.of(8, 18),
            Point.of(9, 18),
            Point.of(9, 17)
        ) + shape))
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
        assertThat(board, nullValue())
    }
}