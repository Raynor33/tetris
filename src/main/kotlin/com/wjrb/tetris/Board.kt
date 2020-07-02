package com.wjrb.tetris

class Board(private val nextShape: () -> Shape, private val shape: Shape, private val fixedPoints: Set<Point>) {
    companion object Factory {
        fun of(nextShape: () -> Shape) = Board(nextShape, nextShape.invoke(), setOf())
    }

    fun points(): Set<Point> = fixedPoints + shape.points

    fun left(): Board? = process {
        shape.points.mapNotNull {
            it.shift(-1, 0)
        }
    }

    fun right(): Board? = process {
        shape.points.mapNotNull {
            it.shift(1, 0)
        }
    }

    fun turn(): Board? = process {
        shape.points.mapNotNull {
            it.rotateAbout(shape.points[shape.centreIndex])
        }
    }

    fun down(): Board? = process {
        shape.points.mapNotNull {
            it.shift(0, 1)
        }
    }

    fun drop(): Board? = process {
        throw UnsupportedOperationException()
    }

    private fun process(newPointsFunction: () -> List<Point>): Board? =
        if (shape.points.intersect(fixedPoints).isNotEmpty()) null else {
            val newPoints = newPointsFunction()
            if (newPoints.size < shape.points.size || newPoints.intersect(fixedPoints).isNotEmpty()) {
                this
            } else {
                Board(nextShape, Shape(shape.centreIndex, newPoints), fixedPoints)
            }
        }
}

