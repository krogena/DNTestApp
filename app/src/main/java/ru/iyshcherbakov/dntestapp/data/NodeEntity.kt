package ru.iyshcherbakov.dntestapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Инициализация "таблицы"
@Entity("nodes")
data class NodeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val parentId: String?,
)
