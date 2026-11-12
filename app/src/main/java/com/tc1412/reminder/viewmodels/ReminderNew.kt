package com.tc1412.reminder.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc1412.reminder.Category
import com.tc1412.reminder.NewReminderState
import com.tc1412.reminder.NewReminderStatus
import com.tc1412.reminder.Priority
import com.tc1412.reminder.Reminder
import com.tc1412.reminder.repositories.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ReminderNew(
    private val repository: ReminderRepository,
    var initialStatus: NewReminderState = NewReminderState()
) : ViewModel() {
    private val _state = MutableStateFlow<NewReminderStatus>(NewReminderStatus.Creating)
    val state = _state.asStateFlow()
    var status by mutableStateOf(initialStatus)

    fun setTitle(title:String) {
        status = status.copy(title = title)
    }
    fun setDescription(title:String) {
        status = status.copy(description = title)
    }
    fun setDateTime(dateTime: LocalDateTime) {
        status = status.copy(dateTime = dateTime)
    }
    fun setCategory(category: Category) {
        status = status.copy(category = category)
    }
    fun setPriority(priority: Priority) {
        status = status.copy(priority = priority)
    }

    val titleError: Boolean
        get() = status.title.trim().length < 4
    val descriptionError: Boolean
        get() = status.description.length > 200
    val categoryError: Boolean
        get() = status.category == null
    val priorityError: Boolean
        get() = status.priority == null
    /*val canSend: Boolean
        get() = status.title.trim().length >= 4
                && status.description.trim().length <= 200
                && status.priority != null
                && status.category != null*/
    val cannotSend: Boolean
        get() = titleError || descriptionError || categoryError || priorityError
    fun send(onSuccess: () -> Unit) {
        if(cannotSend)
            return
        viewModelScope.launch {
            try {
                _state.value = NewReminderStatus.Sending
                repository.storeReminder(Reminder(
                    id = 0,
                    name = status.title,
                    description = status.description,
                    time = status.dateTime,
                    category = status.category!!,
                    priority = status.priority!!
                ))
                onSuccess()
                _state.value = NewReminderStatus.Creating
            } catch (e: Exception) {
                _state.value = NewReminderStatus.Error(e.message ?: "Error desconocido.")
            }
        }
    }
}