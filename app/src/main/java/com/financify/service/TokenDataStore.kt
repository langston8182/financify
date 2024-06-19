package com.financify.service

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class TokenDataStore(private val context: Context) {
    companion object {
        val TOKEN_KEY = stringPreferencesKey("access_token")
        val ID_TOKEN_KEY = stringPreferencesKey("id_token")
    }

    val token: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val idToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[ID_TOKEN_KEY]
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveIdToken(idToken: String) {
        context.dataStore.edit { preferences ->
            preferences[ID_TOKEN_KEY] = idToken
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(ID_TOKEN_KEY)
        }
    }
}