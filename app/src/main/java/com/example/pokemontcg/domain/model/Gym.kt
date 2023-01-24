package com.example.pokemontcg.domain.model

data class Gym(
    val name : String,
    val value : String,
    val enabled : Boolean = false,
    val leaderUrl : String
)

val defaultGyms = listOf<Gym>(
    Gym(
        name = "Pewter",
        value = "I",
        enabled = true,
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/26.png",
    ),
    Gym(
        name = "Cerulean",
        value = "II",
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/27.png",

        enabled = true

    ),
    Gym(
        name = "Vermillion",
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/28.png",
        value = "III",
    ),
    Gym(
        name = "Celadon",
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/29.png",
        value = "IV"
    ),
    Gym(
        name = "Fuschia",
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/30.png",
        value = "V"
    ),
    Gym(
        name = "Saffron",
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/31.png",
        value = "VI"
    ),
    Gym(
        name = "Cinnabar",
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/32.png",
        value = "VII"
    ),
    Gym(
        name = "Viridian",
        leaderUrl = "https://www.serebii.net/pokearth/trainers/rby/33.png",
        value = "VIII"
    ),
)
