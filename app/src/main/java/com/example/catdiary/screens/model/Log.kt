package com.example.catdiary.screens.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "log_table")
data class Log(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val dateAndTime: String,
    val event: String,
    val quantity: String,
    val comment: String,
): Parcelable
