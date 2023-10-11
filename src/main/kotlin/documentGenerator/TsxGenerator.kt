package documentGenerator

import documentGenerator.xmlModels.Image
import documentGenerator.xmlModels.TilesetImage
import javax.imageio.ImageIO

class TsxGenerator
(
    private val tileWidth: Int,
    private val tileHeight: Int,
    private val indentation: Int,
    private val interval: Int
)
{
    fun generate(tilePath:String, fileName: String): TilesetImage
    {

        val imageStream = javaClass.getResourceAsStream(tilePath)
        val image = ImageIO.read(imageStream)
        val originalWidth = image.width
        val originalHeight = image.height

        // Заданные ширина и высота для разрезания
        val chunkWidth = tileWidth + interval
        val chunkHeight = tileHeight + interval

        // Разделение картинки на части
        var tiles = 0
        var x = indentation
        var y = indentation
        var column = 0
        while (y < originalHeight) {
            column = 0
            while (x < originalWidth) {
//                val width = (if (x + chunkWidth > originalWidth) originalWidth - x else chunkWidth)
//                val height = (if (y + chunkHeight > originalHeight) originalHeight - y else chunkHeight)
//                val subImage = image.getSubimage(x, y, width, height)
                tiles++
                x += chunkWidth
                column++
            }
            x = indentation
            y += chunkHeight
        }

        // Вывод количества частичек и количества колонок
        val countTiles = tiles
        val countColumns = column

        val imageName = tilePath.substringAfterLast("/")
        val tilesetImage = TilesetImage()
        tilesetImage.version = "0.1"
        tilesetImage.name = fileName
        tilesetImage.tilewidth = tileWidth.toString()
        tilesetImage.tileheight = tileHeight.toString()
        tilesetImage.spacing = interval.toString()
        tilesetImage.margin = indentation.toString()
        tilesetImage.tilecount = countTiles.toString()
        tilesetImage.columns = countColumns.toString()

        val imageInfo = Image()
        imageInfo.source = imageName
        imageInfo.height = originalHeight.toString()
        imageInfo.width = originalWidth.toString()

        tilesetImage.image = imageInfo

        val xmlGenerator = XmlGenerator()
        xmlGenerator.generate<TilesetImage>("$fileName.tsx",tilesetImage)

        return tilesetImage
    }

    }
