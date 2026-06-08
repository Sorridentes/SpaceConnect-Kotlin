package br.com.thefirst.fiap.spaceconnect.common.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_session")

class SessionManager(
    private val context: Context
) {
    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")

        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    // ========== Autenticação ==========

    suspend fun saveUser(userId: String, userName: String, userEmail: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[USER_NAME] = userName
            preferences[USER_EMAIL] = userEmail
        }
    }

    fun getUser(): Flow<User?> {
        return context.dataStore.data.map { preferences ->
            val userId = preferences[USER_ID] ?: return@map null
            User(
                id = userId,
                name = preferences[USER_NAME] ?: "",
                email = preferences[USER_EMAIL] ?: ""
            )
        }
    }

    suspend fun cleanUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // ========== Onboarding ==========

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    fun isOnboardingCompleted(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }
    }

    suspend fun resetOnboarding() {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = false
        }
    }
}