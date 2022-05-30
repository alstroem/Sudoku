package dk.alstroem.logic.data

import kotlin.math.roundToInt
import kotlin.math.sqrt

sealed class GridSize(val count: Int) {

    object Small: GridSize(4)
    object Normal: GridSize(9)
    object Large: GridSize(16)

    val minValue = 1
    val maxValue = count

    val minIndex = 0
    val maxIndex = count.minus(1)

    val valueRange = minValue..maxValue
    val indexRange = minIndex.. maxIndex

    val nonetSize: Int = sqrt(count.toDouble()).roundToInt()
    val nonetRange = minIndex until nonetSize

}
