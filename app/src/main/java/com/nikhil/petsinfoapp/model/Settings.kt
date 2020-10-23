package com.nikhil.petsinfoapp.model

data class Settings(
    var isChatEnabled: Boolean = true,
    var isCallEnabled: Boolean = true,
    var workHours: String
)