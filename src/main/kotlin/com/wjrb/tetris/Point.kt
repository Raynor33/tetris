package com.wjrb.tetris

import java.lang.IllegalStateException

data class Point(val x: Int, val y: Int) {
    init {
        if (x < 0 || x >= 10 || y < 0 || y >= 20) {
            throw IllegalStateException()
        }
    }
}