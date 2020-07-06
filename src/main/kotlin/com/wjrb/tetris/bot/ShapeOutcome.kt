package com.wjrb.tetris.bot

import com.wjrb.tetris.Board

data class ShapeOutcome(val instructions: List<Instruction>, val deadPoints: Array<BooleanArray>) {
    companion object Enumerator {
        fun all(board: Board) = setOf<ShapeOutcome>()
    }
}