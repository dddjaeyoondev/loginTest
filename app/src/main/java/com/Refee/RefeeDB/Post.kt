package com.Refee.RefeeDB

data class Post(
    val title: String = "",
    val body: String = "",
    val userId: String = "",
    val timestamp: Long = 0L,
    val id: String = "", // Firestore 문서 ID
)