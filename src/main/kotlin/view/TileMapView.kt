package view

import PerlinNoise.PerlinNoise2D
import models.Color
import java.awt.*
import java.awt.image.BufferedImage
import javax.swing.*


class TileMapView {


    fun ViewMap(noise: PerlinNoise2D) {

        noise.showImg { getPixelColor(it) }
    }

    fun getPixelColor(value: Float): Int
    {
        val DeepWater = 0.1f
        val ShallowWater = 0.3f
        val Sand = 0.5f
        val Grass = 0.7f
        val Forest = 0.8f
        val Rock = 0.9f
        val Snow = 1f

        val DeepColor = Color(0x000050)
        val ShallowColor = Color(0x007eb4)
        val SandColor = Color(0xf0d654)
        val GrassColor = Color(0x3aa827)
        val ForestColor = Color(0x117007)
        val RockColor = Color(0x808080)
        val SnowColor = Color(0xffffff)

        return when(value)
        {
            in 0f..DeepWater -> DeepColor.getColor()
            in DeepWater..ShallowWater -> ShallowColor.getColor()
            in ShallowWater..Sand -> SandColor.getColor()
            in Sand..Grass -> GrassColor.getColor()
            in Grass..Forest -> ForestColor.getColor()
            in Forest..Rock -> RockColor.getColor()
            in Rock..Snow -> SnowColor.getColor()

            else -> { DeepColor.getColor()}
        }
    }
}