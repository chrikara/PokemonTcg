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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.R
import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Attack
import com.example.pokemontcg.domain.model.Symbol
import com.example.pokemontcg.domain.model.defaults.DefaultPokedex
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.GameMenuPressedColor
import com.example.pokemontcg.ui.theme.GreenBrush
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.HEART_EMPTY
import com.example.pokemontcg.util.HEART_FILLED

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameMainScreen(
    modifier : Modifier = Modifier,
    viewModel: GameViewModel
){
    val state = viewModel.state
    val spacing = LocalSpacing.current
//    val dex = DefaultPokedex.getKeyByPokemonName(DefaultPokedex.pokedexNationaltoNameHash, selectedPokemonCardImage.name)
//    "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$dex.png"

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.EXPLANATION){
        GameActionWindow(title = " PLAYER \n TURN ", message ="It's your turn now!\n\n") {
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))
        }
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.MAIN){
        println("Attacks and state ${state.player.currentPokemon?.attack}")
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .offset(y = -100.dp)

                    ){
                        Image(
                            painter = rememberImagePainter(
                                data = DefaultPokedex.imageUrlFromDex(state.player.currentPokemon!!.nationalDex)
                            ),
                            contentDescription ="",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1.3f)
                        )
                        Spacer(modifier = Modifier.width(spacing.spaceMedium))
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                        ) {

                            Text(
                                text = buildAnnotatedString {
                                    append("HP\n")
                                    repeat(3) { append(HEART_FILLED) }
                                    repeat(3) { append(HEART_EMPTY) }
                                },
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                            Text(
                                text = "Energy",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily.Monospace

                            )
                            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))

                            FlowRow(
                            ){
                                repeat(14){
                                    Image(
                                        painter = painterResource(id = R.drawable.symbol_fighting),
                                        contentDescription = "",
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }
                    }


                    GamePlayerMenu(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        viewModel = viewModel
                    )
                }


                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ){
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(start = spacing.paddingSmall)
                        .weight(1f)
                    ) {

                        Text(
                            text = buildAnnotatedString {
                                append("HP\n")
                                repeat(3) { append(HEART_FILLED) }
                                repeat(3) { append(HEART_EMPTY) }
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        Text(
                            text = "Energy",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))


                        FlowRow(
                        ){
                            repeat(14){
                                Image(
                                    painter = painterResource(id = R.drawable.symbol_fighting),
                                    contentDescription = "",
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(spacing.spaceMedium))

                    Image(
                        painter = rememberImagePainter(
                            data = DefaultPokedex.imageUrlFromDex(state.opponent.currentPokemon!!.nationalDex)
                        ),
                        contentDescription ="",
                        modifier = Modifier
                            .weight(1.3f)
                            .aspectRatio(1f)
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
    viewModel: GameViewModel

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
            GameAttackBox(viewModel = viewModel)
        }
    }





}
@Composable
private fun GameAttackBox(
    viewModel: GameViewModel
){
    val currentPokemonAttacks = viewModel.state.player.currentPokemon!!.attack


    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0x99AA180E))
        .padding(horizontal = 10.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Column(modifier = Modifier.fillMaxWidth())
        {
            currentPokemonAttacks?.forEach{
                GamePokemonAttack(attack = it)
            }
        }
    }
}
@Composable
private fun GamePokemonAttack(
    attack: Attack
){
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(2f),
            horizontalArrangement = Arrangement.Start,
        ) {
            attack.cost.forEach{
                Image(
                    painter = painterResource(id = Symbol.fromString(it).drawable),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Text(
            modifier = Modifier.weight(3f),
            text = attack.name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.weight(1f),

            text = attack.damage,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.End
        )
    }

}
