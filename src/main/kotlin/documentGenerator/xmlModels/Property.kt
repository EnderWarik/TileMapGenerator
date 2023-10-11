package documentGenerator.xmlModels

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute


@XmlAccessorType(XmlAccessType.FIELD)
class Property
(
    @XmlAttribute()
    var name: String,

    @XmlAttribute()
    var value: String,
)
{
}