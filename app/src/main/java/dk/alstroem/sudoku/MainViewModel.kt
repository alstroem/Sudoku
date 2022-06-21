package dk.alstroem.sudoku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.alstroem.logic.data.Generator
import dk.alstroem.logic.data.Grid
import dk.alstroem.logic.data.Level
import dk.alstroem.logic.data.getNonetStart
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
        viewModelScope.launch {
            val selectedCell = uiState.selectedCell.let {
                if (it.row == row && it.column == column) {
                    SelectedCell(-1, -1)
                } else SelectedCell(row, column)
            }

            uiState = uiState.copy(selectedCell = selectedCell)
        }
    }

    fun addInput(number: Int) {
        viewModelScope.launch {
            val grid = withContext(Dispatchers.IO) {
                uiState.grid.copy().apply {
                    val row = uiState.selectedCell.row
                    val column = uiState.selectedCell.column
                    this[row, column] = this[row, column].copy(value = number, error = !isUnused(row, column, number))
                    updateAutoCandidates(row, column, number, false)
                }
            }

            uiState = uiState.copy(grid = grid)
        }
    }

    fun clearInput() {
        viewModelScope.launch {
            val grid = withContext(Dispatchers.IO) {
                uiState.grid.copy().apply {
                    val row = uiState.selectedCell.row
                    val column = uiState.selectedCell.column
                    val cell = this[row, column]
                    this[row, column] = cell.copy(value = 0, error = false)
                    updateAutoCandidates(row, column, cell.value, true)
                }
            }

            uiState = uiState.copy(grid = grid)
        }
    }

    fun showAutoCandidates(selected: Boolean) {
        uiState = uiState.copy(showAutoCandidates = selected)
    }

    fun addCandidate(number: Int) {
        viewModelScope.launch {
            val grid = withContext(Dispatchers.IO) {
                uiState.grid.copy().apply {
                    val row = uiState.selectedCell.row
                    val column = uiState.selectedCell.column
                    val cellCandidates = candidates[row][column]

                    val selectedCandidates = if (uiState.showAutoCandidates) cellCandidates.autoCandidates else cellCandidates.userCandidates
                    val newCandidates = if (selectedCandidates.contains(number)) {
                        selectedCandidates.minus(number)
                    } else selectedCandidates.plus(number to true)

                    candidates[row][column] = if (uiState.showAutoCandidates) {
                        cellCandidates.copy(autoCandidates = newCandidates)
                    } else cellCandidates.copy(userCandidates = newCandidates)
                }
            }

            uiState = uiState.copy(grid = grid)
        }
    }

    fun clearCandidates() {
        viewModelScope.launch {
            val grid = withContext(Dispatchers.IO) {
                uiState.grid.copy().apply {
                    val row = uiState.selectedCell.row
                    val column = uiState.selectedCell.column

                    candidates[row][column] = if (uiState.showAutoCandidates) {
                        candidates[row][column].copy(autoCandidates = emptyMap())
                    } else {
                        candidates[row][column].copy(userCandidates = emptyMap())
                    }
                }
            }

            uiState = uiState.copy(grid = grid)
        }
    }

    private fun Grid.updateAutoCandidates(row: Int, column: Int, digit: Int, display: Boolean) {
        val nonetRowStart = size.getNonetStart(row)
        val nonetColumnStart = size.getNonetStart(column)
        updateAutoCandidatesInNonet(nonetRowStart, nonetColumnStart, digit, display)
        updateAutoCandidatesInRow(row, digit, display)
        updateAutoCandidatesInColumn(column, digit, display)
    }

    private fun Grid.updateAutoCandidatesInNonet(rowStart: Int, columnStart: Int, digit: Int, display: Boolean) {
        for (row in size.nonetRange) {
            for (column in size.nonetRange) {
                if (candidates[rowStart + row][columnStart + column].autoCandidates.contains(digit)) {
                    val autoCandidates = candidates[rowStart + row][columnStart + column].autoCandidates.plus(digit to display)
                    candidates[rowStart + row][columnStart + column] = candidates[rowStart + row][columnStart + column].copy(autoCandidates = autoCandidates)
                }
            }
        }
    }

    private fun Grid.updateAutoCandidatesInRow(row: Int, digit: Int, display: Boolean) {
        for (column in size.indexRange) {
            if (candidates[row][column].autoCandidates.contains(digit)) {
                val autoCandidates = candidates[row][column].autoCandidates.plus(digit to display)
                candidates[row][column] = candidates[row][column].copy(autoCandidates = autoCandidates)
            }
        }
    }

    private fun Grid.updateAutoCandidatesInColumn(column: Int, digit: Int, display: Boolean) {
        for (row in size.indexRange) {
            if (candidates[row][column].autoCandidates.contains(digit)) {
                val autoCandidates = candidates[row][column].autoCandidates.plus(digit to display)
                candidates[row][column] = candidates[row][column].copy(autoCandidates = autoCandidates)
            }
        }
    }
}
