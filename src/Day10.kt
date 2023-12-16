fun main() {
    fun part1(input: List<String>): Int {
        var start = Pair(0,0)
        val pw = mutableListOf<Pair<Int,Int>>()
        input.forEachIndexed { ix,s->
            if (s.contains("S")) {
                start=Pair(ix,s.indexOf("S"))
                if ((start.first-1)>=0 && canProceed1(Pair(start.first-1,start.second),input,start)!=null)
                    pw.add(Pair(start.first-1,start.second))
                if ((start.first+1)<input.size&&canProceed1(Pair(start.first+1,start.second),input,start)!=null)
                    pw.add(Pair(start.first+1,start.second))
                if ((start.second-1)>=0 && canProceed1(Pair(start.first,start.second-1),input,start)!=null)
                    pw.add(Pair(start.first,start.second-1))
                if ((start.second-1)<input.size && canProceed1(Pair(start.first,start.second+1),input,start)!=null)
                    pw.add(Pair(start.first,start.second+1))
                return@forEachIndexed
            }
        }
        var res=0
        val vis=Array(input.size) {BooleanArray(input.first().length) {false} }
        val dis = Array(input.size) {IntArray(input.first().length) {-1} }
        vis[start.first][start.second]=true
        dis[start.first][start.second]=0
        pw.forEach {
            var ls = 0
            var pp=it
            var vec = canProceed2(it,input,canProceed1(it,input,start)!!)
            vis[pp.first][pp.second]=true
            dis[pp.first][pp.second]=1
            while (vec!=null) {
                val np = when(vec) {
                    DIR.N -> Pair(pp.first-1,pp.second)
                    DIR.E -> Pair(pp.first,pp.second+1)
                    DIR.S -> Pair(pp.first+1,pp.second)
                    DIR.W -> Pair(pp.first,pp.second-1)
                }
                ls++
                if (ls<dis[pp.first][pp.second] || dis[pp.first][pp.second]==-1)
                    dis[pp.first][pp.second]=ls
                if (vis[np.first][np.second]) return@forEach
                vec=canProceed2(np,input,vec)
                pp=np
            }
            res=if (res<ls) ls else res
        }
        return dis.maxOf { it.maxOf { it } }
    }

    fun part2(input: List<String>): Int {
        var start = Pair(0, 0)

        val pw = mutableListOf<Pair<Pair<Int, Int>, DIR>>()
        input.forEachIndexed { index, s ->
            if (s.contains("S")) {
                start = Pair(index, s.indexOf("S"))
                if ((start.first - 1) >= 0 &&
                        canProceed1(Pair(start.first - 1, start.second), input, start) != null
                ) pw.add(
                        Pair(
                                Pair(start.first - 1, start.second),
                                canProceed1(Pair(start.first - 1, start.second), input, start)!!
                        )
                )
                if ((start.first + 1) < input.size &&
                        canProceed1(Pair(start.first + 1, start.second), input, start) != null
                ) pw.add(
                        Pair(
                                Pair(start.first + 1, start.second),
                                canProceed1(Pair(start.first + 1, start.second), input, start)!!
                        )
                )
                if ((start.second - 1) >= 0 &&
                        canProceed1(Pair(start.first, start.second - 1), input, start) != null
                ) pw.add(
                        Pair(
                                Pair(start.first, start.second - 1),
                                canProceed1(Pair(start.first, start.second - 1), input, start)!!
                        )
                )
                if ((start.second - 1) < input.first().length &&
                        canProceed1(Pair(start.first, start.second + 1), input, start) != null
                ) pw.add(
                        Pair(
                                Pair(start.first, start.second + 1),
                                canProceed1(Pair(start.first, start.second + 1), input, start)!!
                        )
                )
                return@forEachIndexed
            }
        }
        val map = pw.map { it.second }
        val input = input.map { it }.toMutableList()
        val replacedWith = when {
            map.contains(DIR.N) && map.contains(DIR.W) -> "J"
            map.contains(DIR.N) && map.contains(DIR.E) -> "L"
            map.contains(DIR.S) && map.contains(DIR.W) -> "7"
            map.contains(DIR.S) && map.contains(DIR.E) -> "F"
            map.contains(DIR.N) && map.contains(DIR.S) -> "|"
            map.contains(DIR.W) && map.contains(DIR.E) -> "-"
            else -> "S"
        }
        input[start.first] = input[start.first].replace("S", replacedWith)
        var sum = 0
        val visitedArr = Array(input.size) { BooleanArray(input.first().length) { false } }
        val distanceArr = Array(input.size) { IntArray(input.first().length) { -1 } }
        visitedArr[start.first][start.second] = true
        distanceArr[start.first][start.second] = 0

        pw.forEach { (coordinate, direction) ->
            var localSum = 0
            var previousPoint = coordinate
            var vector = canProceed2(coordinate, input, canProceed1(coordinate, input, start)!!)
            visitedArr[previousPoint.first][previousPoint.second] = true
            distanceArr[previousPoint.first][previousPoint.second] = 1
            while (vector != null) {
                val nextPoint = when (vector) {
                    DIR.N -> Pair(previousPoint.first - 1, previousPoint.second)
                    DIR.E -> Pair(previousPoint.first, previousPoint.second + 1)
                    DIR.S -> Pair(previousPoint.first + 1, previousPoint.second)
                    DIR.W -> Pair(previousPoint.first, previousPoint.second - 1)
                    null -> Pair(0, 0)
                }
                localSum++

                if (localSum < distanceArr[previousPoint.first][previousPoint.second] || distanceArr[previousPoint.first][previousPoint.second] == -1) {
                    distanceArr[previousPoint.first][previousPoint.second] = localSum
                }
                if (visitedArr[nextPoint.first][nextPoint.second]) return@forEach
                vector = canProceed2(nextPoint, input, vector!!)

                previousPoint = nextPoint
            }
            sum = if (sum < localSum) localSum else sum
        }
        val visualisedArr = Array(input.size) { Array(input.first().length) { "" } }
        distanceArr.forEachIndexed { indexR, ints ->
            ints.forEachIndexed { indexC, i ->
                visualisedArr[indexR][indexC] = if (i == -1) (-1).toString() else input[indexR][indexC].toString()
            }
        }
        var sumOfInners = 0
        input.forEachIndexed row@{ indexR, s ->
            var inPipe = 0
            var pipeWalls = 0
            var lastC = ' '
            s.forEachIndexed column@{ indexC, c ->
                if (distanceArr[indexR][indexC] == -1) {
                    if (pipeWalls != 0 && pipeWalls % 2 != 0) inPipe++
                } else {
                    if (c != '-') {
                        if (lastC == 'F' && c == 'J' || lastC == 'L' && c == '7')
                            pipeWalls--
                        lastC = c
                        pipeWalls++
                    }
                }
            }
            sumOfInners += inPipe
        }

        return sumOfInners
    }

    // test if implementation meets criteria from the description, like:
    //val testInput = readInput("Day01_test")
    //check(part1(testInput) == 1)

    val input = readInput("day10input.txt")
    part1(input).println()
    part2(input).println()
}

