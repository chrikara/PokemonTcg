package com.example.pokemontcg.presentation.features.game.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.presentation.components.subscreens.gameplayerturn.GamePlayerMain
import com.example.pokemontcg.presentation.features.game.presentation.components.GameActionWindow
import com.example.pokemontcg.presentation.features.game.presentation.components.GameCardInfoBox
import com.example.pokemontcg.presentation.features.game.presentation.components.GameHandBox
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn.GamePlayerTurnViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn.PlayerTurnEvent
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn.use_cases.CardTextInHandUseCase
import com.example.pokemontcg.ui.theme.LocalSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GamePlayerTurnScreen(
    viewModelGame: GameViewModel,
    viewModelPlayerTurn : GamePlayerTurnViewModel
){
    val stateGame = viewModelGame.state
    val statePTurn = viewModelPlayerTurn.state
    val spacing = LocalSpacing.current



    val cardTextInHandUseCase = CardTextInHandUseCase()



    BackHandler(enabled = true) {
        when (stateGame.currentState) {
            GameState.GameSealedClass.PLAYER_TURN.CARD_INFO -> { viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.HAND)) }
            GameState.GameSealedClass.PLAYER_TURN.HAND -> {viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))}
            else -> Unit
        }
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.EXPLANATION){
        GameActionWindow(title = " PLAYER \n TURN ", message ="It's your turn now!\n\n") {
            viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))
        }
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.MAIN){
        GamePlayerMain(
            viewModelGame = viewModelGame,
            viewModelPTurn = viewModelPlayerTurn
        )
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.HAND){
        var textButton1 by remember {
            mutableStateOf("")
        }

        GameHandBox(
            viewModel = viewModelGame,
            textButton2 = textButton1,
            textButton1 = "Info",
            textButton3 = "Back",
            isButton3Visible = true,
            onClick2 = {},
            onDoSomethingWithSelectedIndex = {
                                             textButton1 = cardTextInHandUseCase(it)
            },
            onClick1 = {
                viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.CARD_INFO))
                viewModelPlayerTurn.onEvent(PlayerTurnEvent.OnCardInfoImage(it.image))
            },
            onClick3 = { viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))}
        )
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.CARD_INFO){
        GameCardInfoBox(
            imageUrl = statePTurn.cardInfoImage,
            radius = 1500f
            )
    }












}

