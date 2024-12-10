package ca.jonathanfritz.aoc2k4

import java.util.Stack

class Day10 {

    /**
     * a hiking trail is any path that starts at height 0, ends at height 9, and always increases by a height of
     * exactly 1 at each step. Hiking trails never include diagonal steps - only up, down, left, or right.
     * A trailhead is any position with height 0 that starts one or more hiking trails. A trailhead's score is the
     * number of 9-height positions reachable from that trailhead via a hiking trail
     */
    fun part1(input: List<String>): Int {
        val positions = input.parsePositions()

        // find all the trailheads (0 height positions)
        val trailheads = positions.flatten().filter { it.isTrailhead() }
        return trailheads.associateWith { trailhead ->
            val stack: Stack<Position> = Stack()
            stack.push(trailhead)

            var score = 0
            val visited = mutableSetOf<Position>()
            while (stack.isNotEmpty()) {
                val current = stack.pop()

                // skip positions that we've already visited to avoid loops
                if (visited.contains(current)) {
                    continue
                } else {
                    visited.add(current)
                }

                if (current.isSummit()) {
                    // we've reached a summit, increment the trailhead's score
                    score++
                } else {
                    // otherwise add all valid moves from this position to the stack
                    current.moveNorth(positions)?.let { stack.push(it) }
                    current.moveEast(positions)?.let { stack.push(it) }
                    current.moveSouth(positions)?.let { stack.push(it) }
                    current.moveWest(positions)?.let { stack.push(it) }
                }
            }

            // return the score for this trailhead (i.e. the number of summits that can be reached from it)
            score
        }.values.sum()
    }

    data class Point(val x: Int, val y: Int)
    data class Position(val point: Point, val height: Int) {
        fun isTrailhead() = height == 0
        fun isSummit() = height == 9

        // these functions return null if the move is out of bounds or if the relative heights differ by more than 1
        fun moveNorth(positions: List<List<Position>>): Position? = move(positions, point.x, point.y - 1)
        fun moveEast(positions: List<List<Position>>) : Position? = move(positions, point.x + 1, point.y)
        fun moveSouth(positions: List<List<Position>>) : Position? = move(positions, point.x, point.y + 1)
        fun moveWest(positions: List<List<Position>>) : Position? = move(positions, point.x - 1, point.y)
        private fun move(positions: List<List<Position>>, x: Int, y: Int): Position? {
            positions.getOrNull(y)?.getOrNull(x)?.let { north ->
                if (north.height == height + 1) {
                    return north
                }
            }
            return null
        }
    }

    private fun List<String>.parsePositions(): List<List<Position>> {
        return this.mapIndexed { row, line ->
            line.mapIndexed { col, height ->
                Position(Point(col, row), height.toString().toInt())
            }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }
}

fun main() {
    val input = Utils.loadFromFile("Day10/input.txt")
    println("Part 1: ${Day10().part1(input)}")
    println("Part 2: ${Day10().part2(input)}")
}