fun canProceed2(pos: Pair<Int,Int>, inp: List<String>, prev: DIR): DIR? {
    val c = inp[pos.first][pos.second]
    when (prev) {
        DIR.N -> {
            when(c) {
                '|'->if (pos.first-1>=0) return DIR.N
                '7'->if (pos.second-1>=0) return DIR.W
                'F'->if (pos.second+1<inp.first().length) return DIR.E
            }
        }
        DIR.E -> {
            when(c) {
                '-'->if (pos.second+1<inp.first().length) return DIR.E
                '7'->if (pos.first+1<inp.size) return  DIR.S
                'J'->if (pos.first-1>=0) return DIR.N
            }
        }
        DIR.S -> {
            when(c) {
                '|'->if (pos.first+1<inp.size) return DIR.S
                'J'->if (pos.second-1>=0) return DIR.W
                'L'->if (pos.second+1<inp.first().length) return DIR.E
            }
        }
        DIR.W -> {
            when(c) {
                '-'->if (pos.second-1>=0) return DIR.W
                'L'->if (pos.first-1>=0) return DIR.N
                'F'->if (pos.first+1<inp.first().length) return DIR.S
            }
        }
    }
    return null
}

fun canProceed1(pos: Pair<Int,Int>, inp: List<String>, prev: Pair<Int,Int>): DIR? {
    if (Pair(pos.first+1,pos.second)==prev && DIR.N.vTiles.contains(inp[pos.first][pos.second])) return DIR.N
    if (Pair(pos.first-1,pos.second)==prev && DIR.S.vTiles.contains(inp[pos.first][pos.second])) return DIR.S
    if (Pair(pos.first,pos.second+1)==prev && DIR.W.vTiles.contains(inp[pos.first][pos.second])) return DIR.W
    if (Pair(pos.first,pos.second-1)==prev && DIR.E.vTiles.contains(inp[pos.first][pos.second])) return DIR.E
    return null
}

enum class DIR(val vTiles: List<Char>) {
    N(listOf('|', '7', 'F')), E(listOf('-', 'J', '7')), S(listOf('|', 'L', 'J')), W(listOf('-', 'L', 'F'))
}
