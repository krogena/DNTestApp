package ru.iyshcherbakov.dntestapp.domain

class GenerateNodeNameUseCase(private val repository: NodeRepository) {
    suspend operator fun invoke(): String = repository.generateNodeName()
}