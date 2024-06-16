package com.financify.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.financify.model.User

@Composable
fun UsersScreen(modifier: Modifier, users: List<User>) {
    LazyColumn (
        modifier = modifier
            .fillMaxSize()
    ){
        items(users) {user ->
            Text(text = "${user.givenName} ${user.familyName}")
        }
    }
}