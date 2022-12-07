package com.example.catdiary.screens.repository

import androidx.lifecycle.LiveData
import com.example.catdiary.screens.data.LogDao
import com.example.catdiary.screens.model.Log

class LogRepository(private val logDao: LogDao) {
    val readAllData: LiveData<List<Log>> = logDao.readAllData()

    suspend fun addLog(log: Log){
        logDao.addLog(log)
    }

    suspend fun updateLog(log: Log){
        logDao.updateLog(log)
    }

    suspend fun deleteLog(log: Log){
        logDao.deleteLog(log)
    }

    suspend fun deleteAllLogs(){
        logDao.deleteAllLogs()
    }
}