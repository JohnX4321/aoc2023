fun main() {
    fun part1(input: List<String>): Int {

        val m = input.map {
            val s = it.split(' ')
            Model(s[0],s[1].toInt())
        }
        val sm = mutableMapOf<TYPE, List<Model>>()
        m.groupBy { it.type }.toSortedMap(compareBy { it.n }).forEach {
            val sb = it.value.sortedBy { it.value.sortByLabel() }
            sm[it.key]=sb
        }
        var r=1
        var res=0
        sm.forEach {
            it.value.forEach {
                res+=it.x*r
                r++
            }
        }
        return res
    }

    fun part2(input: List<String>): Int {
        val m = input.map {
            val s = it.split(' ')
            Model(s[0],s[1].toInt())
        }
        val sm = mutableMapOf<TYPE, List<Model>>()
        m.groupBy { it.type }.toSortedMap(compareBy { it.n }).forEach {
            val sb = it.value.sortedBy { it.value.sortByLabel2() }
            sm[it.key]=sb
        }
        var r=1
        var res=0
        sm.forEach {
            it.value.forEach {
                res+=it.x*r
                r++
            }
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day07input.txt")
    part1(input).println()
    part2(input).println()
}

data class Model(val value: String, val x: Int, val y: Boolean = false) {
    override fun toString(): String {
        return "Model(v=$value,x=$x,type=$type"
    }

    val type = if (y) getT2(value) else getT1(value)

    private fun getT1(value: String): TYPE {
        val aw = value.chunked(1).distinct().associateWith { c -> value.count { it==c[0] } }.toSortedMap()
        return if (aw.maxOf { it.value==5 }) TYPE.FIVE
        else if (aw.maxOf { it.value==4 }) TYPE.FOUR
        else if (aw.maxOf { it.value }==3 && aw.minOf { it.value }==2) TYPE.FULL
        else if (aw.maxOf { it.value }==3 && aw.minOf { it.value }==1) TYPE.THREE
        else if (aw.size==3) TYPE.TWO
        else if (aw.size==4) TYPE.ONE
        else TYPE.HIGH
    }


    private fun getT2(value: String): TYPE {
        if (value=="JJJJJ") return TYPE.FIVE
        val aw = value.chunked(1).distinct().associateWith { c -> value.count { it==c[0] } }.toSortedMap()
        var kmv=""
        val jv=aw["J"] ?: 0
        val z=aw.toMutableMap()
        z.remove("J")
        z.forEach{if (it.value==z.maxOf { it.value }) kmv=it.key}
        z[kmv]=z[kmv]?.plus(jv)
        z.remove("J")
        return if (z.maxOf { it.value==5 }) TYPE.FIVE
        else if (z.maxOf { it.value==4 }) TYPE.FOUR
        else if (z.maxOf { it.value }==3 && z.minOf { it.value }==2) TYPE.FULL
        else if (z.maxOf { it.value }==3 && z.minOf { it.value }==1) TYPE.THREE
        else if (z.size==3) TYPE.TWO
        else if (z.size==4) TYPE.ONE
        else TYPE.HIGH
    }
}

val labels: Map<String, String> = mapOf(Pair("A", "E"), Pair("K", "D"), Pair("Q", "C"), Pair("J", "B"), Pair("T", "A"))
val labels2: Map<String, String> = mapOf(Pair("A", "E"), Pair("K", "D"), Pair("Q", "C"), Pair("J", "0"), Pair("T", "A"))

fun String.sortByLabel(): String {
    var newString = ""
    this.forEach {
        newString += if (it.isDigit()) it else labels[it.toString()]
    }
    return newString
}

fun String.sortByLabel2(): String {
    var newString = ""
    this.forEach {
        newString += if (it.isDigit()) it else labels2[it.toString()]
    }
    return newString
}


enum class TYPE(val n: Int) {
    FIVE(6), FOUR(5), FULL(4), THREE(3), TWO(2), ONE(1), HIGH(0)
}

