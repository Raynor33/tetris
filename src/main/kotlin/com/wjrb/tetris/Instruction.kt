package com.wjrb.tetris

import com.wjrb.tetris.Board

enum class Instruction {
    LEFT {
        override fun applyTo(board: Board) = board.left()
    },
    RIGHT {
        override fun applyTo(board: Board) = board.right()
    },
    ROTATE {
        override fun applyTo(board: Board) = board.rotate()
    },
    DOWN {
        override fun applyTo(board: Board) = board.down()
    },
    DROP {
        override fun applyTo(board: Board) = board.drop()
    };

    abstract fun applyTo(board: Board): Board
}