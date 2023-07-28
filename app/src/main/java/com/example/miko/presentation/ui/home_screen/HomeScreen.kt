package com.example.miko.presentation.ui.home_screen

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
import com.example.miko.R
import com.example.miko.presentation.ui.theme.profileIcon

@Composable
fun HomeScreen() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent() {
    Scaffold(topBar = { HomeScreenTopAppBar() }) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            ChatItem(Modifier.fillMaxWidth())
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
fun ChatItem(
    modifier: Modifier = Modifier
) {
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
                        text = "This is the latest message from Miko, this is just a test",
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
                text = "Today",
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
    HomeScreenContent()
}