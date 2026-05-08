package com.wayuyu.mepatech.models

data class RequestItem(
    val requestId: String = "",
    val type: String = "",
    val location: String = "",
    val description: String = "",
    val userId: String = "",
    val status: String = "Pending"
)
