package dk.alstroem.sudoku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.logic.data.Generator
import dk.alstroem.logic.data.Level
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    var uiState by mutableStateOf(GridUiState())
        private set

    init {
        generateGrid()
    }

    private fun generateGrid() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                Generator().generate(Level.Medium)
            }

            uiState = uiState.copy(grid = data)
        }
    }

    fun selectCell(row: Int, column: Int) {
        val cell = uiState.grid[row, column].copy(selected = true)
        
    }
}
