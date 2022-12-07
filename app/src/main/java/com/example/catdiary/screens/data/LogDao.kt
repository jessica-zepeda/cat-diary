package com.example.catdiary.screens.data

import androidx.room.*
import androidx.lifecycle.LiveData
import com.example.catdiary.screens.model.Log

@Dao
interface LogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLog(log: Log)

    @Update
    suspend fun updateLog(log: Log)

    @Delete
    suspend fun deleteLog(log: Log)

    @Query("DELETE FROM log_table")
    suspend fun deleteAllLogs()

    @Query("SELECT * FROM log_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Log>>

}