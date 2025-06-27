package com.todaylab.cleanarchtemplate.local.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todaylab.cleanarchtemplate.local.model.LuckyResultLocal
import com.todaylab.cleanarchtemplate.local.room.RoomConstant

/**
 * only one weather entity exists at a time, pk = 0 (singleton)
 */
@Dao
interface LuckyResultDao {
    /**
     * get singleton weather entity, if exists
     */
    @Query("SELECT * " +
                "FROM ${RoomConstant.TABLE.USER_LUCKY} " +
                "WHERE id = :id"
    )
    suspend fun getLuckyResult(id: String): LuckyResultLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLuckyResult(luckyResult: LuckyResultLocal)

}