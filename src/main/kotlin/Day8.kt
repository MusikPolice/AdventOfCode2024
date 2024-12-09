package ca.jonathanfritz.aoc2k4

import kotlin.math.abs

class Day8 {

    fun part1(input: List<String>): Int {
        val antennas = parseInput(input)
        return antennas.entries.flatMap { (frequency, antennaPositions) ->
            antennaPositions.allPairs().flatMap { antennaPair ->
                val rise = abs(antennaPair.second.y - antennaPair.first.y)
                val run = abs(antennaPair.second.x - antennaPair.first.x)
                val antinodes = if (antennaPair.first.x < antennaPair.second.x) {
                    listOf(
                        Position(x = antennaPair.first.x - run, y = antennaPair.first.y - rise),
                        Position(x = antennaPair.second.x + run, y = antennaPair.second.y + rise)
                    )
                } else {
                    listOf(
                        Position(x = antennaPair.first.x + run, y = antennaPair.first.y - rise),
                        Position(x = antennaPair.second.x - run, y = antennaPair.second.y + rise)
                    )
                }.filter { antinode ->
                    // make sure each antinode that we found is in bounds
                    antinode.x >= 0 && antinode.y >= 0 && antinode.x < input[0].length && antinode.y < input.size
                }
                antinodes
            }
        }.distinct().size
    }

    private fun print(nodes: List<Position>, antinodes:List<Position>) {
        val minX = antinodes.minByOrNull { it.x }!!.x
        val maxX = antinodes.maxByOrNull { it.x }!!.x
        val minY = antinodes.minByOrNull { it.y }!!.y
        val maxY = antinodes.maxByOrNull { it.y }!!.y

        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (nodes.contains(Position(x, y))) {
                    print("a")
                } else if (antinodes.contains(Position(x, y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    private fun parseInput(input: List<String>): Map<Char, List<Position>> {
         return input.flatMapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, char ->
                if(char != '.') {
                    char to Position(x, y)
                } else {
                    null
                }
            }.filterNotNull()
        }.groupBy({ it.first }, { it.second })
    }

    private data class Position(val x: Int, val y: Int)

    // splits the input list into all possible pairs of elements, ignoring the order of the elements in each pair
    private fun List<Position>.allPairs(): List<Pair<Position, Position>> {
        val pairs = mutableListOf<Pair<Position, Position>>()
        for (i in indices) {
            for (j in i + 1 until size) {
                pairs.add(this[i] to this[j])
            }
        }
        return pairs
    }
}

fun main() {
    val input = Utils.loadFromFile("Day8/input.txt")
    val day8 = Day8()
    println("Part 1: ${day8.part1(input)}")
    println("Part 2: ${day8.part2(input)}")
}