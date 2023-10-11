package documentGenerator.xmlModels

import documentGenerator.xmlModels.interfaces.Tileset
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "tileset")
@XmlAccessorType(XmlAccessType.FIELD)

class TilesetTsx(
    @XmlAttribute()
    var firstId: String? = null,

    @XmlAttribute()
    var source: String? = null,
) : Tileset

