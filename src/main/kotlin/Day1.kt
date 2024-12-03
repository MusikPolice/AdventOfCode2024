package ca.jonathanfritz.aoc2k4

class Day1 {

    fun part1(input: List<String>): Int {
        input.map { println(it) }
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }
}

fun main() {
    val input = Utils.loadFromFile("Day1/test.txt")
    val day1 = Day1()
    println("Part 1: ${day1.part1(input)}")
    println("Part 2: ${day1.part2(input)}")
}