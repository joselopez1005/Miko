package com.example.miko.presentation.ui.chat_screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.miko.presentation.ui.chat_screen.ChatViewModel.Companion.SYSTEM
import com.example.miko.presentation.ui.chat_screen.ChatViewModel.Companion.USER
import com.example.miko.presentation.ui.theme.MikoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreenContent(state = viewModel.state.collectAsState().value, onButtonPressed = viewModel::onEvent)
}

@ExperimentalMaterial3Api
@Composable
fun ChatScreenContent(
    state: ChatScreenStates,
    onButtonPressed: (ChatScreenEvents) -> Unit
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopBar(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            ChatMessageSection(state = state, modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextField(
                    value = textState.value,
                    onValueChange = {textState.value = it}
                )

                Button(onClick = {onButtonPressed(ChatScreenEvents.OnSendMessage(textState.value.text))} ) {
                    Text(text = "Done")
                }
            }
        }
    }
}

@Composable
fun ChatMessageSection(
    state: ChatScreenStates,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)){
            items(state.chatLogs.size) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    when(state.chatLogs[it].role) {
                        USER -> Text(text = state.chatLogs[it].content, color = Color.Red, modifier = Modifier.align(Alignment.CenterEnd))
                        SYSTEM -> Text(text = "Start Conversation", modifier = Modifier.align(Alignment.Center))
                        else -> Text(text = state.chatLogs[it].content)

                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
    state: ChatScreenStates,
    modifier: Modifier
) {
    Row(
        modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileInfoSection(state = state)
    }
}

@Composable
fun ProfileInfoSection(
    modifier: Modifier = Modifier,
    state: ChatScreenStates
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = state.chatProfile.iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .padding(16.dp)
        )
        Box(
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = state.chatProfile.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 2.dp).align(Alignment.TopStart)
            )
            if (state.isLoading) {
                Text(
                    text = "Typing...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChatScreenContentPreview() {
    MikoTheme {
        ChatScreenContent(
            ChatScreenStates(completions = null, true, null),
            onButtonPressed = {}
        )
    }
}

@Preview
@Composable
fun ProfileInfoSectionPreview() {
    MikoTheme {
        ProfileInfoSection(modifier = Modifier.fillMaxWidth(), ChatScreenStates(completions = null, true, null))
    }
}