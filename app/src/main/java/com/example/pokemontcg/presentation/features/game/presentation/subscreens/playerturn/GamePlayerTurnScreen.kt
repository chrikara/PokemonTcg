package com.example.pokemontcg.presentation.features.game.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.presentation.components.subscreens.gameplayerturn.GamePlayerMain
import com.example.pokemontcg.presentation.features.game.presentation.components.GameActionWindow
import com.example.pokemontcg.presentation.features.game.presentation.components.GameCardInfoBox
import com.example.pokemontcg.presentation.features.game.presentation.components.GameHandBox
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.check.GameCheckScreen
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.check.GameCheckViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.GamePlayerTurnViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.PlayerTurnEvent
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases.ActionInHandUseCase
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases.CardTextInHandUseCase
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases.PlayerTurnUseCases
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.UiEvent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GamePlayerTurnScreen(
    viewModelGame: GameViewModel,
    viewModelPlayerTurn : GamePlayerTurnViewModel,
    viewModelCheck : GameCheckViewModel,
    snackbarHostState : SnackbarHostState
){
    val stateGame = viewModelGame.state
    val statePTurn = viewModelPlayerTurn.state
    val stateCheck = viewModelCheck.state
    val spacing = LocalSpacing.current

    println("Stttate ${stateGame.currentState}")

    val playerTurnUseCases = PlayerTurnUseCases(
        cardTextInHand = CardTextInHandUseCase(),
        actionInHand = ActionInHandUseCase()
    )


    LaunchedEffect(key1 = true){
        viewModelPlayerTurn.uiEvent.collect{ event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )
                }
                else -> {}
            }
        }
    }



    BackHandler(enabled = true) {
        when (stateGame.currentState) {
            GameState.GameSealedClass.PLAYER_TURN.CARD_INFO -> { viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.HAND)) }
            GameState.GameSealedClass.PLAYER_TURN.HAND -> {viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))}
            GameState.GameSealedClass.PLAYER_TURN.BENCH -> {viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))}
            GameState.GameSealedClass.PLAYER_TURN.BENCH.HAND -> {viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.BENCH))}
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
        var textButton1 = playerTurnUseCases.cardTextInHand(stateGame.player.currentHand[statePTurn.selectedCardIndex])

        GameHandBox(
            viewModel = viewModelGame,
            textButton2 = textButton1,
            textButton1 = "Info",
            textButton3 = "Back",
            isButton3Visible = true,
            onClick2 = {
                       viewModelPlayerTurn.onEvent(PlayerTurnEvent.OnClickSelectedCardBasicPokemon(
                           gameCard = it,
                           viewModelGame = viewModelGame
                       ))
            },
            onClick1 = {
                viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.CARD_INFO))
                viewModelPlayerTurn.onEvent(PlayerTurnEvent.OnCardInfoImage(it.image))
            },
            onClick3 = { viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))},
            selectedIndex = statePTurn.selectedCardIndex,
            onItemClick = {
                viewModelPlayerTurn.onEvent(PlayerTurnEvent.OnSelectedCardIndex(stateGame.player.currentHand.indexOf(it)))
                textButton1 = playerTurnUseCases.cardTextInHand(stateGame.player.currentHand[statePTurn.selectedCardIndex])
            },
        )
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.CARD_INFO){
        GameCardInfoBox(
            imageUrl = statePTurn.cardInfoImage,
            radius = 1500f
            )
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.BENCH){
        GameCheckScreen(
            viewModelGame = viewModelGame,
            viewModelCheck = viewModelCheck
        )
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.BENCH){
        GameCheckScreen(
            viewModelGame = viewModelGame,
            viewModelCheck = viewModelCheck
        )
    }

    if(stateGame.currentState == GameState.GameSealedClass.PLAYER_TURN.BENCH.HAND){
        println("kati " + stateCheck.imageUrlCardClicked)
        GameCardInfoBox(
            imageUrl = stateCheck.imageUrlCardClicked,
            radius = 1500f
        )
    }











}

