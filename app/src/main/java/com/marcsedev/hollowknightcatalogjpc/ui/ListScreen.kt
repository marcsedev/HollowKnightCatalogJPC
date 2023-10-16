package com.marcsedev.hollowknightcatalogjpc.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcsedev.hollowknightcatalogjpc.R
import com.marcsedev.hollowknightcatalogjpc.model.CharacterHollowKnight
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListScreen() {
    val context = LocalContext.current
    val rvState = rememberLazyListState()
    val coroutinesScope = rememberCoroutineScope()
    val character: Map<String, List<CharacterHollowKnight>> = getCharacters().groupBy { it.game }
    Column {
        LazyColumn(
            state = rvState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            character.forEach { (game, myCharacter) ->
                stickyHeader {
                    Text(
                        text = "Game: $game",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.DarkGray),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                items(myCharacter) { character ->
                    ItemCharacter(
                        characterHollowKnight = character,
                        onItemSelected = { selectedCharacter ->
                            showToast(context, selectedCharacter.name)
                        }
                    )
                }
            }
        }

        val showButton by remember {
            derivedStateOf {
                rvState.firstVisibleItemScrollOffset > 0
            }
        }
        if (showButton) {
            Button(
                onClick = {
                    coroutinesScope.launch {
                        rvState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "")
            }
        }
    }
}

@Composable
fun ItemCharacter(
    characterHollowKnight: CharacterHollowKnight,
    onItemSelected: (CharacterHollowKnight) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected(characterHollowKnight) }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = characterHollowKnight.image),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = characterHollowKnight.name, modifier = Modifier.align(CenterHorizontally))
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = characterHollowKnight.gender,
                modifier = Modifier.align(CenterHorizontally),
                fontSize = 12.sp
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getCharacters(): List<CharacterHollowKnight> {
    return listOf(
        CharacterHollowKnight("The Knight", "Male", R.drawable.the_knight, "Hollow Knight"),
        CharacterHollowKnight("Cloth", "Female", R.drawable.cloth, "Hollow Knight"),
        CharacterHollowKnight("Cornifer", "Male", R.drawable.cornifer, "Hollow Knight"),
        CharacterHollowKnight("Hornet", "Female", R.drawable.hornet, "Hollow Knight"),
        CharacterHollowKnight("Grimm", "Male", R.drawable.grimm_boss, "The Grimm Troupe"),
        CharacterHollowKnight("Grimm", "Male", R.drawable.grimm_boss, "The Grimm Troupe"),
        CharacterHollowKnight("Grimm", "Male", R.drawable.grimm_boss, "The Grimm Troupe"),
        CharacterHollowKnight("Grimm", "Male", R.drawable.grimm_boss, "The Grimm Troupe"),
        CharacterHollowKnight(
            "Mister Mushroom",
            "Male",
            R.drawable.mister_mushroom,
            "Hollow Knight"
        ),
        CharacterHollowKnight("Quirrel", "Male", R.drawable.quirrel, "Hollow Knight"),
        CharacterHollowKnight("Tiso", "Male", R.drawable.tiso, "Hollow Knight"),
        CharacterHollowKnight("Zote", "Male", R.drawable.zote, "Hollow Knight")
    )
}