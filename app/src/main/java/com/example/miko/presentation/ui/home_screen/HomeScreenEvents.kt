package com.example.miko.presentation.ui.home_screen

sealed class HomeScreenEvents {
    sealed class AddNewChat(name: String): HomeScreenEvents()
}