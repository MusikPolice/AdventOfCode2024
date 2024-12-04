package ca.jonathanfritz.aoc2k4

import kotlin.math.abs

class Day1 {

    fun part1(input: List<String>): Int {
        val integers = input.map { line -> line.split(" ").filter { token -> token.isNotBlank() } }
            .map { it[0].toInt() to it[1].toInt() }
        val left = integers.map { it.first }.sorted()
        val right = integers.map { it.second }.sorted()
        return left.mapIndexed { i, token ->
            abs(token - right[i])
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val integers = input.map { line -> line.split(" ").filter { token -> token.isNotBlank() } }
            .map { it[0].toInt() to it[1].toInt() }
        val keys = integers.map { it.first }
        val values = integers.map { it.second }
        val counts = keys.associateWith { key -> values.count { it == key } }
        return keys.sumOf { it * (counts[it] ?: 0) }
    }
}

fun main() {
    val input = Utils.loadFromFile("Day1/input.txt")
    val day1 = Day1()
    println("Part 1: ${day1.part1(input)}")
    println("Part 2: ${day1.part2(input)}")
}