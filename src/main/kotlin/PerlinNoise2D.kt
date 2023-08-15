
import kotlin.math.*
import kotlin.random.Random

class PerlinNoise2D(private val seed: Long) {

    private val permutationTable = IntArray(256)

    init {
        for (i in 0 until 256) {
            permutationTable[i] = i
        }
        permute(seed, permutationTable)
    }

    fun permute(seed: Long, array: IntArray) {
        val random = Random(seed)
        for (i in array.size - 1 downTo 1) {
            val j = random.nextInt(i + 1)
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
        }
    }

    // функция вычисляющаяя значение шума в заданной точке
     fun perlinNoise2D(x: Double, y: Double): Double {
        // Определение координат узлов сетки
        val x0 = x.toInt()
        val x1 = x0 + 1
        val y0 = y.toInt()
        val y1 = y0 + 1

        // Вычисление векторов от узлов сетки до заданной точки
        val dx0 = x - x0
        val dx1 = x - x1
        val dy0 = y - y0
        val dy1 = y - y1

        // Вычисление значений градиентов в каждом узле сетки
        val grad00 = gradient(x0, y0)
        val grad01 = gradient(x0, y1)
        val grad10 = gradient(x1, y0)
        val grad11 = gradient(x1, y1)

        // Вычисление скалярных произведений векторов и градиентов
        val dot00 = grad00.dot(dx0, dy0)
        val dot01 = grad01.dot(dx0, dy1)
        val dot10 = grad10.dot(dx1, dy0)
        val dot11 = grad11.dot(dx1, dy1)

        // интерполяуия и сглаживание
        val weightX = fade(dx0)
        val weightY = fade(dy0)
        val lerp0 = lerp(dot00, dot10, weightX)
        val lerp1 = lerp(dot01, dot11, weightX)
        return lerp(lerp0, lerp1, weightY)
    }

    private fun fade(t: Double) = t * t * t * (t * (t * 6 - 15) + 10)

    private fun lerp(a: Double, b: Double, t: Double) = a + t * (b - a)



    data class Gradient(val x: Double, val y: Double) {
        fun dot(dx: Double, dy: Double) = x * dx + y * dy
    }

    private val gradients = arrayOf(
        Gradient(1.0, 0.0),
        Gradient(-1.0, 0.0),
        Gradient(0.0, 1.0),
        Gradient(0.0, -1.0),
        Gradient(1.0, 1.0).normalize(),
        Gradient(-1.0, 1.0).normalize(),
        Gradient(1.0, -1.0).normalize(),
        Gradient(-1.0, -1.0).normalize()
    )

    fun gradient(x: Int, y: Int): Gradient {
        val index = permutationTable[(permutationTable[x % 256] + y) % 256]
        return gradients[index % 8]
    }

    fun Gradient.normalize(): Gradient {
        val length = sqrt(x * x + y * y)
        return Gradient(x / length, y / length)
    }

}
