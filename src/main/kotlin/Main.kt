fun main() {
    val width = 32
    val height = 32
    val noise = PerlinNoise2D(width,height,123).turbulence(64f, 0.5f)

    val noiseMap = noise.getNoiseMap()

    for (y in 0 until height) {
        for (x in 0 until width) {
            val value = (noiseMap[x][y] * 255).toInt()

            print(String.format("%3d ", value))
        }
        println()
    }
}
