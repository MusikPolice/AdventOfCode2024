package ca.jonathanfritz.aoc2k4

class Day5 {

    fun part1(input: List<String>): Int {
        val rules = input.parseRules()
        val updates = input.parseUpdates()

        return updates.filter {
            it.satisfiesRules(rules)
        }.sumOf {
            it.elementAt(it.size/2)
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    private fun List<Int>.satisfiesRules(rules: Map<Int, Set<Int>>): Boolean {
        // we only care to evaluate rules that apply to the pages in this update
        val applicableRules = rules.filter {
            rule -> rule.key in this || rule.value.intersect(this.toSet()).isNotEmpty()
        }

        this.indices.forEach { i ->
            // is it before everything that is supposed to come after it?
            val previousPages = this.subList(0, i)
            if (previousPages.isNotEmpty()) {
                val mustComeBefore = applicableRules.getOrDefault(this[i], emptySet())
                if (previousPages.intersect(mustComeBefore).isNotEmpty()) {
                    return false
                }
            }

            // is it after everything that is supposed to come before it?
            val subsequentPages = this.subList(i + 1, this.size)
            if (subsequentPages.isNotEmpty()) {
                val mustComeAfter = applicableRules.filter {
                    rule -> rule.value.contains(this[i])
                }.map { rule -> rule.key }.toSet()
                if (subsequentPages.intersect(mustComeAfter).isNotEmpty()) {
                    return false
                }
            }
        }

        return true
    }

    private fun List<String>.parseRules(): Map<Int, Set<Int>> {
        val rules: MutableMap<Int, Set<Int>> = mutableMapOf()
        this.filter { it.contains("|") }.forEach { line ->
            val parts = line.split("|")
            rules.putIfAbsent(parts[0].toInt(), setOf(parts[1].toInt()))?.let { existing ->
                rules[parts[0].toInt()] = existing + parts[1].toInt()
            }
        }
        return rules
    }

    private fun List<String>.parseUpdates(): List<List<Int>> {
        val updates: MutableList<List<Int>> = mutableListOf()
        this.filter { it.contains(",") }.forEach { line ->
            updates.add(line.split(",").map { it.toInt() })
        }
        return updates
    }
}

fun main() {
    val input = Utils.loadFromFile("Day5/input.txt")
    val day5 = Day5()
    println("Part 1: ${day5.part1(input)}")
    println("Part 2: ${day5.part2(input)}")
}