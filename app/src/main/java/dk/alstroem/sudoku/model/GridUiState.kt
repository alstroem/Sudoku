package dk.alstroem.sudoku.model

import dk.alstroem.logic.data.Grid

data class GridUiState(
    val grid: Grid = Grid(),
    val selectedCell: SelectedCell = SelectedCell(-1, -1)
)
