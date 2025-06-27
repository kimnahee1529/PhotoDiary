package com.todaylab.cleanarchtemplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todaylab.cleanarchtemplate.domain.model.Color
import com.todaylab.cleanarchtemplate.local.room.RoomConstant
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