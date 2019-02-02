package com.kanawish.thing.robot

import android.os.Build

/**
 * NOTE: Only went with popular/latest devices.
 */
enum class Board(val devId: String) {
    Board_RPI3("rpi3"),
    Board_IMX7D_PICO("imx7d_pico"),
    UNKNOWN("")
}

/**
 * see
 * https://developer.android.com/things/hardware/imx7d-pico-io.html
 * https://developer.android.com/things/hardware/raspberrypi-io.html
 */
val map = hashMapOf(
        13 to ("BCM27" to "GPIO_35"),
        15 to ("BCM22" to "GPIO_10"),
        22 to ("BCM25" to "GPIO_128"),
        7 to ("BCM4" to "UART6"),
        29 to ("BCM5" to "GPIO_33"),
        31 to ("BCM6" to "GPIO_34")
        )

fun currentDevice(): Board = (Board.values().find { it.devId == Build.DEVICE } ?: Board.UNKNOWN)

fun Board.i2cBus():String = when(this) {
    Board.Board_RPI3, Board.Board_IMX7D_PICO -> "I2C1"
    Board.UNKNOWN -> ""
}
fun gpioPin(number:Int) = map.get(number)?.let { (rpi,mx7) ->
    when (currentDevice()) {
        Board.Board_RPI3 -> rpi
        Board.Board_IMX7D_PICO -> mx7
        Board.UNKNOWN -> throw IllegalStateException("currentDevice() is UNKNOWN.")
    }
}
