package ru.iyshcherbakov.dntestapp.domain

class SaveNodeUseCase (private val repository: NodeRepository){
    suspend operator fun invoke(node: Node) = repository.saveNode(node)
}