import java.awt.Color
import java.util.UUID

enum class PowerStatus{
    ON,
    OFF,
}

class SP110EDataProvider {

    val serviceUUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
    val characteristicUUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")

    //PLEASE NOTE: Anywhere you the byte value 0x9A you can replace it with whatever value you like.

    /**
     * Makes characteristic values to set the power of the lights.
     * @Param powerStatus value from PowerStatus enum, ON or OFF.
     */

    fun makePowerData(powerStatus: PowerStatus) =
        byteArrayOf(
            0x9A.toByte(),
            0x9A.toByte(),
            0x9A.toByte(),
            if (powerStatus == PowerStatus.ON) 0xAA.toByte() else 0xAB.toByte(),
        )

    /**
     * Makes characteristic values to set the color of the lights
     * @param color The color to set.
     */

    fun makeColorData(color: Color) =
        byteArrayOf(
            color.red.toByte(),
            color.green.toByte(),
            color.blue.toByte(),
            0x2C.toByte(),
        )

    /**
     * Makes characteristic values to set the pattern of the lights.
     * @param pattern Pattern is just a simple data class to hold an index and a name. You could replace this with
     * any int in the range 0 - 120.
     */

    fun makePatternData(pattern: Pattern) =
        byteArrayOf(
            pattern.index.toByte(),
            0x9A.toByte(),
            0x9A.toByte(),
            0x2C.toByte(),
        )

    /**
     * Makes characteristic values to set the brightness of the lights.
     * @param brightness How bright to set the lights, values can be in the range 0..255.
     */

    fun makeBrightnessData(brightness: Int) =
        byteArrayOf(
            brightness.coerceIn(0..255).toByte(),
            0x9A.toByte(),
            0x9A.toByte(),
            0x2A.toByte(),
        )

    /**
     * Makes characteristic values to set the speed of the lights.
     * @param speed How fast the patten should be, values can be in the range 0..255.
     */
    fun makeSpeedData(speed: Int) =
        byteArrayOf(
            speed.toByte(),
            0x9A.toByte(),
            0x9A.toByte(),
            0x03.toByte()
        )

    /**
     * Makes characteristic values to set the number of pixels for the lights.
     * @param pixels How many pixels, values can be in the range 0..1024.
     */
    fun makePixelNumberData(pixels: Int): ByteArray{
        val pixelBytes = makePixelBytes(pixels)
        return byteArrayOf(
            pixelBytes.first,
            pixelBytes.second,
            0x9A.toByte(),
            0x2D.toByte(),
        )
    }

    /**
     * Function to split the pixels number into its upper and lower bytes.
     */
    private fun makePixelBytes(pixels: Int): Pair<Byte, Byte>{
        val upperByte = pixels shr 8
        val lowerByte = (pixels shl 8) shr 8
        return Pair(upperByte.toByte(), lowerByte.toByte())
    }


    /**
     * Makes characteristic values to set the order of the light's colors, for use if colors are not being displayed
     * correctly.
     * @param wireOrder enum value representing the color order, this could be replaced with an int in the range 0..5.
     */
    fun makeWireOrderData(wireOrder: WireOrder) =
        byteArrayOf(
            wireOrder.ordinal.toByte(),
            0x9A.toByte(),
            0x9A.toByte(),
            0x3C.toByte(),
        )
}