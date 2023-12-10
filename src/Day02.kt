fun main() {
    fun part1(input: List<String>): Int {
        val ids = mutableListOf<Int>()
        val colorMap = HashMap<String,Int>().apply {
            put("red",12)
            put("green",13)
            put("blue",14)
        }
        for (i in input) {
            val j = i.split(": ")
            if (j[1].split(";").none { cs ->
                cs.split(",").any { cc ->
                    val count = cc.split(" ")
                    colorMap[count[1]]?.let {
                        it < count[0].toInt()
                    }==true
                }
            })
                ids.add(j[0].split(" ")[1].toInt())
        }
        return ids.sum()
    }

    fun part2(input: List<String>): Int {
        var res=0
        val map = HashMap<String,Int>().apply {
            put("red",0)
            put("green",0)
            put("blue",0)
        }
        for (i in input) {
            i.split(": ")[1]
                .split("; ").forEach { s->
                    s.split(", ").forEach { c ->
                        val ci = c.split(" ")
                        map[ci[1]]?.takeIf { it<ci[0].toInt() }?.let {
                            map[ci[1]]=ci[0].toInt()
                        }
                    }
                }
            var pow = 1
            map.keys.forEach {
                pow*=map.getValue(it)
            }
            res+=pow
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day02_test")
    //check(part1(testInput) == 1)

    val input = readInput("day02input.txt")
    part1(input).println()
    part2(input).println()
}