package com.wjrb.tetris

enum class Shape(val rotationStages: List<Set<Point>>) {
    O(listOf(
        setOf(Point(4,0), Point(5,0), Point(4,1), Point(5,1))
    )),
    S(listOf(
        setOf(Point(4,0), Point(5,0), Point(3,1), Point(4,1)),
        setOf(Point(4,0), Point(4,1), Point(5,1), Point(5,2)),
        setOf(Point(4,1), Point(5,1), Point(3,2), Point(4,2)),
        setOf(Point(3,0), Point(3,1), Point(4,1), Point(4,2))
    )),
    Z(listOf(
        setOf(Point(3,0), Point(4,0), Point(4,1), Point(5,1)),
        setOf(Point(5,0), Point(5,1), Point(4,1), Point(4,2)),
        setOf(Point(3,1), Point(4,1), Point(4,2), Point(5,2)),
        setOf(Point(4,0), Point(4,1), Point(3,1), Point(3,2))
    )),
    T(listOf(
        setOf(Point(4, 0), Point(3, 1), Point(4, 1), Point(5, 1)),
        setOf(Point(4, 0), Point(4, 1), Point(4, 2), Point(5, 1)),
        setOf(Point(4, 2), Point(3, 1), Point(4, 1), Point(5, 1)),
        setOf(Point(4, 0), Point(3, 1), Point(4, 1), Point(4, 2))
    )),
    L(listOf(
        setOf(Point(5,0), Point(3, 1), Point(4, 1), Point(5, 1)),
        setOf(Point(4,0), Point(4, 1), Point(4, 2), Point(5, 2)),
        setOf(Point(3,2), Point(3, 1), Point(4, 1), Point(5, 1)),
        setOf(Point(3,0), Point(4, 0), Point(4, 1), Point(4, 2))
    )),
    J(listOf(
        setOf(Point(3,0), Point(3, 1), Point(4, 1), Point(5, 1)),
        setOf(Point(4,0), Point(5, 0), Point(4, 1), Point(4, 2)),
        setOf(Point(5,2), Point(3, 1), Point(4, 1), Point(5, 1)),
        setOf(Point(4,0), Point(3, 2), Point(4, 1), Point(4, 2))
    )),
    I(listOf(
        setOf(Point(3,1), Point(4, 1), Point(5, 1), Point(6, 1)),
        setOf(Point(5,0), Point(5, 1), Point(5, 2), Point(5, 3)),
        setOf(Point(3,2), Point(4, 2), Point(5, 2), Point(6, 2)),
        setOf(Point(4,0), Point(4, 1), Point(4, 2), Point(4, 3))
    ));

    private val leftMostPointXs = rotationStages
        .map { points -> points.minBy { it.x } ?.x ?: throw IllegalStateException("A shape must have at least one rotation stage") }
        .toIntArray()
    private val rightMostPointXs = rotationStages
        .map { points -> points.maxBy { it.x } ?.x ?: throw IllegalStateException("A shape must have at least one rotation stage") }
        .toIntArray()

    fun rotate(times: Int) = rotationStages[times % 4]

    fun leftMostPointX(rotationTimes: Int) = leftMostPointXs[rotationTimes % 4]

    fun rightMostPointX(rotationTimes: Int) = rightMostPointXs[rotationTimes % 4]
}

