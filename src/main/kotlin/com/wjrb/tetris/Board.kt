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

    fun filledAt(x: Int, y: Int): Boolean = deadPoints[x][y] || currentShape
        .rotate(rotations)
        .find { point ->
            point.x + currentShapeXTranslation == x && point.y + currentShapeYTranslation == y
        }
        .let { it != null }

    fun left(): Board? = process(0, -1, 0)

    fun right(): Board? = process(0, 1, 0)

    fun turn(): Board? = process(1, 0, 0)

    fun down(): Board? = process(0, 0, 1)

    fun drop(): Board? = run {
        val yDiff = currentShape
            .rotate(rotations)
            .map { point ->
                val unavailableRow = (point.y + currentShapeYTranslation until 20)
                    .find { y -> !validPlacement(0, 0, y) }
                    ?: 20
                val possibleDropDistance = unavailableRow - 1 - currentShapeYTranslation
                possibleDropDistance
            }
            .min()
            ?: 0
        process(0, 0, yDiff)
    }

    private fun process(rotationsDiff: Int, xDiff: Int, yDiff: Int): Board? =
        if (!validPlacement(0, 0, 0)) null else {
            if (!validPlacement(rotationsDiff, xDiff, yDiff)) {
                this
            } else {
                if (!validPlacement(0, xDiff, yDiff + 1)) {
                    currentShape.rotate(rotations).forEach{point ->
                        deadPoints[point.x + currentShapeXTranslation + xDiff][point.y + currentShapeYTranslation + yDiff] = true
                    }
                    Board(nextShape, nextShape.invoke(), deadPoints, 0, 0, 0)
                } else {
                    Board(nextShape, currentShape, deadPoints, rotations + rotationsDiff, currentShapeXTranslation + xDiff, currentShapeYTranslation + yDiff)
                }
            }
        }

    private fun validPlacement(rotationsDiff: Int, xDiff: Int, yDiff: Int) =
        currentShape
            .rotate(rotations + rotationsDiff)
            .all { point ->
                val newX = point.x + currentShapeXTranslation + xDiff
                val newY = point.y + currentShapeYTranslation + yDiff
                newX >= 0 && newX < 10 && newY >=0 && newY < 20 && !deadPoints[newX][newY]
            }

}

