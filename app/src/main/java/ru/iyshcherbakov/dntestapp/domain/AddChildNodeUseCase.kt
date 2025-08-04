package ru.iyshcherbakov.dntestapp.domain

class AddChildNodeUseCase(private val repository: NodeRepository) {
    suspend operator fun invoke(parentId: String, child: Node) = repository.addChildNode(parentId, child)
}