package ca.jonathanfritz.aoc2k4

import kotlin.math.abs

class Day2 {

    fun part1(input: List<String>): Int {
        val reports = input.map { line -> line.split(" ").filter { token -> token.isNotBlank() } }
            .map { levels -> levels.map { it.toInt() } }
        return reports.count { report -> report.isSafe() }
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { line -> line.split(" ").filter { token -> token.isNotBlank() } }
            .map { levels -> levels.map { it.toInt() } }

        return reports.count { report ->
            if (report.isSafe()) {
                return@count true
            }

            // re-check, removing each level from the report in turn
            for (i in report.indices) {
                val dampenedReport = report.slice(report.indices - i)
                if (dampenedReport.isSafe()) {
                    return@count true
                }
            }
            return@count false
        }
    }

    private fun List<Int>.isSafe() = (isDecreasing() || isIncreasing()) && levelDifferenceIsOk()
    private fun List<Int>.isDecreasing() = (1 until size).all { i -> this[i - 1] > this[i] }
    private fun List<Int>.isIncreasing() = (1 until size).all { i -> this[i - 1] < this[i] }
    private fun List<Int>.levelDifferenceIsOk() =
        (1 until size).all { i -> abs(this[i - 1] - this[i]) in 1..3 }
}

fun main() {
    val input = Utils.loadFromFile("Day2/input.txt")
    val day2 = Day2()
    println("Part 1: ${day2.part1(input)}")
    println("Part 2: ${day2.part2(input)}")
}