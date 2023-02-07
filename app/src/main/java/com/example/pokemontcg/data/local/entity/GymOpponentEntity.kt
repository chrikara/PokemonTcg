package com.example.pokemontcg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemontcg.domain.model.GymOpponent

@Entity
class GymOpponentEntity(
    val gymName:String,
    val name:String,
    val image:Int,
    val symbolImage : Int,
    val isBoss:Boolean,
    val isBeaten:Boolean,
    @PrimaryKey val id: Int? = null

)

fun GymOpponentEntity.toGymOpponent() : GymOpponent{
    return GymOpponent(
        gymName = this.gymName,
        name = this.name,
        image = this.image,
        symbolImage = this.symbolImage,
        isBoss = this.isBoss,
        isBeaten = this.isBeaten,
        id = id
    )
}

fun GymOpponent.toGymOpponentEntity() : GymOpponentEntity{
    return GymOpponentEntity(
        gymName = this.gymName,
        name = this.name,
        image = this.image,
        symbolImage = this.symbolImage,
        isBoss = this.isBoss,
        isBeaten = this.isBeaten,
        id = id
    )
}




