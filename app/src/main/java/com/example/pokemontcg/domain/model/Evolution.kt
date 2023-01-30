package com.example.pokemontcg.domain.model

sealed class Evolution{

    object Both : Evolution()
    object From : Evolution()
    object To: Evolution()
    object None : Evolution()

    companion object{
        fun returnEvolution(from : String ?, to: String?) :Evolution{

            return when{
                from != null && to != null -> Both
                from != null -> From
                to!= null -> To
                else -> None
            }

        }
    }
}