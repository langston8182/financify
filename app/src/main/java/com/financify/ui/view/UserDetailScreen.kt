package com.financify.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.financify.model.User

@Composable
fun UserDetailScreen(
    modifier: Modifier,
    user: User,
    updateGivenName: (givenName: String) -> Unit,
    updateFamilyName: (familyName: String) -> Unit,
    updateUser: () -> Unit
) {
    val manager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        OutlinedTextField(
            value = user.givenName,
            onValueChange = { updateGivenName(it) },
            label = { Text(text = "Nom") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { manager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = user.familyName,
            onValueChange = { updateFamilyName(it) },
            label = { Text(text = "Prénom") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = user.email,
            onValueChange = {},
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = false
        )
        Spacer(modifier = Modifier.weight(1.0F))
        Button(
            onClick = { updateUser() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Modifier")
        }
    }
}