package documentGenerator.xmlModels

import documentGenerator.xmlModels.interfaces.Tileset
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlAdapter
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "map")
@XmlAccessorType(XmlAccessType.FIELD)
class Map(
    @XmlAttribute()
    var version: String? = null,

    @XmlAttribute()
    var  orientation: String,

//    @XmlAttribute()
//    var renderorder: String? = null,

    @XmlAttribute()
    var width: String? = null,

    @XmlAttribute()
    var height: String? = null,

    @XmlAttribute(name = "tilewidth")
    var tileWidth: String,

    @XmlAttribute(name = "tileheight")
    var tileHeight: String,

    @XmlAttribute()
    var infinite: String,

//    @XmlAttribute()
//    var nextlayerid: String? = null,
//
//    @XmlAttribute()
//    var nextobjectid: String? = null,


    @XmlElementWrapper(name="properties")
    @XmlElement(name="property")
    val properties: MutableList<Property> = mutableListOf(),


    @XmlElement(name="tileset")
    var tileset: MutableList<Tileset> = mutableListOf()
)
{
    constructor():this(
        orientation = "orthogonal",
        infinite = "0",
        tileHeight = "16",
        tileWidth = "16"
    )
}

class TilesetImageAdapter : XmlAdapter<Any, Tileset>() {
    override fun marshal(value: Tileset): Any {
        return when(value)
        {
            is TilesetTsx -> value
            is TilesetImage -> value
            else ->
            {
                throw Exception("Unresolved type")
            }
        }
    }

    override fun unmarshal(value: Any): Tileset {
        return value as Tileset // Convert TilesetImpl back to Tileset
    }
}
