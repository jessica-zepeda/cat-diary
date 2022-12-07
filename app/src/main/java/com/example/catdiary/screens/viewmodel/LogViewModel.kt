package com.example.catdiary.screens.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.catdiary.screens.data.UserDatabase
import com.example.catdiary.screens.model.Log
import com.example.catdiary.screens.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Log>>
    private val repository: LogRepository

    init {
        val logDao = UserDatabase.getDatabase(application).logDao()
        repository = LogRepository(logDao)
        readAllData = repository.readAllData
    }

    fun addLog(log: Log){
        viewModelScope.launch(Dispatchers.IO){
            repository.addLog(log)
        }
    }
    fun updateLog(log: Log){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateLog(log)
        }
    }
    fun deleteLog(log: Log){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLog(log)
        }
    }

    fun deleteAllLogs(log: Log){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllLogs()
        }
    }

}