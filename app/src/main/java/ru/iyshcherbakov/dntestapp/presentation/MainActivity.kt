package ru.iyshcherbakov.dntestapp.presentation

import NodeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.iyshcherbakov.dntestapp.ui.theme.DNTestAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DNTestAppTheme {
                val viewModel: NodeViewModel = hiltViewModel()
                NodeScreen { nodeId ->
                    viewModel.navigateToNode(nodeId)
                }
            }
        }
    }
}