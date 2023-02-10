package com.example.pokemontcg.presentation.features.game.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.domain.model.Symbol
import com.example.pokemontcg.domain.model.defaults.DefaultPokedex
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.GameMenuPressedColor
import com.example.pokemontcg.ui.theme.GreenBrush

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameMainScreen(
    modifier : Modifier = Modifier,
    viewModel: GameViewModel
){
    val state = viewModel.state
//    val dex = DefaultPokedex.getKeyByPokemonName(DefaultPokedex.pokedexNationaltoNameHash, selectedPokemonCardImage.name)
//    "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$dex.png"

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.EXPLANATION){
        GameActionWindow(title = " PLAYER \n TURN ", message ="It's your turn now!\n\n") {
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))
        }
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.MAIN){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            reverseLayout = true
        ){
            item {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ){

                    Image(
                        painter = rememberImagePainter(
                            data = DefaultPokedex.imageUrlFromDex(state.player.currentPokemon!!.nationalDex)
                        ),
                        contentDescription ="",
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.BottomStart)
                            .offset(y = -100.dp)
                    )
                    GamePlayerMenu(modifier = Modifier.align(Alignment.BottomCenter))
                }


                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp))

                Box(modifier = Modifier.fillMaxWidth()){
                    Image(
                        painter = rememberImagePainter(
                            data = DefaultPokedex.imageUrlFromDex(state.opponent.currentPokemon!!.nationalDex)
                        ),
                        contentDescription ="",
                        modifier = Modifier
                            .size(200.dp)
                            .align(Alignment.TopEnd)
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GamePlayerMenu(
    modifier : Modifier = Modifier,

){

    var isAttackVisible by remember{
        mutableStateOf<Boolean>(false)
    }
    var isCheckVisible by remember{
        mutableStateOf<Boolean>(false)
    }


    Column(
        modifier = modifier
    ) {

        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .background(BlueAlpha80)
                .border(width = 1.dp, color = Color.Black)
                .padding(10.dp),        horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            maxItemsInEachRow = 2,
        ) {
            val paddingValues = PaddingValues(15.dp)
            ButtonSecondary(modifier = Modifier
                .weight(1f),
                text = "Attack",
                paddingValues = paddingValues,
                backGroundColors = if(isAttackVisible) GameMenuPressedColor else GreenBrush,
                isEnabled = true){
                isAttackVisible = !isAttackVisible
            }

            ButtonSecondary(
                modifier = Modifier
                    .weight(1f),
                text = "Hand",
                paddingValues = paddingValues,
                isEnabled = true){}

            ButtonSecondary(modifier = Modifier
                .alpha(if (isCheckVisible) 0.5f else 1f)
                .weight(1f),
                text = "Check",
                paddingValues = paddingValues,
                isEnabled = true){}

            ButtonSecondary(modifier = Modifier
                .weight(1f),
                text = "Pass",
                paddingValues = paddingValues,
                isEnabled = true){}
        }

        AnimatedVisibility(visible = isAttackVisible) {
            GameAttackBox()
        }
    }





}
@Composable
private fun GameAttackBox(){


    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0x99AA180E))
        .padding(horizontal = 10.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Column(modifier = Modifier.fillMaxWidth())
        {
            GamePokemonAttack(energyCount = 1, textDmg = "20", textAttack = "Water gun")
            GamePokemonAttack(energyCount = 3, textDmg = "303", textAttack = "Call for family")
        }
    }
}
@Composable
private fun GamePokemonAttack(
    energyCount : Int,
    textDmg : String,
    textAttack : String
){
    Row(
        modifier = Modifier.fillMaxWidth().clickable {  }.padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.Start,
        ) {
            repeat(energyCount){
                Image(
                    painter = painterResource(id = Symbol.fromString("Fighting").drawable),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Text(
            modifier = Modifier.weight(3f),
            text = textAttack,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.weight(1f),

            text = textDmg,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.End
        )
    }

}
