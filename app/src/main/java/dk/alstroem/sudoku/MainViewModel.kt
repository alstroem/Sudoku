package dk.alstroem.sudoku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.logic.data.Generator
import dk.alstroem.logic.data.Level
import dk.alstroem.logic.data.isUnused
import dk.alstroem.sudoku.model.GridUiState
import dk.alstroem.sudoku.model.SelectedCell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val generator = Generator()

    var uiState by mutableStateOf(GridUiState())
        private set

    init {
        generateGrid()
    }

    private fun generateGrid() {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                generator.generate(Level.Medium)
            }

            uiState = uiState.copy(grid = data)
        }
    }

    fun selectCell(row: Int, column: Int) {
        val selectedCell = uiState.selectedCell.let {
            if (it.row == row && it.column == column) {
                SelectedCell(-1, -1)
            } else SelectedCell(row, column)
        }

        uiState = uiState.copy(selectedCell = selectedCell)
    }

    fun addNumber(number: Int) {
        val grid = uiState.grid.copy()
        val row = uiState.selectedCell.row
        val column = uiState.selectedCell.column
        grid[row, column] = grid[row, column].copy(value = number, error = !grid.isUnused(row, column, number))
        uiState = uiState.copy(grid = grid)
    }

    fun clearNumber() {
        val grid = uiState.grid.copy()
        val row = uiState.selectedCell.row
        val column = uiState.selectedCell.column
        grid[row, column] = grid[row, column].copy(value = 0, error = false)
        uiState = uiState.copy(grid = grid)
    }
}
