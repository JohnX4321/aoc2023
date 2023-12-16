fun main() {
    fun part1(input: List<String>): Int {
        val a=input.first()
        val m = input.drop(2).associate {
            val key = it.substringBefore(" ")
            val all = Regex("\\w+").findAll(it.substringAfter("=")).toList().map { it.value }
            Pair(key,Pair(all[0],all[1]))
        }
        var res=0
        var n="AAA"
        while (n!="222") {
            when(a[res%a.length]) {
                'L' -> n=m[n]?.first ?: ""
                'R' -> n=m[n]?.second ?: ""
            }
            res+=1
        }
        return res
    }

    fun part2(input: List<String>): Long {
        val a=input.first()
        val m = input.drop(2).associate {
            val key = it.substringBefore(" ")
            val all = Regex("\\w+").findAll(it.substringAfter("=")).toList().map { it.value }
            Pair(key,Pair(all[0],all[1]))
        }
        var res=0
        var nm = m.filter { it.key[2]=='A' }.map { it.key }.toMutableList()
        val nl = mutableListOf<Int>()
        while (nm.isNotEmpty()) {
            val newList = nm.toMutableList()
            var ix = 0
            nm.forEach {
                newList.remove(it)
                when(a[(res%a.length.toLong()).toInt()]) {
                    'L' -> nm[ix]=m[nm[ix]]?.first ?: ""
                    'R' -> nm[ix]=m[nm[ix]]?.second ?: ""
                }
                if (nm[ix].endsWith('Z'))
                    nl.add(res+1)
                else
                    newList.add(nm[ix])
                ix++
            }
            nm=newList
            res++
        }
        return lcm(nl.map { it.toLong() }.toLongArray())
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day08input.txt")
    part1(input).println()
    part2(input).println()
}
