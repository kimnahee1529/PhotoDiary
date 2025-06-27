package com.todaylab.cleanarchtemplate.local.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todaylab.cleanarchtemplate.local.model.LuckyResultLocal
import com.todaylab.cleanarchtemplate.local.room.RoomConstant

@Dao
interface LuckyResultDao {
    @Query(
        "SELECT * " +
                "FROM ${RoomConstant.TABLE.USER_LUCKY}" +
                "WHERE id = :id"
    )
    suspend fun getById(id: String): LuckyResultLocal?

    @Query(
        "SELECT * " +
                "FROM ${RoomConstant.TABLE.USER_LUCKY}"
    )
    suspend fun getAll(): List<LuckyResultLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: LuckyResultLocal)

    @Query(
        "DELETE " +
                "FROM ${RoomConstant.TABLE.USER_LUCKY}" +
                "WHERE id = :id"
    )
    suspend fun deleteById(id: String)
}