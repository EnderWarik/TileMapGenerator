import PerlinNoise.PerlinNoise2D
import documentGenerator.TileMapFileGenerator
import view.TileMapView
import java.io.File
import javax.imageio.ImageIO


//указывать откуда брать ассеты.
fun main() {
    //тестирование машинного зрения.
    // детерминированные процессы
    // алгоритм построения карты для начала первую версию.

    val width = 512
    val height = 512
    val noise = PerlinNoise2D(width,height,123).turbulence(64f, 0.5f,1).normalize()

    val tileMapView = TileMapView()
//    tileMapView.ViewMap(noise)


    val tileMapFileGenerator = TileMapFileGenerator("/example/start_map.png",16,16,2,4)
    tileMapFileGenerator.generate()
//    for (y in 0 until height) {
//        for (x in 0 until width) {
//            val value = (noiseMap[x][y] * 255).toInt()
//
//            print(String.format("%3d ", value))
//        }
//        println()
//    }

}
fun isFileIncludedInBuild(filePath: String): Boolean {
    val resource = ClassLoader.getSystemClassLoader().getResource(filePath)
    return resource != null
}
