package ca.jonathanfritz.aoc2k4

import kotlin.math.pow

class Day7 {

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val equation = line.parseEquation()
            equation.enumerateOperators().forEach { operators ->
                if (equation.evaluate(operators) == equation.result) {
                    return@sumOf equation.result
                }
            }
            return@sumOf 0
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    enum class Operators {
        ADDITION {
            override fun apply(a: Long, b: Long): Long = a + b
            override fun toString(): String = "+"
        },
        MULTIPLICATION {
            override fun apply(a: Long, b: Long): Long = a * b
            override fun toString(): String = "*"
        };

        abstract fun apply(a: Long, b: Long): Long
    }

    data class Equation(val result: Long, val operands: List<Long>) {

        fun enumerateOperators(): List<List<Operators>> {
            // if we know the number of possible operators, and we know how many operators are in the equation,
            // we can compute the number of permutations that need to be computed
            val numOperatorsInEquation = operands.size - 1
            val radix = Operators.entries.size
            val permutations = radix.toDouble().pow((numOperatorsInEquation).toDouble()).toInt()

            // for each permutation, convert the number to a string in the given radix, then map each digit in that
            // string to an operator, using the enum values as the index
            return (0 until permutations).map { i ->
                val permutationString = i.toString(radix).padStart(numOperatorsInEquation, '0')
                permutationString.map { Operators.entries[it.toString().toInt()] }
            }
        }

        fun evaluate(operators: List<Operators>): Long {
            var result = operands[0]
            for (i in 1 until operands.size) {
                result = operators[i - 1].apply(result, operands[i])
            }
            return result
        }
    }

    private fun String.parseEquation(): Equation {
        val result = this.substringBefore(":").toLong()
        val operands = this.substringAfter(":").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        return Equation(result, operands)
    }
}

fun main() {
    val input = Utils.loadFromFile("Day7/input.txt")
    println("Part 1: ${Day7().part1(input)}")
    println("Part 2: ${Day7().part2(input)}")
}