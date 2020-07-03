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

    fun rotate(times: Int): Set<Point> = rotationStages[times % 4]
}

