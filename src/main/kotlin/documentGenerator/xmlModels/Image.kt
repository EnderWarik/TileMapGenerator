package documentGenerator.xmlModels

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "image")
@XmlAccessorType(XmlAccessType.FIELD)

class Image
(
    @XmlAttribute()
    var source: String? = null,

    @XmlAttribute()
    var width: String? = null,

    @XmlAttribute()
    var height: String? = null
)
{

}