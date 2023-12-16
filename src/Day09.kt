fun main() {

    fun help1(it: String): String {
        val split = it.split(" ").toMutableList()
        val il = mutableListOf<String>()
        for (i in 1 until split.size)
            il.add((split[i].toInt()-split[i-1].toInt()).toString())
        if (il.count { it=="0" }!=il.size)
            il.add(help1(il.joinToString(" ")).split(" ").last())
        (split.add((split.last().toInt()+il.last().toInt()).toString()))
        return split.joinToString(" ")
    }

    fun help2(s: String): String {
        val split = Regex("-?\\d+").findAll(s).map { it.value }.toMutableList()
        val il = mutableListOf<String>()
        for (i in 1 until split.size)
            il.add((split[1].toInt()-split[i-1].toInt()).toString())
        if (il.count { it=="0" }!=il.size)
            il.add(0,help2(il.joinToString(" ")).split(" ").first())
        (split.add(0,(split.first().toInt()-il.first().toInt()).toString()))
        return split.joinToString(" ")
    }
    fun part1(input: List<String>): Int {
        var res=0
        input.forEach {
            res+=help1(it).split(" ").last().toInt()
        }
        return res

    }

    fun part2(input: List<String>): Int {
        var res=0
        input.forEach {
            res+=help2(it).split(" ").first().toInt()
        }
        return res
    }





    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day09input.txt")
    part1(input).println()
    part2(input).println()
}
