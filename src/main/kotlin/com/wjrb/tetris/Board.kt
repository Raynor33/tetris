package com.wjrb.tetris

class Board(
    private val nextShape: () -> Shape,
    private val currentShape: Shape = nextShape.invoke(),
    private val deadPoints: Array<BooleanArray> = Array(10) { BooleanArray(20) },
    private val rotations: Int = 0,
    private val currentShapeXTranslation: Int = 0,
    private val currentShapeYTranslation: Int = 0
) {
    companion object Factory {
        fun of(nextShape: () -> Shape) =
            Board(nextShape)
    }

    fun filledAt(x: Int, y: Int) = deadPoints[x][y] || currentShape
        .rotate(rotations)
        .find { point ->
            point.x + currentShapeXTranslation == x && point.y + currentShapeYTranslation == y
        }
        .let { it != null }

    fun left() = process(0, -1, 0)

    fun right() = process(0, 1, 0)

    fun rotate() = process(1, 0, 0)

    fun down() = process(0, 0, 1)

    fun drop() = process(0, 0, dropDistance(0, 0))

    fun complete() = !validPlacement(0, 0, 0)

    fun possibleOutcomes(): Iterable<ShapeOutcome> = Iterable {
        object : Iterator<ShapeOutcome> {
            var rotationsDiff = 0
            var xDiff = currentShapeXTranslation - currentShape.leftMostPointX(rotations + rotationsDiff)
            var hasNext = true

            override fun hasNext(): Boolean = hasNext

            override fun next(): ShapeOutcome = object : ShapeOutcome(rotationsDiff, xDiff) {
                val newDeadPoints = newDeadPoints(
                    rotations + rotationsDiff,
                    currentShapeXTranslation + xDiff,
                    dropDistance(rotationsDiff, xDiff)
                )

                init {
                    if (currentShape.rightMostPointX(rotations + rotationsDiff) + xDiff == 9) {
                        if (rotationsDiff == 3) {
                            hasNext = false
                        } else {
                            rotationsDiff += 1
                            xDiff = currentShapeXTranslation - currentShape.leftMostPointX(rotations + rotationsDiff)
                        }
                    } else {
                        xDiff += 1
                    }
                }

                override fun filledAt(x: Int, y: Int): Boolean {
                    return newDeadPoints[x][y]
                }
            }
        }
    }

    private fun process(rotationsDiff: Int, xDiff: Int, yDiff: Int): Board =
        if (complete() || !validPlacement(rotationsDiff, xDiff, yDiff)) {
            this
        } else {
            if (!validPlacement(rotationsDiff, xDiff, yDiff + 1)) {
                val newDeadPoints = newDeadPoints(
                    rotations + rotationsDiff,
                    currentShapeXTranslation + xDiff,
                    currentShapeYTranslation + yDiff
                )
                Board(nextShape, nextShape.invoke(), newDeadPoints, 0, 0, 0)
            } else {
                Board(
                    nextShape,
                    currentShape,
                    deadPoints,
                    rotations + rotationsDiff,
                    currentShapeXTranslation + xDiff,
                    currentShapeYTranslation + yDiff
                )
            }
        }

    private fun dropDistance(rotationsDiff: Int, xDiff: Int): Int =
        (0 until 19)
            .find { y -> !validPlacement(rotationsDiff, xDiff, y + 1) }
            ?: 19

    private fun newDeadPoints(totalRotations: Int, totalXTranslation: Int, totalYTranslation: Int): Array<BooleanArray> {
        val newDeadPoints = deadPoints.map { it.clone() }.toTypedArray()
        var lowestRow = 0
        currentShape.rotate(totalRotations).forEach { point ->
            val x = point.x + totalXTranslation
            val y = point.y + totalYTranslation
            newDeadPoints[x][y] = true
            lowestRow = if (y > lowestRow) y else lowestRow
        }
        var totalComplete = 0
        (lowestRow downTo 0).forEach { y ->
            val complete = (0 until 10).all { x -> newDeadPoints[x][y] }
            if (complete) {
                totalComplete += 1
            } else {
                (0 until 10).forEach { x -> newDeadPoints[x][y + totalComplete] = newDeadPoints[x][y] }
            }
        }
        return newDeadPoints
    }

    private fun validPlacement(rotationsDiff: Int, xDiff: Int, yDiff: Int) =
        currentShape
            .rotate(rotations + rotationsDiff)
            .all { point ->
                val newX = point.x + currentShapeXTranslation + xDiff
                val newY = point.y + currentShapeYTranslation + yDiff
                newX >= 0 && newX < 10 && newY >= 0 && newY < 20 && !deadPoints[newX][newY]
            }

}

