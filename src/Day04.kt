import java.util.*
import kotlin.collections.HashMap
import kotlin.math.pow

fun main() {
    val space = " +".toRegex()
    fun part1(input: List<String>): Int {
        var res=0
        
        for (i in input) {
            val ci = i.split(" | ")
            val cw = ci[0].split(": +".toRegex())
            val w = cw[1].split(space)
            ci[1].split(space).count { w.contains(it) }
                .takeIf { it>0 }?.let { res+=2.0.pow(it-1).toInt() }
        }
        return res
    }

    fun part2(input: List<String>): Int {
        var res = 0
        val map = HashMap<Int,Int>()
        for (i in input) {
            res+=1
            val ci=i.split(" | ")
            val cw=ci[0].split(": +".toRegex())
            val cn = cw[0].split(space)[1].toInt()
            val w=cw[1].split(space)
            res+=map[cn]?:0
            ci[1].split(space).count { w.contains(it) }
                .takeIf { it>0 }?.let {
                    (cn+1..cn+it).forEach { j ->
                        map[j]=(map[j]?:0)+(map[cn]?:0)+1
                    }
                }
        }
        
        return res
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day04input.txt")
    part1(input).println()
    part2(input).println()
}
