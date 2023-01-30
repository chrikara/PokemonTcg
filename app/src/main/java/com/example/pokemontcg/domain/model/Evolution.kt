package com.example.pokemontcg.domain.model

sealed class Evolution(){

    data class Both(
        val from: String?,
        val initial : String?,
        val to : String?) : Evolution()
    data class From(
        val from : String?,
        val initial : String?
        ) : Evolution()
    data class To(
        val initial : String?,
        val to : String?): Evolution()
    object None : Evolution()

    companion object{
        var initial = ""

        fun initialEvolution(newInitial: String) {
            initial = newInitial
        }
        fun returnEvolution(from : String ?,  to: String?) :Evolution{

            return when{
                from != null && to != null -> Both(from ,initial , to)
                from != null -> From(from, initial)
                to!= null -> To(to, initial)
                else -> None
            }

        }
    }
}