import java.util.*

const val DEFAULT = -1

data class Point(val x: Int, val y: Int)

data class Number(val start: Int,val end: Int,val value: Int) {
    constructor(start: Int,end: Int,l: String):
    this(start, end, l.substring(start,end+1).toInt())
    fun isNeighbour(point: Point) = point.x in (if (start==-1) 0 else start-1)..(end+1)
}

fun parseData(r: Int,l: String, setData: (Char) -> Boolean = {true}): Pair<List<Point>,List<Number>> {
    val s = mutableListOf<Point>()
    val n = mutableListOf<Number>()
    var ns = DEFAULT
    fun add(x: Int) {
        if (ns==DEFAULT) return
        n.add(Number(ns,x-1,l))
        ns=DEFAULT
    }
    for ((ix,c) in l.withIndex()) {
        when {
            c=='.' -> add(ix)
            c.isSymbol() -> {
                if (setData(c)) s.add(Point(ix,r))
                add(ix)
            }
            ix<l.lastIndex -> {
                if (ns==DEFAULT) ns=ix
            }
            else -> {
                val from = if (ns!=DEFAULT) ns else ix
                n.add(Number(from,ix,l))
            }
        }
    }
    return s to n
}

fun main() {
    fun part1(input: List<String>): Int {
        var prev = emptyList<Point>()
        return input.mapIndexed(::parseData)
            .windowed(2,1,true) { w ->
                val s = prev+w.flatMap { (sy,_) -> sy}
                val (cls,cln) = w.first()
                prev = cls
                cln.filter {s.any(it::isNeighbour)}
                    .sumOf(Number::value)
            }.sum()
    }

    fun part2(input: List<String>): Int {
        var prev = emptyList<Point>()
        val numWithN = input.mapIndexed { index, s -> parseData(index,s) {it=='*'} }
            .windowed(2,1,true) {w->
                val g = prev+w.flatMap { (g,_) -> g }
                val (clg,cln) = w.first()
                prev = clg
                cln.mapNotNull {num ->
                    g.fold(mutableListOf<Point>()) {a,g2->
                        a.also { if (num.isNeighbour(g2)) a.add(g2) }
                    }.ifEmpty { null }?.let { num to it }
                }
            }.flatten()
        return numWithN.foldIndexed(0) {ix,s,(num,g) ->
            numWithN.slice((ix+1)..<numWithN.size)
                .fold(s) {a,(n2,gs) ->
            a+if (g.intersect(gs.toSet()).isNotEmpty()) {
                num.value*n2.value
            } else 0
        }
            }
        
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day03input.txt")
    part1(input).println()
    part2(input).println()
}
