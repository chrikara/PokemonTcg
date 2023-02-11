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
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn.use_cases.CardTextInHandUseCase
import com.example.pokemontcg.ui.theme.LocalSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GamePlayerTurnScreen(
    viewModel: GameViewModel,
){
    val state = viewModel.state
    val spacing = LocalSpacing.current

    var imageUrl by remember {
        mutableStateOf("")
    }

    val cardTextInHandUseCase = CardTextInHandUseCase()



    BackHandler(enabled = true) {
        when (state.currentState) {
            GameState.GameSealedClass.PLAYER_TURN.CARD_INFO -> { viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.HAND)) }
            GameState.GameSealedClass.PLAYER_TURN.HAND -> {viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))}
            else -> Unit
        }
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.EXPLANATION){
        GameActionWindow(title = " PLAYER \n TURN ", message ="It's your turn now!\n\n") {
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))
        }
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.MAIN){
        GamePlayerMain(viewModel = viewModel)
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.HAND){
        var textButton1 by remember {
            mutableStateOf("")
        }

        GameHandBox(
            viewModel = viewModel,
            textButton1 = textButton1,
            textButton2 = "Info",
            textButton3 = "Back",
            isButton3Visible = true,
            onClick1 = {},
            onDoSomethingWithSelectedIndex = {
                                             textButton1 = cardTextInHandUseCase(it)
            },
            onClick2 = {
                viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.CARD_INFO))
                imageUrl = it.image
            },
            onClick3 = { viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))}
        )
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.CARD_INFO){
        GameCardInfoBox(
            imageUrl = imageUrl,
            radius = 1500f
            )
    }












}

