package documentGenerator.xmlModels.interfaces



import documentGenerator.xmlModels.TilesetImageAdapter


import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters



@XmlJavaTypeAdapter(TilesetImageAdapter::class)
interface Tileset
{
}