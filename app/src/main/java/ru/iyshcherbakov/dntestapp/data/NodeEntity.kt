package ru.iyshcherbakov.dntestapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("nodes")
data class NodeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val parentId: String,
)
