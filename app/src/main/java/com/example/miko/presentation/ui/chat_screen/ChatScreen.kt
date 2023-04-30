package com.example.miko.presentation.ui.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.miko.R
import com.example.miko.domain.chat.ProfileInfo
import com.example.miko.presentation.ui.theme.MikoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreenContent(state = viewModel.state.collectAsState().value, onButtonPressed = viewModel::sendMessage)
}

@ExperimentalMaterial3Api
@Composable
fun ChatScreenContent(
    state: ChatScreenStates,
    onButtonPressed: (String) -> Unit
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopBar(
            state = state,
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .background(Color.Blue)
        )
        Text(
            text = if (state.isLoading) "Loading..." else state.completions?.messages?.get(0)?.content ?: "No messages to display",
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Center)
        )

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

            Button(onClick = {onButtonPressed(textState.value.text)} ) {
                Text(text = "Done")
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
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileInfoSection(profileInfo = state.chatProfile)
    }
}

@Composable
fun ProfileInfoSection(
    modifier: Modifier = Modifier,
    profileInfo: ProfileInfo
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = profileInfo.iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .padding(16.dp)
        )

        Text(
            text = profileInfo.name,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChatScreenContentPreview() {
    MikoTheme {
        ChatScreenContent(
            ChatScreenStates(completions = null, false, null),
            onButtonPressed = {}
        )
    }
}

@Preview
@Composable
fun ProfileInfoSectionPreview() {
    MikoTheme {
        ProfileInfoSection(modifier = Modifier.fillMaxWidth(), profileInfo = ProfileInfo("Miko", R.drawable.person_girl))
    }
}