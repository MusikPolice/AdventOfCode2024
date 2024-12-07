package ca.jonathanfritz.aoc2k4

import ca.jonathanfritz.aoc2k4.Day6.Direction.*
import kotlin.math.abs

class Day6 {

    fun part1(input: List<String>): Int {
        val bounds = Position(input.first().length, input.size)
        val obstacles = input.parseObstacles()
        var guard = input.parseGuard()

        val visited = mutableSetOf<Position>()
        while (guard.isWithinBounds(bounds)) {
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

        return visited.size - 1
    }

    fun part2(input: List<String>): Int {
        val bounds = Position(input.first().length, input.size)
        val obstacles = input.parseObstacles()
        var guard = input.parseGuard()
        val startingPosition = guard.position

        val visited = mutableSetOf<Position>()
        val newObstacles = mutableSetOf<Position>()

        printMap(bounds, guard, obstacles, visited)

        while (guard.isWithinBounds(bounds)) {
            var forwardPosition = guard.lookAhead()
            val rightwardDirection = guard.direction.turnRight()
            if (obstacles.contains(forwardPosition)) {
                // turn right
                forwardPosition = guard.copy(direction = rightwardDirection).lookAhead() // TODO: what if this is also an obstacle?
                guard = guard.copy(direction = rightwardDirection, position = forwardPosition)
            } else {
                // if we were to turn right here, would we end up at a corner that we've already visited?
                var testGuard = guard.copy(direction = rightwardDirection)
                do {
                    val testPosition = testGuard.lookAhead()
                    if (obstacles.contains(testPosition)) {
                        if (visited.contains(testGuard.position) && forwardPosition.isNotAdjacentTo(startingPosition)) {
                            newObstacles.add(forwardPosition)

                            println()
                            printMap(bounds, guard, obstacles, visited, newObstacles)
                        }
                        break
                    }
                    testGuard = testGuard.copy(position = testPosition)
                } while (testGuard.isWithinBounds(bounds))

                // having checked to see if a new obstacle could be added in front of us, move forward as if one isn't
                // there because we want to find all possible places where an obstacle could be added
                guard = guard.copy(position = forwardPosition)
            }
            visited.add(guard.position)
        }

        return newObstacles.size
    }

    private fun printMap(bounds: Position, guard: Guard, obstacles: Set<Position>, visited: Set<Position> = emptySet(), newObstacles: Set<Position> = emptySet()) {
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
                } else if (newObstacles.contains(position)) {
                    print("O")
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

    data class Position(val x: Int, val y: Int) {
        fun isNotAdjacentTo(other: Position) = this != other && (abs(this.x - other.x) != 1 || abs(this.y - other.y) != 1)
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT;
    }
    private fun Direction.turnRight(): Direction {
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

        fun isWithinBounds(bounds: Position) =
            position.x >= 0 && position.x < bounds.x && position.y >= 0 && position.y < bounds.y
    }
}

fun main() {
    val input = Utils.loadFromFile("Day6/test.txt")
    val day6 = Day6()
    println("Part 1: ${day6.part1(input)}")
    println("Part 2: ${day6.part2(input)}")
}