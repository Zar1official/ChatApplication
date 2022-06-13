package ru.zar1official.chatapplication.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import models.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private const val managerDatastoreName = "session_manager"
        private const val userIdKeyName = "user_id"
        private const val usernameKeyName = "username"
        private const val passwordKeyName = "password"
    }

    private val userIdKey = intPreferencesKey(userIdKeyName)
    private val usernameKey = stringPreferencesKey(usernameKeyName)
    private val passwordKey = stringPreferencesKey(passwordKeyName)


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        managerDatastoreName
    )

    @Volatile
    private var currentSession: UserEntity = UserEntity(username = "", password = "")

    suspend fun updateSession(user: UserEntity) {
        context.dataStore.edit { prefs ->
            prefs[userIdKey] = user.userId
            prefs[usernameKey] = user.username
            prefs[passwordKey] = user.password
        }
        currentSession = user
    }

    suspend fun getSession(): UserEntity {
        if (currentSession.userId == 0) {
            val prefs = context.dataStore.data.first()

            val userId = prefs[userIdKey] ?: 0
            val username = prefs[usernameKey].orEmpty()
            val password = prefs[passwordKey].orEmpty()
            val user = UserEntity(userId, username, password)
            currentSession = user
        }
        return currentSession
    }

    suspend fun clearSession() {
        currentSession = UserEntity(username = "", password = "")
        context.dataStore.edit {
            it.clear()
        }
    }
}