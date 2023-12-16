import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val ire = mutableListOf<Int>()
        val ice = mutableListOf<Int>()
        val m = input.map { it.map { it }.toCharArray() }.toMutableList()
        m.forEachIndexed {ir,s ->
            if (s.count { it=='.' }==s.size)
                ire.add(ir)
        }
        val tm=transpose(m)
        tm.forEachIndexed { ic,c ->
            if (c.count { it=='.' }==c.size)
                ice.add(ic)
        }
        ire.forEachIndexed { index, i -> m.add(i+index, CharArray(m.first().size){'.'})  }
        val tm1=transpose(m)
        ice.forEachIndexed { index, i -> tm1.add(i+index, CharArray(tm1.first().size){'.'}) }
        val mw = transpose(tm1)
        val gal = mutableMapOf<Int,Pair<Int,Int>>()
        var ct =1
        mw.forEachIndexed { index, chars ->
            chars.forEachIndexed { ic, c ->
                if (c=='#') gal[ct++]=Pair(index,ic)
            }
        }
        var res=0
        gal.forEach { i,c->
            for (k in i+1..gal.size)
                res+= abs(c.first-gal[k]!!.first)+abs(c.second-gal[k]!!.second)
        }
        return res
    }

    fun part2(input: List<String>): Long {
        val ire = mutableListOf<Int>()
        val ice = mutableListOf<Int>()
        val m = input.map { it.map { it }.toCharArray() }.toMutableList()
        m.forEachIndexed {ir,s ->
            if (s.count { it=='.' }==s.size)
                ire.add(ir)
        }
        transpose(m).forEachIndexed { index, chars ->
            if (chars.count { it=='.' }==chars.size) ice.add(index)
        }
        val gal = mutableMapOf<Int,Pair<Int,Int>>()
        var ct=1
        m.forEachIndexed { index, chars ->
            chars.forEachIndexed { ic, c ->
                if (c=='#') gal[ct++]=Pair(index,ic)
            }
        }
        val tl=1_000_000
        val ae = gal.map { (i,c) ->
            val row = if (c.first<ire.first()) c.first else (ire.indexOf(ire.last { it<=c.first })+1)*tl+c.first-(ire.indexOf(ire.last { it<=c.first })+1)
            val col = if (c.second<ice.first()) c.second else (ice.indexOf(ice.last { it<=c.second })+1)*tl+c.second-(ice.indexOf(ice.last { it<=c.second })+1)
            i to Pair(row,col)
        }.toMap()
        var res=0L
        ae.forEach { (i,c) ->
            for (k in i+1..ae.size) {
                res+=abs(c.first-ae[k]!!.first)+abs(c.second-ae[k]!!.second)
            }
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day11input.txt")
    part1(input).println()
    part2(input).println()
}
