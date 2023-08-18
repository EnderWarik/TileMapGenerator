import kotlin.math.sqrt
import kotlin.random.Random

class PerlinNoise2D(private val width: Int, private val height: Int, private val seed: Long, private val permutationTableSize: Int = 256 )
{
    private var noiseMap: Array<FloatArray> = Array(width) { FloatArray(height) }

    private val permutationTable: IntArray = IntArray(permutationTableSize)

    private var isFill: Boolean = false

    private val gradients = arrayOf(
        Gradient(1f, 0f),
        Gradient(-1f, 0f),
        Gradient(0f, 1f),
        Gradient(0f, -1f),
        Gradient(1f, 1f).normalize(),
        Gradient(-1f, 1f).normalize(),
        Gradient(1f, -1f).normalize(),
        Gradient(-1f, -1f).normalize()
    )
    init
    {
        val random = Random(seed)
        for(i in permutationTable.indices)
        {
            permutationTable[i] = random.nextInt(permutationTableSize)
        }
    }
    fun randomFilling(): PerlinNoise2D
    {
        val random = Random(seed)
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                noiseMap[i][j] = random.nextFloat()
            }
        }
        isFill = true
        return this
    }

    fun getNoiseMap(): Array<FloatArray>
    {
        return noiseMap
    }

    fun smoothNoise(zoom: Int = 1) : PerlinNoise2D
    {
        val result: Array<FloatArray> = Array(width) { FloatArray(height) }
        if(!isFill)this.randomFilling()
        val isVertex: Boolean = zoom < 2
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                result [i][j] = this.getValueSmoothNoiseFrom(i.toFloat()/zoom,j.toFloat()/zoom,isVertex)
            }
        }
        noiseMap = result
        return this
    }
    fun getPerlinNoiseNoiseMap(zoom: Int = 2,persistence:Float = 1f): Array<FloatArray>
    {
        val result: Array<FloatArray> = Array(width) { FloatArray(height) }
        if(!isFill)this.randomFilling()
        val isVertex: Boolean = zoom < 2
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                result[i][j] = this.getValuePerlinNoiseFrom(i.toFloat()/zoom,j.toFloat()/zoom,isVertex) * persistence
            }
        }

        return result
    }
    fun getSmoothNoiseNoiseMap(zoom: Int = 1, persistence:Float = 1f) : Array<FloatArray>
    {
        val result: Array<FloatArray> = Array(width) { FloatArray(height) }
        if(!isFill)this.randomFilling()
        val isVertex: Boolean = zoom < 2
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                result [i][j] = this.getValueSmoothNoiseFrom(i.toFloat()/zoom,j.toFloat()/zoom,isVertex) * persistence
            }
        }

        return result
    }
    fun getValueSmoothNoiseFrom(x:Float, y:Float,isVertex:Boolean = false): Float
    {

        val x0 = x.toInt()   //левый верхний угол
        val y0 = y.toInt()  //левый верхний угол

        val x1 = (x0 + 1) % width
        val y1 = (y0 +  1) % height

        var fractionX = x - x0
        var fractionY = y - y0
        if(isVertex)
        {
            if(fractionX == 0f) fractionX = 0.5f
            if(fractionY == 0f) fractionY = 0.5f
        }
        val top = bilinearInterpolation(noiseMap[x0][y0],noiseMap[x1][y0],fractionX)
        val bottom = bilinearInterpolation(noiseMap[x0][y1],noiseMap[x1][y1],fractionX)

        val middle = bilinearInterpolation(top,bottom,fractionY)

        return middle
    }


    fun perlinNoise(zoom: Int = 2): PerlinNoise2D
    {
        val result: Array<FloatArray> = Array(width) { FloatArray(height) }
        if(!isFill)this.randomFilling()
        val isVertex: Boolean = zoom < 2
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                result[i][j] = this.getValuePerlinNoiseFrom(i.toFloat()/zoom,j.toFloat()/zoom,isVertex)
            }
        }
        noiseMap = result
        return this
    }

    fun getValuePerlinNoiseFrom(x:Float, y:Float,isVertex:Boolean = false): Float
    {
        val x0: Int = x.toInt()
        val y0:Int  = y.toInt()
        val x1 = x0 + 1
        val y1 = y0 +  1
        var fractionX: Float = x - x0
        var fractionY: Float  = y - y0
        if(isVertex)
        {
            if(fractionX == 0f) fractionX = 0.5f
            if(fractionY == 0f) fractionY = 0.5f
        }
        val topLeftGradient: Gradient = gradient(x0, y0)
        val topRightGradient: Gradient = gradient(x1, y0)
        val bottomLeftGradient: Gradient = gradient(x0, y1)
        val bottomRightGradient: Gradient = gradient(x1, y1)

        val topX0 = topLeftGradient.dot(fractionX,   fractionY) // arg: distance to top left
        val topX1 = topRightGradient.dot(fractionX-1, fractionY) // arg: distance to top right
        val bottomX0 = bottomLeftGradient.dot(fractionX,   fractionY-1) // arg: distance to bottom left
        val bottomX1 = bottomRightGradient.dot(fractionX-1, fractionY-1) // arg: distance to bottom right

        fractionX = qunticCurve(fractionX)
        fractionY = qunticCurve(fractionY)

        val top: Float = bilinearInterpolation(topX0, topX1, fractionX)
        val bottom = bilinearInterpolation(bottomX0, bottomX1, fractionX)
        val middle = bilinearInterpolation(top, bottom, fractionY)
        return middle
    }

    fun normalize(): PerlinNoise2D
    {
        var minValue = noiseMap[0][0]
        var maxValue =  noiseMap[0][0]
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                val value = noiseMap[i][j]
                if (value < minValue){
                    minValue = value
                } else if (value > maxValue){
                    maxValue = value
                }
            }
        }
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                noiseMap[i][j] =  (noiseMap[i][j] - minValue) / (maxValue - minValue)
            }
        }

        return this
    }

    fun turbulence(octaves: Float, persistence: Float, zoom: Int = 8): PerlinNoise2D {
        if(!isFill)this.randomFilling()
        val result: Array<FloatArray> = Array(width) { FloatArray(height) }
        val initialOctaves = octaves
        var octaves = octaves * zoom
        var persistence = persistence
        while (octaves >= 1) {
            result.addMatrix(getPerlinNoiseNoiseMap(octaves.toInt(),persistence))
            octaves /= 2f
            persistence*=0.5f
        }
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                result[i][j] = 128f * result[i][j] / initialOctaves
            }
        }
        noiseMap = result
        return this
    }
    private fun Array<FloatArray>.addMatrix(b: Array<FloatArray>,persistence: Float = 0.5f): Array<FloatArray>
    {
        for(i in 0 until width)
        {
            for(j in 0 until height)
            {
                this[i][j] = this[i][j] + b[i][j] * persistence
            }
        }
        return this
    }


    private fun bilinearInterpolation(leftValue:Float, rightValue:Float, fractionX: Float): Float
    {
        return leftValue + fractionX*(rightValue-leftValue)
    }
    fun qunticCurve(t: Float):Float
    {
        return t * t * t * (t * (t * 6 - 15) + 10)
    }
    fun gradient(x: Int, y: Int): Gradient
    {
        val index = permutationTable[(permutationTable[x % permutationTableSize] + y) % permutationTableSize]
        return gradients[index % 8]
    }
    class Gradient(val x: Float, val y: Float)
    {
        fun dot(dx: Float, dy: Float) = x * dx + y * dy
        fun normalize(): Gradient {
            val length = sqrt(x * x + y * y)
            return Gradient(x / length, y / length)
        }
    }
}
