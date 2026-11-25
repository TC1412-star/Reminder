package com.tc1412.reminder.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("userPreferences")

data class UserPreferences(val uiTheme: UITheme, val sort: Sort, val hidePast: Boolean)

class DataStoring(private val dataStore: DataStore<Preferences>) {
    companion object{
        val UI_THEME = intPreferencesKey("ui_theme")
        val SORT = intPreferencesKey("reminder_sort")
        val HIDE_PAST = booleanPreferencesKey("hide_past")
    }

    val userPreferences: Flow<UserPreferences> = dataStore.data
        .catch { exc -> if(exc is IOException) emit(emptyPreferences()) else throw exc }
        .map { pref -> UserPreferences(
            uiTheme = UITheme.getByKey(pref[UI_THEME] ?: 0),
            sort = Sort.getByKey(pref[SORT] ?: 0),
            hidePast = pref[HIDE_PAST] ?: false
        ) }

    suspend fun setUiTheme(uiTheme: UITheme) = dataStore.edit { it[UI_THEME] = uiTheme.key }
    suspend fun setSort(sort: Sort) = dataStore.edit { it[SORT] = sort.key }
    suspend fun setHidePast(hidePast: Boolean) = dataStore.edit { it[HIDE_PAST] = hidePast }
}