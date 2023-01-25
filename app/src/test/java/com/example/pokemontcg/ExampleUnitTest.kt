package com.example.pokemontcg

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(numberWithThreeDigits(1))
        println(numberWithThreeDigits(30))
        println(numberWithThreeDigits(400))
    }
}

fun numberWithThreeDigits(number : Int):String{
    return when (number.toString().length){
        1 -> "00$number"
        2 -> "0$number"
        else -> number.toString()
    }
}