fun main() {
    val width = 32
    val height = 32
    val generator = PerlinNoise2D(123)
    val noiseMap = Array(width) { x ->
        Array(height) { y ->
            generator.perlinNoise2D(x.toDouble() / width, y.toDouble() / height)
        }
    }
    for (y in 0 until height) {
        for (x in 0 until width) {
            val value = (noiseMap[x][y] * 255).toInt()
            print(String.format("%3d ", value))
        }
        println()
    }
}
