package ru.iyshcherbakov.dntestapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.iyshcherbakov.dntestapp.domain.AddChildNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.DeleteNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.GenerateNodeNameUseCase
import ru.iyshcherbakov.dntestapp.domain.GetNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.GetRootNodeUseCase
import ru.iyshcherbakov.dntestapp.domain.Node
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NodeViewModel @Inject constructor(
    private val getRootNode: GetRootNodeUseCase,
    private val getNode: GetNodeUseCase,
    private val deleteChildNode: DeleteNodeUseCase,
    private val addChildNode: AddChildNodeUseCase,
    private val generateNodeName: GenerateNodeNameUseCase
) : ViewModel() {

    private val _currentNode = MutableStateFlow<Node?>(null)
    val currentNode: StateFlow<Node?> = _currentNode

    private val _navigationStack = MutableStateFlow<List<String>>(emptyList())
    val navigationStack: StateFlow<List<String>> = _navigationStack

    init {
        loadRootNode()
    }

    private fun loadRootNode() {
        viewModelScope.launch {
            _currentNode.value = getRootNode()
            _navigationStack.value = listOf(_currentNode.value?.id ?: "")
        }
    }

    fun navigateToNode(nodeId: String) {
        viewModelScope.launch {
            _currentNode.value = getNode(nodeId)
            _navigationStack.value += nodeId
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            val parentId = _navigationStack.value[_navigationStack.value.size - 2]
            _currentNode.value = getNode(parentId)
            _navigationStack.value = _navigationStack.value.dropLast(1)
        }
    }

    fun addChildNode() {
        viewModelScope.launch {
            val parentId = _currentNode.value?.id ?: return@launch
            val child = Node(
                id = UUID.randomUUID().toString(),
                name = generateNodeName(),
                parentId = parentId
            )
            addChildNode(parentId, child)
            _currentNode.value = getNode(parentId)
        }
    }

    fun deleteNode(node: Node) {
        viewModelScope.launch {
            deleteChildNode(node)
            _currentNode.value?.id?.let { getNode(it) }?.let {
                _currentNode.value = it
            }
        }
    }
}