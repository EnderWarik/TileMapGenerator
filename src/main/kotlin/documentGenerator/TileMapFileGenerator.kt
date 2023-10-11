package documentGenerator

import documentGenerator.xmlModels.Property
import documentGenerator.xmlModels.TilesetImage
import documentGenerator.xmlModels.TilesetTsx
import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller

class TileMapFileGenerator(
    val tilePath: String,
    val tileWith: Int,
    val tileHeight: Int,
    val indentation: Int,
    val interval: Int

)
{
    fun generate()
    {

        val tsxGenerator = TsxGenerator(tileWith,tileHeight,indentation,interval)
        val tsxTileset = tsxGenerator.generate(tilePath, "GeneratedTsx")


        try {
            // Создаем объекты, представляющие данные в XML
            val map:documentGenerator.xmlModels.Map  = documentGenerator.xmlModels.Map(
                orientation = "orthogonal",
                infinite = "0",
                tileHeight = "16",
                tileWidth = "16")

            map.version = "1.1"

            val properties = arrayOf(Property("1","1"),Property("2","2"))

            val tileset = TilesetTsx()
            tileset.source = "./GeneratedTsx.tsx"
            tileset.firstId = tsxTileset.tilecount+1
            map.properties += properties
            map.tileset += tileset


            // Создаем контекст JAXB
            val jaxbContext:JAXBContext = JAXBContext.newInstance(documentGenerator.xmlModels.Map::class.java,TilesetImage::class.java,TilesetTsx::class.java)

            // Создаем Marshaller
            val marshaller: Marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Записываем объекты в XML-файл
            val outputFile: File = File("GeneratedMap.tmx");
            marshaller.marshal(map, outputFile);

            System.out.println("XML-файл успешно создан.");

        } catch ( e: JAXBException) {
            e.printStackTrace();
        }
    }
}
