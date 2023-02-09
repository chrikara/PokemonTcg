package com.example.pokemontcg.presentation.features.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.model.Symbol
import com.example.pokemontcg.domain.model.cardinfo.SuperType
import com.example.pokemontcg.domain.model.defaults.defaultOpponents
import com.example.pokemontcg.domain.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.domain.use_cases.GetCardsUseCase
import com.example.pokemontcg.presentation.features.game.domain.model.EnergyCard
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.Player
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonType
import com.example.pokemontcg.presentation.features.game.domain.use_cases.GameUseCases
import com.example.pokemontcg.util.INITIAL_CARDS_TO_DRAW
import com.example.pokemontcg.util.Resource
import com.example.pokemontcg.util.TOTAL_DECK_CARDS_GLOBAL
import com.example.pokemontcg.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases,
    private val gameUseCases: GameUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private var getCardsFromAPIJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(GameState())
        private set

    init {

        val deckNumber = DeckNumber.fromString(savedStateHandle.get<String>("deckNumber")?: "1st")
        val opponent = savedStateHandle.get<String>("opponent") ?: "Yugo"
        startGame(deckNumber = deckNumber, opponent)

    }

    fun onEvent(event: GameEvent){
        when(event){
            is GameEvent.OnShuffleDeck -> {
                if(event.player == state.opponent)
                {
                    state = state.copy(
                        opponent = state.opponent.copy(cards = gameUseCases.shuffle(state.opponent.cards)),
                    )
                    return
                }

                state = state.copy(
                    player = state.player.copy(cards = gameUseCases.shuffle(state.player.cards)),
                )


            }
            is GameEvent.OnGive7CardsToEachPlayer -> {

                val cards7Player = gameUseCases.drawCards(INITIAL_CARDS_TO_DRAW, state.player.cards)
                val cards7Opponent = gameUseCases.drawCards(INITIAL_CARDS_TO_DRAW, state.opponent.cards)

                val playerHandPokemonTypes = cards7Player.filterIsInstance<PokemonCard>().map { it.pokemonType }
                val opponentHandPokemonTypes = cards7Opponent.filterIsInstance<PokemonCard>().map { it.pokemonType }

                println(playerHandPokemonTypes)

                if(PokemonType.Basic !in playerHandPokemonTypes || PokemonType.Basic !in opponentHandPokemonTypes){
                    state.player.cards.addAll(cards7Player)
                    state.opponent.cards.addAll(cards7Opponent)

                    println(state.player.cards.size)

                    onEvent(GameEvent.OnShuffleDeck(state.player))
                    onEvent(GameEvent.OnShuffleDeck(state.opponent))
                    return
                }

                state = state.copy(
                    player = state.player.copy(currentHand = cards7Player),
                    opponent = state.opponent.copy(currentHand = cards7Opponent),
                    currentState = GameState.GameSealedClass.CHOOSE_ACTIVE.EXPLANATION
                )


            }

            is GameEvent.OnChooseActivePokemon -> {
                if(event.card is PokemonCard){
                    if(event.card.pokemonType == PokemonType.Basic){
                        state.player.currentPokemon = event.card
                        state.player.currentHand.remove(event.card)
                        state.opponent.currentPokemon = state.opponent.currentHand.filterIsInstance<PokemonCard>().find { it.pokemonType == PokemonType.Basic }



                        state = state.copy(
                            currentState = GameState.GameSealedClass.CHOOSE_BENCH.EXPLANATION
                        )
                        return
                    }
                }
                    viewModelScope.launch {
                    _uiEvent.send(UiEvent.ShowSnackBar(message = "Διάλεξε ένα βασικό Pokemon!"))
                }

            }

            is GameEvent.OnChangeGameState -> {
                state = state.copy(
                    currentState = event.gameState
                )
            }

            is GameEvent.OnChooseBenchPokemon -> {
                viewModelScope.launch {
                    if(event.gameCard is EnergyCard){
                        _uiEvent.send(UiEvent.ShowSnackBar(message = "This is an Energy card!"))
                        return@launch
                    }
                    if(event.gameCard is PokemonCard){
                        if(event.gameCard.pokemonType !is PokemonType.Basic){
                            _uiEvent.send(UiEvent.ShowSnackBar(message = "This is not a basic Pokemon!"))
                            return@launch
                        }


                        state.player.currentHand = state.player.currentHand.also { it.remove(event.gameCard) }
                        state.player.benchPokemon = state.player.benchPokemon.also { it.add(event.gameCard) }


                    }
                }
            }
        }
    }
    fun startGame(deckNumber: DeckNumber, opponent : String){
        getCardsFromAPI(deckNumber, opponent)
    }



    fun getCardsFromAPI(
        deckNumber: DeckNumber,
        opponent: String
    )   {
        val emptyListPlayer = mutableListOf<GameCard>()
        val emptyListOpponent = mutableListOf<GameCard>()

        getCardsFromAPIJob?.cancel()
        getCardsFromAPIJob = allMyDeckUseCases.getAllPokemonInfoFromAPIUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {

                    allMyDeckUseCases.getPokemonFromDeckUseCase().collectLatest { cardsSavedList ->

                        val cardsSavedListDeck = cardsSavedList.filter { it.deckNumber == deckNumber }
                        val cardsAPI = resource.data ?: emptyList()

                        for(cardSaved in cardsSavedListDeck){
                            for(cardAPI in cardsAPI){
                                if(cardSaved.pokemonId == cardAPI.id){
                                    val superTypeOfChosenCard = SuperType.fromString(cardAPI.supertype)
                                    if(superTypeOfChosenCard == SuperType.Pokemon){
                                        val gameCard = PokemonCard(
                                            baseId = cardAPI.id,
                                            image = cardAPI.images.large,
                                            name = cardAPI.name,
                                            attack = cardAPI.attacks,
                                            nationalDex = cardAPI.nationalPokedexNumbers!![0],
                                            pokemonType = getPokemonType(cardAPI.subtypes[0])
                                        )
                                        gameCard.hp = cardAPI.hp.toInt()
                                        emptyListPlayer.add(gameCard)
                                        break
                                    }
                                    if(superTypeOfChosenCard == SuperType.Energy){
                                        val gameCard = EnergyCard(
                                            baseId = cardAPI.id,
                                            image = cardAPI.images.large,
                                            name = cardAPI.name,
                                            symbol = Symbol.fromString(cardAPI.name)
                                        )
                                        emptyListPlayer.add(gameCard)
                                        break
                                    }

                                }
                            }
                        }
                        for(cardOpponent in defaultOpponents.find { it.name == opponent }!!.defaultDeck){
                            for(cardAPI in cardsAPI){
                                if(cardOpponent.id == cardAPI.id){
                                    val superTypeOfChosenCard = SuperType.fromString(cardAPI.supertype)
                                    if(superTypeOfChosenCard == SuperType.Pokemon){
                                        val gameCard = PokemonCard(
                                            baseId = cardAPI.id,
                                            image = cardAPI.images.large,
                                            name = cardAPI.name,
                                            nationalDex = cardAPI.nationalPokedexNumbers!![0],
                                            attack = cardAPI.attacks,
                                            pokemonType = getPokemonType(cardAPI.subtypes[0]
                                            )
                                        )
                                        gameCard.hp = cardAPI.hp.toInt()
                                        emptyListOpponent.add(gameCard)
                                        break
                                    }
                                    if(superTypeOfChosenCard == SuperType.Energy){
                                        val gameCard = EnergyCard(
                                            baseId = cardAPI.id,
                                            image = cardAPI.images.large,
                                            name = cardAPI.name,
                                            symbol = Symbol.fromString(cardAPI.name)
                                        )
                                        emptyListOpponent.add(gameCard)
                                        break
                                    }

                                }
                            }
                        }
                        state = state.copy(
                            player = state.player.copy(
                                cards = emptyListPlayer,
                            ),
                            opponent = state.opponent.copy(
                                name = opponent,
                                cards = emptyListOpponent
                            ) ,
                            currentState = GameState.GameSealedClass.START,
                        )
                    }
                }

                is Resource.Error -> {
                    state = state.copy(
                        currentState = GameState.GameSealedClass.ERROR,
                        errorMessageAPI = resource.message ?: "Some error occured"
                    )
                }
                is Resource.Loading -> {
                    state = state.copy(
                        currentState = GameState.GameSealedClass.LOADING
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPokemonType(subtype : String) : PokemonType{
        return when(subtype){
            "Basic" -> PokemonType.Basic
            "Stage 1" -> PokemonType.Stage1
            "Stage 2" -> PokemonType.Stage2
            else -> PokemonType.Stage2
        }
    }
}