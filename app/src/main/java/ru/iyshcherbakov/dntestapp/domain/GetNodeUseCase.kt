package ru.iyshcherbakov.dntestapp.domain

class GetNodeUseCase (private val repository: NodeRepository){
    suspend operator fun invoke(id: String) = repository.getNode(id)

}