package com.todaylab.photodiary.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

/**
 * domain lucky Result model
 */
data class LuckyResult(
    val id: String,
    val date: LocalDate,
    val animal: String,
    val numbers: Int,
    val initials: List<Char>,
    val color: Color,
) {
    companion object {
        fun generateLuckyResult(): LuckyResult {
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            val todayId = today.format(formatter)

            return LuckyResult(
                id = todayId,
                date = today,
                animal = getRandomAnimal(),
                numbers = getRandomNumber(),
                initials = getRandomInitials(),
                color = getRandomColor()
            )
        }

        private fun getRandomAnimal(): String {
            val animals = listOf("강아지", "고양이", "토끼", "여우", "판다", "원숭이", "개구리",)
            return animals.random()
        }

        private fun getRandomNumber(): Int {
            return Random.nextInt(1, 46)
        }

        private fun getRandomInitials(): List<Char> {
            val chosungs = listOf('ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ')
            return List(2) { chosungs.random() }
        }

        private fun getRandomColor(): Color {
            return Color.entries.toTypedArray().random()
        }
    }
}


enum class Color {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    PURPLE,
    BLACK,
    WHITE,
    PINK
}
