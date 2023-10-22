package com.example.pokemontcg.presentation.features.game.presentation.components.subscreens.gameplayerturn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Attack
import com.example.pokemontcg.domain.model.Symbol
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.GamePlayerTurnViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.PlayerTurnEvent
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.GameMenuPressedColor
import com.example.pokemontcg.ui.theme.GreenBrush

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GamePlayerMenu(
    modifier : Modifier = Modifier,
    viewModel: GameViewModel,
    viewModelPTurn : GamePlayerTurnViewModel,
    isAttackVisible : Boolean,
    onClickAttack : () -> Unit

){

    var isCheckVisible by remember{
        mutableStateOf<Boolean>(false)
    }




    Column(
        modifier = modifier
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(BlueAlpha80)
                .border(width = 1.dp, color = Color.Black)
                .padding(10.dp),

        ) {
            val paddingValues = PaddingValues(15.dp)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,){
                ButtonSecondary(modifier = Modifier
                    .weight(1f),
                    text = "Attack",
                    paddingValues = paddingValues,
                    backGroundColors = if(isAttackVisible) GameMenuPressedColor else GreenBrush,
                    isEnabled = true){
                    onClickAttack()
                }

                ButtonSecondary(
                    modifier = Modifier
                        .weight(1f),
                    text = "Hand",
                    paddingValues = paddingValues,
                    isEnabled = true){
                    viewModelPTurn.onEvent(PlayerTurnEvent.OnClickHand(
                        currentHand = viewModel.state.player.currentHand,
                        onShowHand = { viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.HAND)) })
                    )

                }

            }

            Row(   horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,){
                ButtonSecondary(modifier = Modifier
                    .weight(1f),
                    text = "Check",
                    fontSize = 18.sp,
                    paddingValues = paddingValues,
                    isEnabled = true){
                    viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.BENCH))
                }

                ButtonSecondary(modifier = Modifier
                    .alpha(if (isCheckVisible) 0.5f else 1f)
                    .weight(1f),
                    text = "Switch",
                    fontSize = 18.sp,
                    paddingValues = paddingValues,
                    isEnabled = true){}

                ButtonSecondary(modifier = Modifier
                    .weight(1f),
                    fontSize = 18.sp,
                    text = "Pass",
                    paddingValues = paddingValues,
                    isEnabled = true){}
            }

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
