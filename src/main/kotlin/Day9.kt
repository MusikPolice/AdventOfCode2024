package ca.jonathanfritz.aoc2k4

class Day9 {

    fun part1(input: List<String>): Long {
        val diskMap = parseDiskMap(input[0]).toMutableList()

        // two pointers, one at the first null element in the list, one at the last numeric element in the list
        // move the value at the second pointer to the first pointer, increment the first pointer to the next null
        // element and decrement the second pointer to the next numeric element. repeat until the pointers meet.
        var nullPointer: Int
        var numericPointer: Int
        while (true) {
            nullPointer = diskMap.indexOfFirst { it == null }
            numericPointer = diskMap.indexOfLast { it != null }
            if (nullPointer < numericPointer) {
                diskMap[nullPointer] = diskMap[numericPointer]
                diskMap[numericPointer] = null
            } else {
                break
            }
        }

        // have to explicitly case as long here, else integer overflow gives the wrong answer
        return diskMap.filterNotNull().mapIndexed { i, fileId -> (i * fileId).toLong() }.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    private fun parseDiskMap(input: String): List<Int?> {
        var fileId = -1
        return input.flatMapIndexed { i, c ->
            val length = c.digitToInt()
            if (i % 2 == 0) {
                fileId++
                (0 until length).map { fileId }.toList()
            } else {
                (0 until length).map { null }.toList()
            }
        }
    }
}

fun main() {
    val input = Utils.loadFromFile("Day9/input.txt")
    println("Part 1: ${Day9().part1(input)}")
    println("Part 2: ${Day9().part2(input)}")
}