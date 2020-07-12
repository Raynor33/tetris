package com.wjrb.tetris

fun main(args: Array<String>) {
    (0 until 1000).forEach {
        runGame()
    }

    val start = System.currentTimeMillis()

    val times = 10000
    (0 until times).forEach {
        runGame()
    }

    println((times * 1000.0 / (System.currentTimeMillis() - start)).toString() + " games per second")
}

private fun runGame() {
    var board = Board.of { Shape.T }
    while (!board.complete()) {
        board
            .possibleOutcomes()
            .last()
            .instructions()
            .forEach {
                board = it.applyTo(board)
            }
    }
}

private fun runGame2() {
    var board = Board.of { Shape.T }
    while (!board.complete()) {
        board = board.drop()
    }
}