import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var res=0
        for (i in input) {
            var stack = Stack<Char>()
            i.toCharArray().forEach { c ->
                if(c.isDigit())
                    stack.push(c)
            }
            if(stack.isNotEmpty()) {
                res+="${stack.first()}${stack.peek()}".toInt()
            }
        }
        return res
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val arr = arrayOf("0","1","2","3","4","5","6","7","8","9",
            "zero","one","two","three","four","five","six","seven","eight","nine")
        for (i in input) {
            val map = TreeMap<Int,Int>()
            arr.forEachIndexed { index, s ->
                i.indexOf(s).takeIf { it!=-1 }?.let {
                    map[it]=index%10
                }
                i.lastIndexOf(s).takeIf { it!=-1 }?.let {
                    map[it]=index%10
                }
            }
            sum+=map.firstEntry().value*10+map.lastEntry().value
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day01input.txt")
    part1(input).println()
    part2(input).println()
}
