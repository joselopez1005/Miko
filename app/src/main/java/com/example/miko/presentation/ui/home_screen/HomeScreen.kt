package com.example.miko.presentation.ui.home_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.miko.R
import com.example.miko.presentation.ui.theme.profileIcon
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    HomeScreenContent(viewModel.state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeScreenStates
) {
    if (!state.isLoading) {
        Scaffold(topBar = { HomeScreenTopAppBar() }) { contentPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                ChatItems(state = state, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun HomeScreenTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Conversations",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = TextUnit(value = 0.5f, TextUnitType.Sp)
            )
        },
        actions = {
            AddNewChatSectionTopBar()
        }
    )
}

@Composable
fun AddNewChatSectionTopBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Add New",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun ChatItems(
    state: HomeScreenStates,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(state.amountOfChats) { current ->
            val currentChat = state.latestMessagesChats[current]
            Log.d("HomeScreen", "Currentchat: $currentChat")
            currentChat?.let {
                ChatItem(
                    name = "Miko",
                    latestMessage = it.latestMessage,
                    latestMessageTime = it.latestTime,
                    profilePicture = 2,
                    Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ChatItem(
    name: String,
    latestMessage: String,
    latestMessageTime: LocalDateTime,
    profilePicture: Int,
    modifier: Modifier = Modifier
) {
    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    val currentMessageTime = latestMessageTime.format(formatter)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(10.dp)
    ) {
        Row (Modifier.weight(.7f))  {
            Image(
                painter = painterResource(id = R.drawable.ic_morgana),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(55.dp)
                    .clip(profileIcon)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.onSecondaryContainer)


            )
            Box(
                modifier = Modifier.height(55.dp)
            ) {
                Column (Modifier.align(Alignment.CenterStart)) {
                    Text(
                        text = "Miko",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                    )
                    Text(
                        text = latestMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        maxLines = 1 ,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                    )
                }
            }
        }
        Box(modifier = Modifier.weight(.2f)) {
            Text(
                text = currentMessageTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(state = HomeScreenStates(1))
}