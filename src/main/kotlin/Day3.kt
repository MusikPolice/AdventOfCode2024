package ca.jonathanfritz.aoc2k4

class Day3 {

    fun part1(input: List<String>): Int {
        val regex = Regex("mul\\(\\d+,\\d+\\)")
        return input.sumOf { line ->
            regex.findAll(line).sumOf { instruction ->
                val (a, b) = instruction.value.substringAfter("(").substringBefore(")").split(",")
                a.toInt() * b.toInt()
            }
        }
    }

    fun part2(input: List<String>): Int {
        var enabled = true
        return input.sumOf { line ->
            val mults = Regex("mul\\(\\d+,\\d+\\)").findAllIndexed(line)
            val dos = Regex("do\\(\\)").findAllIndexed(line)
            val donts = Regex("don't\\(\\)").findAllIndexed(line)

            var sum = 0
            (0 .. line.length).forEach { i ->
                if (enabled && mults.containsKey(i)) {
                    val (a, b) = mults[i]!!.substringAfter("(").substringBefore(")").split(",")
                    sum += a.toInt() * b.toInt()
                } else if (dos.containsKey(i)) {
                    enabled = true
                } else if (donts.containsKey(i)) {
                    enabled = false
                }
            }
            sum
        }
    }

    // finds all matches, returning a map that has the character index of the match as the key
    private fun Regex.findAllIndexed(input: String) = findAll(input).map { it.range.first to it.value }.toMap()
}

fun main() {
    val input = Utils.loadFromFile("Day3/input.txt")
    val day3 = Day3()
    println("Part 1: ${day3.part1(input)}")
    println("Part 2: ${day3.part2(input)}")
}