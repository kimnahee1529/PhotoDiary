package com.todaylab.photodiary.local.room.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todaylab.photodiary.local.model.DiaryLocal

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY date DESC")
    suspend fun getAll(): List<DiaryLocal>

    @Query("SELECT * FROM diary WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): DiaryLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diary: DiaryLocal)

    @Query("DELETE FROM diary WHERE id = :id")
    suspend fun deleteById(id: Long)

}