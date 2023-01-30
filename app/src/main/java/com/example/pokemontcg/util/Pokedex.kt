package com.example.pokemontcg.util

object Pokedex {

val pokedexBaseIdtoNameHash = hashMapOf(
    "base1-1" to "Alakazam",
    "base1-2" to "Blastoise",
    "base1-3" to "Chansey",
    "base1-4" to "Charizard",
    "base1-5" to "Clefairy",
    "base1-6" to "Gyarados",
    "base1-7" to "Hitmonchan",
    "base1-8" to "Machamp",
    "base1-9" to "Magneton",
    "base1-10" to "Mewtwo",
    "base1-11" to "Nidoking",
    "base1-12" to "Ninetales",
    "base1-13" to "Poliwrath",
    "base1-14" to "Raichu",
    "base1-15" to "Venusaur",
    "base1-16" to "Zapdos",
    "base1-17" to "Beedrill",
    "base1-18" to "Dragonair",
    "base1-19" to "Dugtrio",
    "base1-20" to "Electabuzz",
    "base1-21" to "Electrode",
    "base1-22" to "Pidgeotto",
    "base1-23" to "Arcanine",
    "base1-24" to "Charmeleon",
    "base1-25" to "Dewgong",
    "base1-26" to "Dratini",
    "base1-27" to "Farfetch'd",
    "base1-28" to "Growlithe",
    "base1-29" to "Haunter",
    "base1-30" to "Ivysaur",
    "base1-31" to "Jynx",
    "base1-32" to "Kadabra",
    "base1-33" to "Kakuna",
    "base1-34" to "Machoke",
    "base1-35" to "Magikarp",
    "base1-36" to "Magmar",
    "base1-37" to "Nidorino",
    "base1-38" to "Poliwhirl",
    "base1-39" to "Porygon",
    "base1-40" to "Raticate",
    "base1-41" to "Seel",
    "base1-42" to "Wartortle",
    "base1-43" to "Abra",
    "base1-44" to "Bulbasaur",
    "base1-45" to "Caterpie",
    "base1-46" to "Charmander",
    "base1-47" to "Diglett",
    "base1-48" to "Doduo",
    "base1-49" to "Drowzee",
    "base1-50" to "Gastly",
    "base1-51" to "Koffing",
    "base1-52" to "Machop",
    "base1-53" to "Magnemite",
    "base1-54" to "Metapod",
    "base1-55" to "Nidoran♂",
    "base1-56" to "Onix",
    "base1-57" to "Pidgey",
    "base1-58" to "Pikachu",
    "base1-59" to "Poliwag",
    "base1-60" to "Ponyta",
    "base1-61" to "Rattata",
    "base1-62" to "Sandshrew",
    "base1-63" to "Squirtle",
    "base1-64" to "Starmie",
    "base1-65" to "Staryu",
    "base1-66" to "Tangela",
    "base1-67" to "Voltorb",
    "base1-68" to "Vulpix",
    "base1-69" to "Weedle"
)

