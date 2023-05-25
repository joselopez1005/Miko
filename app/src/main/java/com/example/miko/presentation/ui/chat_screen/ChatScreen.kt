@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.miko.presentation.ui.chat_screen

import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.miko.R
import com.example.miko.domain.chat.Message
import com.example.miko.presentation.ui.chat_screen.ChatViewModel.Companion.ASSISTANT
import com.example.miko.presentation.ui.chat_screen.ChatViewModel.Companion.SYSTEM
import com.example.miko.presentation.ui.chat_screen.ChatViewModel.Companion.USER
import com.example.miko.presentation.ui.theme.MessageBubbleShapeAssistant
import com.example.miko.presentation.ui.theme.MessageBubbleShapeUser
import com.example.miko.presentation.ui.theme.MikoTheme
import com.example.miko.presentation.ui.theme.Shapes
import com.example.miko.presentation.ui.theme.profileIcon
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    ChatScreenContent(
        state = viewModel.state.collectAsState().value,
        onButtonPressed = viewModel::onEvent
    )
}

@ExperimentalMaterial3Api
@Composable
fun ChatScreenContent(
    state: ChatScreenStates,
    onButtonPressed: (ChatScreenEvents) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TopBar(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.TopCenter)
                .zIndex(1f)
        )

        Column(modifier = Modifier
            .fillMaxSize()) {
            ChatMessageSection(
                state = state, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp, bottom = 80.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.White, MaterialTheme.colorScheme.surfaceVariant),
                            tileMode = TileMode.Clamp
                        )
                    )
                    .padding(start = 10.dp, end = 10.dp)
            )
        }

        BottomChatSection(
            onButtonPressed = onButtonPressed,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
        )
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
        Image(
            painter = painterResource(id = state.chatProfile.iconRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .height(40.dp)
                .width(40.dp)
                .clip(profileIcon)
                .background(MaterialTheme.colorScheme.tertiary)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary)


        )
        Box(
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = state.chatProfile.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .align(Alignment.TopStart)
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

@Composable
fun ChatMessageSection(
    state: ChatScreenStates,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        items(state.chatLogs.size) {
            Box(modifier = Modifier.fillMaxWidth()) {
                when (state.chatLogs[it].role) {
                    USER -> {
                        MessageBubble(
                            message = state.chatLogs[it].content,
                            sender = USER,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }

                    SYSTEM -> {
//                        Text(
//                            text = Date().toString(),
//                            style = MaterialTheme.typography.bodySmall,
//                            modifier = Modifier.align(Alignment.Center)
//                        )
                    }

                    else -> {
                        MessageBubble(
                            message = state.chatLogs[it].content,
                            sender = ASSISTANT,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }

                }
            }
            if (it == state.chatLogs.size -1 ){
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    LaunchedEffect(state.chatLogs.size) {
        listState.animateScrollToItem(state.chatLogs.size - 1)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomChatSection(
    onButtonPressed: (ChatScreenEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column(modifier) {
        Divider(thickness = 1.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp)
        ) {
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                placeholder = {
                    Text(
                        "Type your message...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                leadingIcon = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(60.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.insert_emoji),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )

                        Divider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .width(1.dp)
                                .height(20.dp)
                        )
                    }
                },
                modifier = Modifier
                    .width(320.dp)
                    .clip(Shapes.extraLarge)
                    .zIndex(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_paper_plane_send),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onButtonPressed.invoke(ChatScreenEvents.OnSendMessage(textState.value.text))
                    textState.value = TextFieldValue()
                }
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: String,
    sender: String,
    modifier: Modifier = Modifier
) {
    val isUser = sender == USER
    Box(
        modifier = modifier
            .clip(
                if (isUser) MessageBubbleShapeUser else MessageBubbleShapeAssistant
            )
            .background(if (isUser) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isUser) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary,
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChatScreenContentPreview() {
    MikoTheme {
        ChatScreenContent(
            ChatScreenStates(
                completions = null, true, null,
                chatLogs = mutableListOf(
                    Message("system", "You are a helpful assistant", ""),
                    Message(USER, "Hello my name is Jose", ""),
                    Message(ASSISTANT, "Hello, Jose, my name is Miko", "")
                ),
            ),
            onButtonPressed = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubblePreview() {
    MikoTheme {
        MessageBubble(
            message = "This is a long sentence, I just want to see what happens \n if it goes next line",
            sender = USER
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomChatSectionPreview() {
    MikoTheme {
        BottomChatSection(onButtonPressed = {})
    }
}
