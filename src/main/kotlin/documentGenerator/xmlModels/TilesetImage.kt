package documentGenerator.xmlModels

import documentGenerator.xmlModels.interfaces.Tileset
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "tileset")
@XmlAccessorType(XmlAccessType.FIELD)

class TilesetImage(
    @XmlAttribute()
    var version: String? = null,

    @XmlAttribute()
    var name: String? = null,

    @XmlAttribute()
    var tilewidth: String? = null,

    @XmlAttribute()
    var tileheight: String? = null,

    @XmlAttribute()
    var spacing: String? = null,

    @XmlAttribute()
    var margin: String? = null,

    @XmlAttribute()
    var tilecount: String? = null,

    @XmlAttribute()
    var columns: String? = null,

    @XmlElement(name="image")
    var image: Image? = null,
)  : Tileset
