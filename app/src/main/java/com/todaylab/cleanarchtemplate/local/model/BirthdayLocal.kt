package com.todaylab.cleanarchtemplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_birthdays")
data class BirthdayLocal(
    @PrimaryKey val userId: String,
    val birthDate: String // 예: "1995-06-06"
)