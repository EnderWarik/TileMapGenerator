
enum class ColorType
{
    HEX
}
class Color(private val color:Int,type: ColorType = ColorType.HEX)
{

    fun getColor(): Int
    {
        return this.color
    }

}