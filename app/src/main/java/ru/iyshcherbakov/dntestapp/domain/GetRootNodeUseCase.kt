package ru.iyshcherbakov.dntestapp.domain

class GetRootNodeUseCase (private val repository: NodeRepository){
    suspend operator fun invoke(): Node? = repository.getRootNode()
}