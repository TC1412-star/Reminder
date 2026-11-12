package com.tc1412.reminder.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc1412.reminder.AppStatus
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.repositories.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReminderList(private val repository: ReminderRepository) : ViewModel() {
    private val _appStatus = MutableStateFlow<AppStatus>(AppStatus.Loading)
    val appStatus = _appStatus.asStateFlow()

    init {
        loadReminders()
    }

    fun loadReminders() {
        viewModelScope.launch {
            _appStatus.value = AppStatus.Loading

            try {
                _appStatus.value = AppStatus.Ready(mutableStateListOf<Reminder>().apply { addAll(repository.getReminders()) })
            } catch (e: Exception) {
                _appStatus.value = AppStatus.Error(e.message ?: "Un error desconocido ocurrió,")
            }
        }
    }

    fun storeReminder(rem: Reminder, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                repository.storeReminder(rem)
                onSuccess?.invoke()
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        }
    }
    fun removeReminder(idx: Int, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                repository.removeReminder(idx)
                onSuccess?.invoke()
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        }
    }
}