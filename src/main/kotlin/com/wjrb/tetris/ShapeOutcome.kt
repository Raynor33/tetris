package com.wjrb.tetris

abstract class ShapeOutcome(private val rotationsDiff: Int, private val xDiff: Int) {

    fun instructions() =
        (0 until rotationsDiff).map { Instruction.ROTATE } +
                (if (xDiff < 0) (0 until -xDiff).map { Instruction.LEFT }
                else if (xDiff > 0) (0 until xDiff).map { Instruction.RIGHT }
                else listOf()) +
                Instruction.DROP

    abstract fun filledAt(x: Int, y: Int) : Boolean
}