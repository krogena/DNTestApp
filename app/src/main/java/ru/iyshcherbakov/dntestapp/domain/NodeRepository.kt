package ru.iyshcherbakov.dntestapp.domain

interface NodeRepository {
    suspend fun getRootNode(): Node?
    suspend fun getNode(id: String): Node?
    suspend fun saveNode(node: Node)
    suspend fun deleteNode(node: Node)
    suspend fun addChildNode(parentId: String, child: Node)
    suspend fun generateNodeName(): String
}