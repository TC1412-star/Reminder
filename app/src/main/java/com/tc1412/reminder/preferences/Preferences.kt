package com.tc1412.reminder.preferences

sealed class UITheme(val key: Int, val name: String) {
    object Dark : UITheme(1, "Oscuro")
    object Light : UITheme(2, "Claro")
    object System : UITheme(0, "Dejar que el sistema decida")
    companion object {
        val all get() = listOf(Dark, Light, System)
        fun getByKey(key: Int) = when(key) {
            1 -> Dark
            2 -> Light
            else -> System
        }
    }
}
sealed class Sort(val key: Int, val name: String) {
    object Default : Sort(0, "Por defecto")
    object Name : Sort(1, "Por nombre (A-z)")
    object DateTime : Sort(2, "Por fecha límite")
    companion object {
        val all get() = listOf(Default, Name, DateTime)
        fun getByKey(key: Int) = when(key) {
            1 -> Name
            2 -> DateTime
            else -> Default
        }
    }
}