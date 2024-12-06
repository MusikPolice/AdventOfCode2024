package ca.jonathanfritz.aoc2k4

import ca.jonathanfritz.aoc2k4.Day6.Direction.*

class Day6 {

    fun part1(input: List<String>): Int {
        val bounds = Position(input.first().length, input.size) // potential off by one?
        val obstacles = input.parseObstacles()
        var guard = input.parseGuard()

        val visited = mutableSetOf<Position>()
        while (guard.position.x >= 0 && guard.position.x < bounds.x && guard.position.y >= 0 && guard.position.y < bounds.y) {
            var nextPosition = guard.lookAhead()
            if (obstacles.contains(nextPosition)) {
                // turn right
                val newDirection = guard.direction.turnRight()
                nextPosition = guard.copy(direction = newDirection).lookAhead() // TODO: what if this is also an obstacle?
                guard = guard.copy(direction = newDirection, position = nextPosition)
            } else {
                // move forward
                guard = guard.copy(position = nextPosition)
            }
            visited.add(guard.position)
        }

        printMap(bounds, guard, obstacles, visited)

        return visited.size - 1
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    private fun printMap(bounds: Position, guard: Guard, obstacles: Set<Position>, visited: Set<Position> = emptySet()) {
        (0 until bounds.y).forEach { y ->
            (0 until bounds.x).forEach { x ->
                val position = Position(x, y)
                if (guard.position == position) {
                    when (guard.direction) {
                        UP -> print("^")
                        DOWN -> print("v")
                        LEFT -> print("<")
                        RIGHT -> print(">")
                    }
                } else if (obstacles.contains(position)) {
                    print("#")
                } else if (visited.contains(position)) {
                    print("X")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    private fun List<String>.parseObstacles(): Set<Position> {
        return this.flatMapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, c ->
                if (c == '#') {
                    Position(x, y)
                } else {
                    null
                }
            }
        }.filterNotNull().toSet()
    }

    private fun List<String>.parseGuard(): Guard {
        this.flatMapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, c ->
                if (c == '^') {
                    return Guard(Position(x, y), UP)
                }
            }
        }
        throw IllegalArgumentException("No guard found")
    }

    data class Position(val x: Int, val y: Int)

    enum class Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    fun Direction.turnRight(): Direction {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    data class Guard(val position: Position, val direction: Direction) {
        fun lookAhead(): Position {
            return when (direction) {
                UP -> Position(position.x, position.y - 1)
                DOWN -> Position(position.x, position.y + 1)
                LEFT -> Position(position.x - 1, position.y)
                RIGHT -> Position(position.x + 1, position.y)
            }
        }
    }
}

fun main() {
    val input = Utils.loadFromFile("Day6/input.txt")
    val day6 = Day6()
    println("Part 1: ${day6.part1(input)}")
    println("Part 2: ${day6.part2(input)}")
}