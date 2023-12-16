fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf {
            val (c,g) = it.split(" ")
            val mem = mutableMapOf<Pair<List<Long>,List<Char>>,Long>()
            getArr(c.map { it },g.split(",").map { it.toLong() },mem)
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { l ->
            val (c,g) = l.split(" ")
            val cl = mutableListOf<String>()
            val gsl = mutableListOf<Long>()
            repeat(5) {
                cl.addAll(listOf(c))
                gsl.addAll(g.split(",").map { it.toLong() })
            }
            val jcrl = cl.joinToString("?")
            val mem = mutableMapOf<Pair<List<Long>,List<Char>>,Long>()
            getArr(jcrl.map { it },gsl,mem)
        }
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day12input.txt")
    part1(input).println()
    part2(input).println()
}

fun getArr(cList: List<Char>, gsList: List<Long>, mem: MutableMap<Pair<List<Long>, List<Char>>, Long>): Long {
    if (cList.isEmpty()) return if (gsList.isEmpty()) 1 else 0
    return when (cList.first()) {
        '.' -> getArr(cList.drop(1),gsList,mem)
        '#' -> isDamaged(cList, gsList, mem).also { mem[gsList to cList] = it }
        '?' -> getArr(cList.drop(1),gsList,mem)+isDamaged(cList, gsList, mem)
                .also { mem[gsList to cList] = it }
        else -> error("EX")
    }
}

fun isDamaged(cList: List<Char>, gsList: List<Long>, mem: MutableMap<Pair<List<Long>, List<Char>>, Long>): Long {
    mem[gsList to cList]?.let { return it }
    if (gsList.isEmpty()) return 0
    val x=gsList[0]
    if (cList.size<x) return 0
    for (i in 0..<x) if (cList[i.toInt()]=='.') return 0
    if (cList.size==x.toInt()) return if (gsList.size==1) 1 else 0
    if (cList[x.toInt()]=='#') return 0
    return getArr(cList.drop(x.toInt()+1),gsList.drop(1),mem)
}