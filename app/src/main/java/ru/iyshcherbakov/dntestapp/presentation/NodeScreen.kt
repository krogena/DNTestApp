
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.iyshcherbakov.dntestapp.R
import ru.iyshcherbakov.dntestapp.domain.Node
import ru.iyshcherbakov.dntestapp.presentation.NodeViewModel

@Composable
fun NodeScreen(
    onNavigateToNode:(String) -> Unit
) {
    val viewModel: NodeViewModel = hiltViewModel()
    val currentNode by viewModel.currentNode.collectAsState()
    val navigationStack by viewModel.navigationStack.collectAsState()

    Scaffold(
        topBar = {
            NodeAppBar(
                canNavigateUp = navigationStack.size > 1,
                onNavigateUp = { viewModel.navigateUp() },
                onAddNode = { viewModel.addChildNode() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            currentNode?.let { node ->
                NodeDetails(node = node)
                Spacer(modifier = Modifier.height(16.dp))
                ChildrenList(
                    children = node.children,
                    onChildClick = onNavigateToNode,
                    onDeleteChild = { viewModel.deleteNode(it) }
                )
            } ?: Text("Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NodeAppBar(
    canNavigateUp: Boolean,
    onNavigateUp: () -> Unit,
    onAddNode: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.tv_top_app_bar)) },
        navigationIcon = {
            if (canNavigateUp) {
                IconButton(onClick = onNavigateUp) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            IconButton(onClick = onAddNode) {
                Icon(Icons.Filled.Add, contentDescription = "Add Node")
            }
        }
    )
}

@Composable
private fun NodeDetails(node: Node) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(R.string.tv_node_details),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.tv_node_id)+node.id,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                stringResource(R.string.tv_node_name)+node.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ChildrenList(
    children: List<Node>,
    onChildClick: (String) -> Unit,
    onDeleteChild: (Node) -> Unit
) {
    if (children.isEmpty()) {
        Text(
            stringResource(R.string.tv_node_empty),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn {
            items(
                items = children,
                key = { it.id }
            ) { child ->
                ChildItem(
                    node = child,
                    onClick = { onChildClick(child.id) },
                    onDelete = { onDeleteChild(child) }
                )
            }
        }
    }
}

@Composable
private fun ChildItem(
    node: Node,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = node.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.tv_node_id)+node.id,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}