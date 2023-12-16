data class MapData(val num: Long, val inp: List<String>) {
    fun parseNum(s: String, n: Long): Long {
        val start = inp.indexOf(inp.find { it==s })+1
        val sm = getMap(inp,start)
        sm.forEach {
            val split = it.split(Regex("\\s+"))
            val x=split[0].toLong()
            val y=split[1].toLong()
            val z=split[2].toLong()
            if (n in y..<y+z)
                return n-y+x
        }
        return n
    }

    val soil = parseNum("seed-to-soil map:",num)
    val fertilizer = parseNum("soil-to-fertilizer map:", soil)
    val water = parseNum("fertilizer-to-water map:", fertilizer)
    val light = parseNum("water-to-light map:", water)
    val temperature = parseNum("light-to-temperature map:", light)
    val humidity = parseNum("temperature-to-humidity map:", temperature)
    val location = parseNum("humidity-to-location map:", humidity)
}

fun main() {
    fun part1(input: List<String>): Long {
        val resList = mutableListOf<MapData>()
        val sm = Regex("\\d+").toPattern().matcher(input[0])
        while (sm.find())
            resList.add(MapData(sm.group().toLong(),input))
        return resList.minOf { it.location }
    }

    fun part2(input: List<String>): Long {
        val res = input.first().substringAfter(" ").split(" ").map { it.toLong() }.chunked(2)
                .map { it.first()..<it.first()+it.last() }
        val cat = input.drop(2).joinToString("\n").split("\n\n").map { s->
            s.lines().drop(1).associate {
                it.split(" ").map { it.toLong() }.let { (dst,src,l) ->
                    src..(src+l) to dst..(dst+l)
                }
            }
        }
        return res.flatMap { sr->
            cat.fold(listOf(sr)) {aac,m->
                aac.flatMap {
                    m.entries.mapNotNull { (src,dst)->
                        (maxOf(src.first,it.first) to minOf(src.last,it.last)).let { (start,end) ->
                            if (start<=end) (dst.first-src.first).let { (start+it)..(end+it) } else null
                        }
                    }
                }
            }
        }.minOf { it.first }
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day05input.txt")
    part1(input).println()
    part2(input).println()
}
