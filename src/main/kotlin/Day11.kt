package ca.jonathanfritz.aoc2k4

class Day11 {

    fun part1(input: List<String>): Int {
        var stones = input[0].split(" ").map{ it.toLong() }

        // blinks
        (0 until 25).forEach { _ ->
            val updatedStones = mutableListOf<Long>()

            stones.forEach { stone ->
                if (stone == 0L) {
                    // If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1
                    updatedStones.add(1)
                } else if (stone.toString().toCharArray().size % 2 == 0) {
                    // If the stone is engraved with a number that has an even number of digits, it is replaced by two
                    // stones. The left half of the digits are engraved on the new left stone, and the right half of the
                    // digits are engraved on the new right stone
                    updatedStones.add(stone.toString().slice(0 until stone.toString().length / 2).toLong())
                    updatedStones.add(stone.toString().slice(stone.toString().length / 2 until stone.toString().length).toLong())
                } else {
                    // If none of the other rules apply, the stone is replaced by a new stone; the old stone's number
                    // multiplied by 2024 is engraved on the new stone
                    updatedStones.add(stone * 2024L)
                }
            }

            // updating the list pointer ensures that the stones change simultaneously
            stones = updatedStones
        }

        return stones.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }
}

fun main() {
    val input = Utils.loadFromFile("Day11/input.txt")
    println("Part 1: ${Day11().part1(input)}")
    println("Part 2: ${Day11().part2(input)}")
}