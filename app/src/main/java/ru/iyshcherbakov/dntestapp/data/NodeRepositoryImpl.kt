package ru.iyshcherbakov.dntestapp.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.iyshcherbakov.dntestapp.domain.Node
import ru.iyshcherbakov.dntestapp.domain.NodeRepository
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.UUID

class NodeRepositoryImpl(
    private val dao: NodeDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): NodeRepository {

    //Получаем корневой уровень
    override suspend fun getRootNode(): Node? = withContext(dispatcher) {
        // Check if root exists first
        if (dao.rootNodeCount() == 0) {
            val newNode = NodeEntity(
                id = UUID.randomUUID().toString(),
                name = generateNodeName(),
                parentId = null
            )
            dao.insert(newNode)
            newNode.toNodeWithChildren()
        } else {
            dao.getRootNode()?.toNodeWithChildren()
        }
    }

    //Получаем ноду
    override suspend fun getNode(id: String): Node? = withContext(dispatcher) {
        dao.getNode(id)?.toNodeWithChildren()
    }

    //Сохранить ноду в базе данных
    override suspend fun saveNode(node: Node) = withContext(dispatcher) {
        dao.insert(node.toEntity())
    }

    //Удаление ноды
    override suspend fun deleteNode(node: Node) = withContext(dispatcher) {
        deleteNodeChildren(node.id)
        dao.delete(node.toEntity())
    }
    //Удаление детей ноды
    private suspend fun deleteNodeChildren(parentId: String) {
        val children = dao.getNodeChildren(parentId)
        for (child in children) {
            //Удаляем детей этого ребенка
            deleteNodeChildren(child.id)
            //Удаляем самого ребенка
            dao.delete(child)
        }
    }

    //Добавление детей ноды
    override suspend fun addChildNode(
        parentId: String,
        child: Node
    ) {
        dao.insert(child.toEntity())
    }

    //Название формируем из последних 20 байт хэша узла по аналогии с адресом кошельков Ethereum
     override suspend fun generateNodeName(): String {
        val bytes = ByteArray(32)
        SecureRandom().nextBytes(bytes)
        val hash = MessageDigest.getInstance("SHA-256").digest(bytes)
        val last20Bytes = hash.takeLast(20).toByteArray()
        return "0x" + last20Bytes.joinToString("") { "%02x".format(it) }
    }


    //Конвертация инстанции базы данных в инстанцию дата класса
    private suspend fun NodeEntity.toNodeWithChildren():Node{
        val childrenEntities = dao.getNodeChildren(this.id)
        val children = mutableListOf<Node>()

        childrenEntities.map { childEntity ->
            childEntity.toNodeWithChildren()
        }.forEach { childNode ->
            children.add(childNode)
        }

        return Node(
            id = this.id,
            name = this.name,
            parentId = this.parentId,
            children = children
        )
    }

    //Конвертация инстанции дата класса в инстанцию базы данных
    private fun Node.toEntity(): NodeEntity{
        return NodeEntity(
            this.id,
            this.name,
            this.parentId
        )
    }


}