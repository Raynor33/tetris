package com.wjrb.tetris

import java.lang.UnsupportedOperationException

class Board(private val shape: Shape, private val fixedPoints: Set<Point>) {
    companion object Factory {
        fun make(nextShape: () -> Shape) = Board(nextShape.invoke(), setOf())
    }

    fun points(): Set<Point> = fixedPoints + shape.points

    fun send(instruction: Instruction): Board {
        throw UnsupportedOperationException()
    }
}

