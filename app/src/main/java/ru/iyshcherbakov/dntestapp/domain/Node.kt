package ru.iyshcherbakov.dntestapp.domain

data class Node(
    val id: String,
    val name: String,
    val parentId: String,
    val children: MutableList<Node> = mutableListOf()
)
