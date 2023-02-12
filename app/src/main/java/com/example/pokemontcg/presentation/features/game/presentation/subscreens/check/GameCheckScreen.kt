@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.example.pokemontcg.presentation.features.game.presentation.subscreens.check

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.domain.model.Player
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonType
import com.example.pokemontcg.ui.theme.BlueAlpha
import com.example.pokemontcg.ui.theme.GreenBrush
import com.example.pokemontcg.ui.theme.LocalSpacing

@Composable
fun GameCheckScreen(
    viewModelGame : GameViewModel,
    viewModelCheck : GameCheckViewModel
) {

    val listState = rememberLazyListState()
    val spacing = LocalSpacing.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(GreenBrush))
            .padding(5.dp)
        ,
        reverseLayout = true,
        state = listState,
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        item {

            GameCheckPlayer(
                player = viewModelGame.state.player,
                onClick = {
                    viewModelCheck.onCardInfoImageUrl(it)
                    viewModelGame.onEvent(GameEvent.OnChangeGameState(
                        gameState = GameState.GameSealedClass.PLAYER_TURN.BENCH.HAND
                    ))
                }
            )


            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
                color = Color.White
            )

            GameCheckOpponent(
                viewModelGame.state.opponent,
                onClick = {
                    viewModelCheck.onCardInfoImageUrl(it)
                    viewModelGame.onEvent(GameEvent.OnChangeGameState(
                        gameState = GameState.GameSealedClass.PLAYER_TURN.BENCH.HAND
                    ))
                }
            )

        }

    }

}

@Composable
fun GameCheckOpponent(
    player: Player,
    onClick: (String) -> Unit
) {
    val spacing = LocalSpacing.current

    Column() {

        GameCheckPlayerBench(
            player = player,
            onClick = {onClick(it)}
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        GameCheckPrize(
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.CenterStart
        )


        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            GameCheckCard(
                pokemonCard = player.currentPokemon!!,
                onClick = {onClick(it)}
            )
        }

        Spacer(modifier = Modifier.height(spacing.spaceMegaLarge))

    }
}

@Composable
fun GameCheckPlayer(
    player: Player,
    onClick: (String) -> Unit
){
    val spacing = LocalSpacing.current
    Column() {

        Spacer(modifier = Modifier.height(spacing.spaceMegaLarge))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            GameCheckCard(
                pokemonCard = player.currentPokemon!!,
                onClick = {
                    onClick(it)
                }
            )
        }
        GameCheckPrize(
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.CenterEnd
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        GameCheckPlayerBench(
            player = player,
            onClick = {
                onClick(it)
            }
        )


    }
}

@Composable
private fun GameCheckPlayerBench(
    player: Player,
    onClick: (String) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        player.benchPokemon.forEach(){
            GameCheckCard(
                pokemonCard = it,
                modifier = Modifier.weight(1f),
                onClick = {imageString ->

                    onClick(it.image)}
                )
        }

        /*
        If there are less than 3 Pokemon at bench, add empty cards
         */

        for(i in 0 until 3-player.benchPokemon.size){
            GameCheckCard(
                isEmpty = true,
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }

    }

}
@Composable
private fun GameCheckPrize(
    modifier : Modifier = Modifier,
    alignment : Alignment
){
    Box(
        modifier = modifier
    ){
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .align(alignment = alignment),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                GamePrizeCard()
            }

            Row( verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                GamePrizeCard()
                GamePrizeCard()
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameCheckCard(
    modifier : Modifier = Modifier,
    isEmpty : Boolean = false,
    onClick : (String) -> Unit,
    pokemonCard: PokemonCard = PokemonCard("", "", pokemonType = PokemonType.Basic)
) {
    Column(
        modifier = modifier
            .alpha(if (isEmpty) 0.5f else 1f)
            .clickable(
                enabled = !isEmpty,
                onClick = {
                    onClick(pokemonCard.image)}
            )
            .shadow(
                elevation = 2.dp,
                shape = CutCornerShape(
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                ),
            )
            .padding(2.dp)
            .clip(
                CutCornerShape(
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )

            .background(
                brush = Brush.radialGradient(
                    listOf(
                        BlueAlpha,
                        Color.Transparent
                    ),
                    radius = 650f
                )
            )
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FlowRow(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(!isEmpty){
                pokemonCard.energyAttached.forEach{
                    Image(
                        modifier = Modifier
                            .size(22.dp)
                        ,
                        painter = painterResource(id = it.symbol.drawable) ,
                        contentDescription = "",
                    )
                }
            }
            if(isEmpty)
                Spacer(modifier = Modifier.size(22.dp))
        }

        Image(
            painter = rememberImagePainter(
                data = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4f7705ec-8c49-4eed-a56e-c21f3985254c/dah43cy-a8e121cb-934a-40f6-97c7-fa2d77130dd5.png/v1/fill/w_1024,h_1420,strp/pokemon_card_backside_in_high_resolution_by_atomicmonkeytcg_dah43cy-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTQyMCIsInBhdGgiOiJcL2ZcLzRmNzcwNWVjLThjNDktNGVlZC1hNTZlLWMyMWYzOTg1MjU0Y1wvZGFoNDNjeS1hOGUxMjFjYi05MzRhLTQwZjYtOTdjNy1mYTJkNzcxMzBkZDUucG5nIiwid2lkdGgiOiI8PTEwMjQifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.9GzaYS7sd8RPY5FlHca09J9ZQZ9D9zI69Ru-BsbkLDA"
                ,
                builder = {
                    crossfade(true)
                },
            ),
            contentDescription = "",
            modifier = Modifier.size(150.dp),
        )

        Text(
            text = if(isEmpty) "-" else pokemonCard.name,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 17.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )


        Spacer(modifier = Modifier.height(5.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GamePrizeCard(
    modifier : Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable { }
            .shadow(
                elevation = 2.dp,
                shape = CutCornerShape(
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                ),
            )
            .padding(2.dp)
            .clip(
                CutCornerShape(
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )

            .background(
                brush = Brush.radialGradient(
                    listOf(
                        BlueAlpha,
                        Color.Transparent
                    ),
                    radius = 650f
                )
            )
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FlowRow(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(22.dp))
        }

        Image(
            painter = rememberImagePainter(
                data = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4f7705ec-8c49-4eed-a56e-c21f3985254c/dah43cy-a8e121cb-934a-40f6-97c7-fa2d77130dd5.png/v1/fill/w_1024,h_1420,strp/pokemon_card_backside_in_high_resolution_by_atomicmonkeytcg_dah43cy-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTQyMCIsInBhdGgiOiJcL2ZcLzRmNzcwNWVjLThjNDktNGVlZC1hNTZlLWMyMWYzOTg1MjU0Y1wvZGFoNDNjeS1hOGUxMjFjYi05MzRhLTQwZjYtOTdjNy1mYTJkNzcxMzBkZDUucG5nIiwid2lkdGgiOiI8PTEwMjQifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.9GzaYS7sd8RPY5FlHca09J9ZQZ9D9zI69Ru-BsbkLDA"
                ,
                builder = {
                    crossfade(true)
                },
            ),
            contentDescription = "",
            modifier = Modifier.size(50.dp),
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}

