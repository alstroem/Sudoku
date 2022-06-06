package dk.alstroem.sudoku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.logic.data.Generator
import dk.alstroem.logic.data.Level
import dk.alstroem.sudoku.model.GridUiState
import dk.alstroem.sudoku.model.SelectedCell
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
        val selectedCell = uiState.selectedCell.let {
            if (it.row == row && it.column == column) {
                SelectedCell(-1, -1)
            } else SelectedCell(row, column)
        }

        uiState = uiState.copy(selectedCell = selectedCell)
    }
}
