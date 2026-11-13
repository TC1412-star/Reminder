package com.tc1412.reminder.preferences

sealed class UITheme(name: String) {
    object Dark : UITheme("Oscuro")
    object Light : UITheme("Claro")
    object System : UITheme("Dejar que el sistema decida")
    companion object {
        val all = listOf(Dark, Light, System)
    }
}
sealed class Sort(name: String) {
    object Default : Sort("Por defecto")
    object Name : Sort("Por nombre (A-z)")
    object DateTime : Sort("Por fecha límite")
}