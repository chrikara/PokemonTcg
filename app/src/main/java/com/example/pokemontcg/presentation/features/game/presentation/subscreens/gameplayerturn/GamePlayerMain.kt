package com.example.pokemontcg.presentation.features.game.presentation.components.subscreens.gameplayerturn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.domain.model.defaults.DefaultPokedex
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn.GamePlayerTurnViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn.PlayerTurnEvent
import com.example.pokemontcg.ui.theme.Dimensions
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.HEART_EMPTY
import com.example.pokemontcg.util.HEART_FILLED

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GamePlayerMain(
    modifier : Modifier = Modifier,
    viewModelGame: GameViewModel,
    viewModelPTurn: GamePlayerTurnViewModel,
    spacing : Dimensions = LocalSpacing.current,
) {

    val stateGame = viewModelGame.state
    val statePTurn = viewModelPTurn.state

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
                            data = DefaultPokedex.imageUrlFromDex(stateGame.player.currentPokemon!!.nationalDex)
                        ),
                        contentDescription ="",
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1.3f)
                    )
                    Spacer(modifier = androidx.compose.ui.Modifier.width(spacing.spaceMedium))
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                    ) {

                        val totalPlayerHp = stateGame.player.currentPokemon!!.totalHp / 10
                        val remainingPlayerHp =  stateGame.player.currentPokemon!!.remainingHp / 10

                        Text(
                            text = buildAnnotatedString {
                                append("HP\n")
                                repeat(remainingPlayerHp) { append(HEART_FILLED) }
                                repeat(totalPlayerHp - remainingPlayerHp) { append(HEART_EMPTY) }
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = androidx.compose.ui.Modifier.height(spacing.spaceMedium))
                        Text(
                            text = "Energy",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Monospace

                        )
                        Spacer(modifier = androidx.compose.ui.Modifier.height(spacing.spaceExtraSmall))

                        FlowRow(
                        ){
                            viewModelGame.state.player.currentPokemon!!.energyAttached.forEach{ energyCard ->
                                Image(
                                    painter = painterResource(id = energyCard.symbol.drawable),
                                    contentDescription = "",
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }


                GamePlayerMenu(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    viewModel = viewModelGame,
                    onClickAttack = {
                        viewModelPTurn.onEvent(PlayerTurnEvent.OnAttackButtonTriggered)

                    },
                    isAttackVisible = statePTurn.isAttackButtonEnabled
                )
            }


            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)

            ){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(start = spacing.paddingSmall)
                    .weight(1f),
                ) {

                    // total 60
                    // remaining 30
                    val totalOpponentHp = stateGame.opponent.currentPokemon!!.totalHp / 10
                    val remainingOpponentHp =  stateGame.opponent.currentPokemon!!.remainingHp / 10

                    Text(
                        text = buildAnnotatedString {
                            append("HP\n")
                            repeat(remainingOpponentHp) { append(HEART_FILLED) }
                            repeat(totalOpponentHp - remainingOpponentHp) { append(HEART_EMPTY) }
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = androidx.compose.ui.Modifier.height(spacing.spaceMedium))
                    Text(
                        text = "Energy",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = androidx.compose.ui.Modifier.height(spacing.spaceExtraSmall))


                    FlowRow(
                    ){
                        viewModelGame.state.opponent.currentPokemon!!.energyAttached.forEach{ energyCard ->
                            Image(
                                painter = painterResource(id = energyCard.symbol.drawable),
                                contentDescription = "",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = androidx.compose.ui.Modifier.width(spacing.spaceMedium))

                Image(
                    painter = rememberImagePainter(
                        data = DefaultPokedex.imageUrlFromDex(stateGame.opponent.currentPokemon!!.nationalDex)
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