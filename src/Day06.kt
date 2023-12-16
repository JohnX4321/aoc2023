fun main() {
    fun part1(input: List<String>): Long {
        val p=input.map { it.substringAfter(" ").trimStart().split(Regex("\\s+")) }
        val t=p[0].zip(p[1]) {x,y->Pair(x.toLong(),y.toLong())}
        var res=1L
        t.forEach {
            var z=0
            for (i in 0..it.first) {
                val d = i*(it.first-i)
                if (d<=it.second) z+=1
                else break
            }
            for (i in (0..it.first).reversed()) {
                val d=i*(it.first-i)
                if (d<=it.second) z+=1
                else break
            }
            res*=(it.first+1)-z
        }
        return res
    }

    fun part2(input: List<String>): Long {
        val t=input[0].substringAfter(":").replace(" ","").toLong()
        val d=input[1].substringAfter(":").replace(" ","").toLong()
        var res=0L
        for (i in 0..t) {
            val a=i*(t-i)
            if (a<=d) res+=1
            else break
        }
        for (i in (0..t).reversed()) {
            val a=i*(t-i)
            if (a<=d) res+=1;
            else break;
        }
        return (t+1)-res
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day06input.txt")
    part1(input).println()
    part2(input).println()
}
