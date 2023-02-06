package com.example.pokemontcg.domain.model

data class Gym(
    val name : String,
    val value : String,
    val enabled : Boolean = false,
    val leaderUrl : String,
    val isCurrent: Boolean = false
)

val defaultGyms = listOf<Gym>(
    Gym(
        name = "Pewter",
        value = "I",
        enabled = true,
        isCurrent = true,
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/30.png",
    ),
    Gym(
        name = "Cerulean",
        value = "II",
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/31.png",
        enabled = false,

    ),
    Gym(
        name = "Vermillion",
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/32.png",
        value = "III",
    ),
    Gym(
        name = "Celadon",
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/33.png",
        value = "IV"
    ),
    Gym(
        name = "Fuschia",
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/34.png",
        value = "V"
    ),
    Gym(
        name = "Saffron",
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/35.png",
        value = "VI"
    ),
    Gym(
        name = "Cinnabar",
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/36.png",
        value = "VII"
    ),
    Gym(
        name = "Viridian",
        leaderUrl = "https://www.serebii.net/pokearth//trainers/frlg/37.png",
        value = "VIII"
    ),
)

