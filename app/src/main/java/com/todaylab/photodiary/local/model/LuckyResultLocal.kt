package com.todaylab.photodiary.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todaylab.photodiary.domain.model.Color
import com.todaylab.photodiary.local.room.RoomConstant
import java.time.LocalDate

// id of singleton lucky local model

/**
 * local weather model
 * room db entity
 */
@Entity(tableName = RoomConstant.TABLE.USER_LUCKY)
data class LuckyResultLocal(
    @PrimaryKey val id: String,
    val date: LocalDate,
    val animal: String,
    val numbers: Int,
    val initials: List<Char>,
    val color: Color,
)