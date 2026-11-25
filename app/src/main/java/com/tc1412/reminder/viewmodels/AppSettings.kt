package com.tc1412.reminder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc1412.reminder.preferences.DataStoring
import com.tc1412.reminder.preferences.Sort
import com.tc1412.reminder.preferences.UITheme
import com.tc1412.reminder.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AppSettings(private val dataStoring: DataStoring) : ViewModel() {
    private val _userPreferences = MutableStateFlow(
        UserPreferences(UITheme.System, Sort.Default, hidePast = false)
    )
    val userPreferences = _userPreferences.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoring.userPreferences
                .catch { }
                .collect { _userPreferences.value = it }
        }
    }

    fun setTheme(value: UITheme) = viewModelScope.launch { dataStoring.setUiTheme(value) }
    fun setSort(value: Sort) = viewModelScope.launch { dataStoring.setSort(value) }
    fun setHidePast(value: Boolean) = viewModelScope.launch { dataStoring.setHidePast(value) }
}
