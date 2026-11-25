package com.tc1412.reminder.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReminderService {
    @GET("reminders") suspend fun getReminders(): List<ReminderDto>
    @POST("reminders") suspend fun createReminder(@Body reminder: ReminderDto): ReminderDto
    @PUT("reminders/{id}") suspend fun updateReminder(@Path("id") id: Int, @Body reminder: ReminderDto): ReminderDto
    @DELETE("reminders/{id}") suspend fun deleteReminder(@Path("id") id: Int): Response<Unit>
}
