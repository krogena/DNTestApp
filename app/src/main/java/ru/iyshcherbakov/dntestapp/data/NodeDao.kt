package ru.iyshcherbakov.dntestapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

//Инициализация Data Access Object
@Dao
interface NodeDao {
    @Insert
    suspend fun insert(node: NodeEntity)

    @Delete
    suspend fun delete(node: NodeEntity)

    @Query("SELECT * FROM nodes WHERE id = :id")
    suspend fun getNode(id: String): NodeEntity?

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    suspend fun getNodeChildren(parentId: String): List<NodeEntity>

    @Query("SELECT * FROM nodes WHERE parentId IS NULL LIMIT 1 ")
    suspend fun getRootNode(): NodeEntity?

    @Query("SELECT COUNT(*) FROM nodes WHERE parentId IS NULL")
    suspend fun rootNodeCount(): Int
}