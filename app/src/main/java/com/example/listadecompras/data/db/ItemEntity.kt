package com.example.listadecompras.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String = "",
    val valor: Double = 0.0,
    val quantidade: Int = 0
)