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

        fun fromString(deckString : String) : DeckNumber{
            return when(deckString){
                "1st" -> First
                "2nd" -> Second
                "3rd" -> Third
                else -> Second
            }
        }
    }

}
fun DeckNumber.toNumberString() : String{
    return when(this) {
        is DeckNumber.First -> "1st"
        is DeckNumber.Second -> "2nd"
        is DeckNumber.Third -> "3rd"
    }
}
fun DeckNumber.toInt() : Int{
    return when(this){
        is DeckNumber.First -> 1
        DeckNumber.Second -> 2
        DeckNumber.Third -> 3
    }
}

