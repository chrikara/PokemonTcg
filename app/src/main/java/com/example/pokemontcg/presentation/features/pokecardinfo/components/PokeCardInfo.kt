package com.example.pokemontcg.presentation.features.pokecardinfo.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pokemontcg.R
import com.example.pokemontcg.data.remote.api.dto.cardinfodto.Attack
import com.example.pokemontcg.domain.model.Symbol
import com.example.pokemontcg.domain.model.cardinfo.PokeInfoCard
import com.example.pokemontcg.util.myClickable
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PokeCardInfo(
    pokeInfoCard : PokeInfoCard,
    navController: NavController,
) {



    var sizeState by remember {
        mutableStateOf(1000.dp)
    }


    val size by animateDpAsState(
    targetValue = sizeState,
        tween(durationMillis = 500)
    )


    LaunchedEffect(key1 = true){

        sizeState = 200.dp
    }


    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFD2DBE4))
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        // This for layout https://dribbble.com/shots/4953871-Pok-dex-Entry
        // This for pics https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/001.png

        item {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center

        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .align(CenterStart)
                    .clip(RoundedCornerShape(20.dp))
                    .myClickable(
                        color = Color.Black,
                        onClick = { navController.popBackStack() }
                    )
                    .padding(10.dp)
                    .size(30.dp)
            )
            Text(
                text = pokeInfoCard.name,
                fontSize = 19.sp ,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Center),
            )

            Image(
                painter = painterResource(Symbol.fromString(pokeInfoCard.types?: "Colorless").drawable),
                contentDescription = "",
                modifier = Modifier.size(30.dp).align(CenterEnd)
            )
        }


            val colorHeart = MaterialTheme.colorScheme.background
            FlowRow(
                modifier = Modifier
                    .border(
                        color = colorHeart,
                        width = 0.2.dp,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .clip(RoundedCornerShape(30.dp))

                    .background(Color(0xF9A1D2D8))
                    .padding(15.dp)
                    ,
                mainAxisAlignment = FlowMainAxisAlignment.Center,
                crossAxisAlignment = FlowCrossAxisAlignment.Center
            ){
                Text("HP:${pokeInfoCard.hp}")
                Spacer(modifier = Modifier.width(10.dp))

                repeat((pokeInfoCard.hp.toInt()/10)){
                    Image(
                        painter = painterResource(id = R.drawable.heart_outlined_50),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(colorHeart),
                        modifier = Modifier.size(20.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .background(Color(0xF9A1D2D8))

                    .padding(horizontal = 20.dp, vertical = 30.dp)
                ,
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = rememberImagePainter(
                        data =
                        when(pokeInfoCard.nationalDex.toString().length){
                            1 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00${pokeInfoCard.nationalDex}.png"
                            2 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0${pokeInfoCard.nationalDex}.png"
                            else ->"https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/${pokeInfoCard.nationalDex}.png"
                        }
                    ),
                    contentDescription ="",
                    modifier = Modifier.size(size)
                )
            }

            Box(modifier = Modifier.fillMaxWidth()){

                val nationalDexString = stringWithThreeDigits(pokeInfoCard.nationalDex?: 0)
                Text(
                    text = nationalDexString[0].toString(),
                    fontSize = 150.sp,
                    color = Color(0xFFBDC6CF),
                    modifier = Modifier
                        .align(TopStart)
                        .alpha(0.7f)
                        .padding(start = 20.dp)
                    ,

                )
                Text(
                    text = nationalDexString[1].toString(),
                    fontSize = 150.sp,
                    color = Color(0xFFBDC6CF),
                    modifier = Modifier
                        .align(TopCenter)
                        .alpha(0.7f)
                    ,
                )
                Text(
                    text = nationalDexString[2].toString(),
                    fontSize = 150.sp,
                    color = Color(0xFFBDC6CF),
                    modifier = Modifier
                        .align(TopEnd)
                        .alpha(0.7f)
                        .padding(end = 20.dp)
                    ,
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = pokeInfoCard.description)
                    Spacer(modifier = Modifier.height(70.dp))

                    AttacksBox(
                        pokeInfoCard = pokeInfoCard,
                        eeveeSize = 75.dp
                    )

                }
            }
    }
    }


}
@Composable
private fun AttacksBox(pokeInfoCard : PokeInfoCard, eeveeSize : Dp){

        Box(
            modifier = Modifier
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0x99AA180E))
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {

                Column() {

                    pokeInfoCard.attacks?.let { attackList ->
                        for(attack in attackList){
                            Attack(attack)
                        }
                    }

                }


            }
            Image(
                painter = painterResource(id = R.drawable.attack_eevee),
                contentDescription ="",
                modifier = Modifier
                    .size(eeveeSize)
                    .align(TopEnd)
                    .offset(y = -eeveeSize / 2)
            )
            Image(
                painter = painterResource(id = R.drawable.attack_eevee),
                contentDescription ="",
                modifier = Modifier
                    .size(eeveeSize)
                    .align(TopStart)
                    .offset(y = -eeveeSize / 2)
                    .graphicsLayer { rotationY = 180f }
            )
        }
}

@Composable
private fun Attack(attack: Attack){
    Column(modifier = Modifier.fillMaxWidth())
    {
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text =

                if(attack.damage == "") attack.name
                else "${attack.name} (${attack.damage}DMG)",

                fontSize = 20.sp
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ){

                for (cost in attack.cost){
                    Image(
                        painter = painterResource(id = Symbol.fromString(cost).drawable),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                }

            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider(color = MaterialTheme.colorScheme.background)

        Text(
            text = attack.text
        )
    }
}

fun stringWithThreeDigits(number : Int):String{
    return when (number.toString().length){
        1 -> "00$number"
        2 -> "0$number"
        else -> number.toString()
    }
}