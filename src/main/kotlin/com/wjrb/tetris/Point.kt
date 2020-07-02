package com.wjrb.tetris

data class Point private constructor(val x: Int, val y: Int) {

    companion object PointCache {
        private const val width = 10
        private const val height = 20

        private val points = Array(10) { Array<Point?>(20) { null } }
        fun of(x: Int, y: Int): Point? =
            if (!(x >= 0 && x < width && y >= 0 && y < height)) {
                null
            } else {
                points[x][y]
                    ?.let { point -> point }
                    ?: run {
                        val point = Point(x, y)
                        points[x][y] = point
                        point
                    }
            }

    }

    fun shift(xDiff: Int, yDiff: Int): Point? = of(x + xDiff, y + yDiff)

    fun rotateAbout(point: Point): Point? = of(
        point.x - (y - point.y),
        point.y + (x - point.x)
    )
}