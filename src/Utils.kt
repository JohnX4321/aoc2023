import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Char.isSymbol() = !isDigit() || this=='.'

fun getMap(inp: List<String>, start: Int): MutableList<String> {
    val z = mutableListOf<String>()
    var end = inp.size
    for (i in start..<end) {
        if (inp[i].isEmpty()) {
            end=i
            break
        }
    }
    for (i in start..<end) {
        if (Regex("\\d+\\s+\\d+\\s+\\d+").find(inp[i])!=null)
            z.add(inp[i])
    }
    return z
}

fun lcm(a: Long,b: Long) = a*(b/gcd(a,b))

fun lcm(a: LongArray): Long {
    var res=a[0]
    for (i in 1..a.lastIndex)
        res=lcm(res,a[i])
    return res
}

fun gcd(a: Long, b: Long): Long {
    if (b==0L) return a
    return gcd(b,a%b)
}

fun transpose(mat: MutableList<CharArray>): MutableList<CharArray> {
    val r = mat.size
    val c=mat[0].size
    val tm = Array(c) {Array(r) {'0'} }
    for (i in 0 until r)
        for (j in 0 until c)
            tm[j][i]=mat[i][j]
    return tm.map { it.toCharArray() }.toMutableList()
}