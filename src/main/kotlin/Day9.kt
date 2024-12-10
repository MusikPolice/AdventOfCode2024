package ca.jonathanfritz.aoc2k4

class Day9 {

    fun part1(input: List<String>): Long {
        val diskMap = parseDiskMap(input[0]).toMutableList()

        // two pointers, one at the first null element in the list, one at the last numeric element in the list
        // move the value at the second pointer to the first pointer, increment the first pointer to the next null
        // element and decrement the second pointer to the next numeric element. repeat until the pointers meet.
        while (true) {
            val nullPointer = diskMap.indexOfFirst { it == null }
            val numericPointer = diskMap.indexOfLast { it != null }
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

    fun part2(input: List<String>): Long {
        val diskMap = parseDiskMap(input[0]).toMutableList()

        // need a different data structure that avoids having to bit fiddle when moving blocks around
        val blocks = diskMap.groupContiguousElements().toMutableList()

        var dataSearchEndIndex = blocks.last { it.isData }.end
        while (true) {

            // find the last block of data that occurs before the end of the search range
            val data = blocks.lastOrNull { it.isData && it.end <= dataSearchEndIndex } ?: break

            // find the first block of free space that is big enough to fit the data and that occurs before the data block
            val freeSpace = blocks.firstOrNull { it.isFreeSpace && it.end <= data.start && it.size >= data.size }
            if (freeSpace == null) {
                dataSearchEndIndex = data.start
                continue
            }

            // remove the data block and replace it with a free space block of equal size
            val indexOfData = blocks.indexOf(data)
            blocks.removeAt(indexOfData)
            blocks.add(indexOfData, Block(data.start, data.size))

            // insert the data block into the free space, updating its start position
            val indexOfFreeSpace = blocks.indexOf(freeSpace)
            blocks.add(indexOfFreeSpace, data.copy(start = freeSpace.start))

            // remove the free space that we filled with data, back filling with a smaller free space block if need be
            blocks.remove(freeSpace)
            val remainingFreeSpace = freeSpace.size - data.size
            if (remainingFreeSpace > 0) {
                blocks.add(indexOfFreeSpace + 1, Block(freeSpace.start + data.size, remainingFreeSpace))
            }

            // update the end of the search range so that we don't waste time looking to the right of data we just moved
            dataSearchEndIndex = data.start
        }

        return blocks.sumOf { it.checksum() }
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

    data class Block(val start: Int, val size: Int, val data: Int? = null) {
        val end = start + size
        val isData = data != null
        val isFreeSpace = data == null

        fun checksum(): Long {
            if (isData) {
                return (0 until size).sumOf { i -> (start + i).toLong() * data!! }
            }
            return 0
        }
    }

    private fun List<Int?>.groupContiguousElements(): List<Block> {
        val blocks = mutableListOf<Block>()
        var startIndex = 0
        var size = 0
        var data: Int? = null
        forEachIndexed { i, element ->
            if (element == data) {
                size++
            } else {
                if (size > 0) {
                    blocks.add(Block(startIndex, size, data))
                }
                startIndex = i
                size = 1
                data = element
            }
        }
        if (size > 0) {
            blocks.add(Block(startIndex, size, data))
        }
        return blocks
    }
}

fun main() {
    val input = Utils.loadFromFile("Day9/input.txt")
    println("Part 1: ${Day9().part1(input)}")
    println("Part 2: ${Day9().part2(input)}")
}