package documentGenerator

import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

class XmlGenerator
{
    inline fun <reified T> generate(fileName: String, exemplar: T)
    {

            val jaxbContext: JAXBContext = JAXBContext.newInstance(T::class.java)

            // Создаем Marshaller
            val marshaller: Marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Записываем объекты в XML-файл
            val outputFile: File = File(fileName);
            marshaller.marshal(exemplar, outputFile);

    }

}