package com.example.pokemontcg.domain.model

sealed class DeckNumber {

    object First : DeckNumber()
    object Second : DeckNumber()
    object Third : DeckNumber()


    companion object {
        fun fromInt(number  : Int) : DeckNumber{
            return when(number){
                1 -> First
                2 -> Second
                3 -> Third
                else -> First
            }
        }
    }

}

fun DeckNumber.toInt() : Int{
    return when(this){
        is DeckNumber.First -> 1
        DeckNumber.Second -> 2
        DeckNumber.Third -> 3
    }
}

