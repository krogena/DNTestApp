package ru.iyshcherbakov.dntestapp.domain

class DeleteNodeUseCase (private val repository: NodeRepository){
    suspend operator fun invoke(node: Node) = repository.deleteNode(node)
}