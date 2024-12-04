package ca.jonathanfritz.aoc2k4

class Day4 {

    fun part1(input: List<String>): Int {
        val puzzle = input.map { it.toCharArray() }
        var results = 0
        puzzle.indices.forEach { row ->
            (puzzle[row].indices).forEach { col ->
                val symbol = puzzle[row][col]
                if (symbol == 'X') {
                    results += puzzle.searchUp(row, col) +
                            puzzle.searchDown(row, col) +
                            puzzle.searchLeft(row, col) +
                            puzzle.searchRight(row, col) +
                            puzzle.searchUpLeft(row, col) +
                            puzzle.searchUpRight(row, col) +
                            puzzle.searchDownLeft(row, col) +
                            puzzle.searchDownRight(row, col)
                }
            }
        }
        return results
    }

    private fun List<CharArray>.searchUp(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow - 1 to fromCol), (fromRow - 2 to fromCol), (fromRow - 3 to fromCol))
    private fun List<CharArray>.searchDown(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow + 1 to fromCol), (fromRow + 2 to fromCol), (fromRow + 3 to fromCol))
    private fun List<CharArray>.searchLeft(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow to fromCol - 1), (fromRow to fromCol - 2), (fromRow to fromCol - 3))
    private fun List<CharArray>.searchRight(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow to fromCol + 1), (fromRow to fromCol + 2), (fromRow to fromCol + 3))
    private fun List<CharArray>.searchUpLeft(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow - 1 to fromCol - 1), (fromRow - 2 to fromCol - 2), (fromRow - 3 to fromCol - 3))
    private fun List<CharArray>.searchUpRight(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow - 1 to fromCol + 1), (fromRow - 2 to fromCol + 2), (fromRow - 3 to fromCol + 3))
    private fun List<CharArray>.searchDownLeft(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow + 1 to fromCol - 1), (fromRow + 2 to fromCol - 2), (fromRow + 3 to fromCol - 3))
    private fun List<CharArray>.searchDownRight(fromRow: Int, fromCol: Int) =
        searchXmas((fromRow + 1 to fromCol + 1), (fromRow + 2 to fromCol + 2), (fromRow + 3 to fromCol + 3))
    private fun List<CharArray>.searchXmas(m: Pair<Int, Int>, a: Pair<Int, Int>, s: Pair<Int, Int>): Int {
        return if (
            this.getOrNull(m.first)?.getOrNull(m.second) == 'M' &&
            this.getOrNull(a.first)?.getOrNull(a.second) == 'A' &&
            this.getOrNull(s.first)?.getOrNull(s.second) == 'S'
        ) { 1 } else { 0 }
    }

    fun part2(input: List<String>): Int {
        val puzzle = input.map { it.toCharArray() }
        var results = 0
        puzzle.indices.forEach { row ->
            (puzzle[row].indices).forEach { col ->
                val symbol = puzzle[row][col]
                if (symbol == 'A') {
                    if (puzzle.searchLeftDiagonal(row, col) && puzzle.searchRightDiagonal(row, col)) {
                        results++
                    }
                }
            }
        }
        return results
    }

    private fun List<CharArray>.searchLeftDiagonal(fromRow: Int, fromCol: Int): Boolean {
        return (this.getOrNull(fromRow - 1)?.getOrNull(fromCol - 1) == 'M' &&
                this.getOrNull(fromRow + 1)?.getOrNull(fromCol + 1) == 'S') ||
               (this.getOrNull(fromRow - 1)?.getOrNull(fromCol - 1) == 'S' &&
                this.getOrNull(fromRow + 1)?.getOrNull(fromCol + 1) == 'M')
    }
    private fun List<CharArray>.searchRightDiagonal(fromRow: Int, fromCol: Int): Boolean {
        return (this.getOrNull(fromRow - 1)?.getOrNull(fromCol + 1) == 'M' &&
                this.getOrNull(fromRow + 1)?.getOrNull(fromCol - 1) == 'S') ||
               (this.getOrNull(fromRow - 1)?.getOrNull(fromCol + 1) == 'S' &&
                this.getOrNull(fromRow + 1)?.getOrNull(fromCol - 1) == 'M')
    }
}

fun main() {
    val input = Utils.loadFromFile("Day4/input.txt")
    val day4 = Day4()
    println("Part 1: ${day4.part1(input)}")
    println("Part 2: ${day4.part2(input)}")
}