    val pokedexNationaltoNameHash = hashMapOf(
    "001" to "Bulbasaur",
    "002" to "Ivysaur",
    "003" to "Venusaur",
    "004" to "Charmander",
    "005" to "Charmeleon",
    "006" to "Charizard",
    "007" to "Squirtle",
    "008" to "Wartortle",
    "009" to "Blastoise",
    "010" to "Caterpie",
    "011" to "Metapod",
    "012" to "Butterfree",
    "013" to "Weedle",
    "014" to "Kakuna",
    "015" to "Beedrill",
    "016" to "Pidgey",
    "017" to "Pidgeotto",
    "018" to "Pidgeot",
    "019" to "Rattata",
    "020" to "Raticate",
    "021" to "Spearow",
    "022" to "Fearow",
    "023" to "Ekans",
    "024" to "Arbok",
    "025" to "Pikachu",
    "026" to "Raichu",
    "027" to "Sandshrew",
    "028" to "Sandslash",
    "029" to "Nidoran♀",
    "030" to "Nidorina",
    "031" to "Nidoqueen",
    "032" to "Nidoran♂",
    "033" to "Nidorino",
    "034" to "Nidoking",
    "035" to "Clefairy",
    "036" to "Clefable",
    "037" to "Vulpix",
    "038" to "Ninetales",
    "039" to "Jigglypuff",
    "040" to "Wigglytuff",
    "041" to "Zubat",
    "042" to "Golbat",
    "043" to "Oddish",
    "044" to "Gloom",
    "045" to "Vileplume",
    "046" to "Paras",
    "047" to "Parasect",
    "048" to "Venonat",
    "049" to "Venomoth",
    "050" to "Diglett",
    "051" to "Dugtrio",
    "052" to "Meowth",
    "053" to "Persian",
    "054" to "Psyduck",
    "055" to "Golduck",
    "056" to "Mankey",
    "057" to "Primeape",
    "058" to "Growlithe",
    "059" to "Arcanine",
    "060" to "Poliwag",
    "061" to "Poliwhirl",
    "062" to "Poliwrath",
    "063" to "Abra",
    "064" to "Kadabra",
    "065" to "Alakazam",
    "066" to "Machop",
    "067" to "Machoke",
    "068" to "Machamp",
    "069" to "Bellsprout",
    "070" to "Weepinbell",
    "071" to "Victreebel",
    "072" to "Tentacool",
    "073" to "Tentacruel",
    "074" to "Geodude",
    "075" to "Graveler",
    "076" to "Golem",
    "077" to "Ponyta",
    "078" to "Rapidash",
    "079" to "Slowpoke",
    "080" to "Slowbro",
    "081" to "Magnemite",
    "082" to "Magneton",
    "083" to "Farfetch'd",
    "084" to "Doduo",
    "085" to "Dodrio",
    "086" to "Seel",
    "087" to "Dewgong",
    "088" to "Grimer",
    "089" to "Muk",
    "090" to "Shellder",
    "091" to "Cloyster",
    "092" to "Gastly",
    "093" to "Haunter",
    "094" to "Gengar",
    "095" to "Onix",
    "096" to "Drowzee",
    "097" to "Hypno",
    "098" to "Krabby",
    "099" to "Kingler",
    "100" to "Voltorb",
    "101" to "Electrode",
    "102" to "Exeggcute",
    "103" to "Exeggutor",
    "104" to "Cubone",
    "105" to "Marowak",
    "106" to "Hitmonlee",
    "107" to "Hitmonchan",
    "108" to "Lickitung",
    "109" to "Koffing",
    "110" to "Weezing",
    "111" to "Rhyhorn",
    "112" to "Rhydon",
    "113" to "Chansey",
    "114" to "Tangela",
    "115" to "Kangaskhan",
    "116" to "Horsea",
    "117" to "Seadra",
    "118" to "Goldeen",
    "119" to "Seaking",
    "120" to "Staryu",
    "121" to "Starmie",
    "122" to "Mr. Mime",
    "123" to "Scyther",
    "124" to "Jynx",
    "125" to "Electabuzz",
    "126" to "Magmar",
    "127" to "Pinsir",
    "128" to "Tauros",
    "129" to "Magikarp",
    "130" to "Gyarados",
    "131" to "Lapras",
    "132" to "Ditto",
    "133" to "Eevee",
    "134" to "Vaporeon",
    "135" to "Jolteon",
    "136" to "Flareon",
    "137" to "Porygon",
    "138" to "Omanyte",
    "139" to "Omastar",
    "140" to "Kabuto",
    "141" to "Kabutops",
    "142" to "Aerodactyl",
    "143" to "Snorlax",
    "144" to "Articuno",
    "145" to "Zapdos",
    "146" to "Moltres",
    "147" to "Dratini",
    "148" to "Dragonair",
    "149" to "Dragonite",
    "150" to "Mewtwo",
    "151" to "Mew"
)

    fun getKeyByPokemonName(map: HashMap<String, String>, value: String): String? {
        for (entry in map.entries) {
            if (entry.value == value) {
                return entry.key
            }
        }
        return null
    }

}


