package dk.alstroem.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.alstroem.logic.data.Grid
import dk.alstroem.sudoku.ui.theme.SudokuTheme

@Composable
fun GridScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        SudokuGrid(data = uiState.grid, modifier = Modifier.background(Color.LightGray).padding(4.dp))
    }
}

@Composable
fun SudokuGrid(
    data: Grid,
    modifier: Modifier = Modifier
) {
    CustomGrid(size = data.size.count, modifier = modifier) {
        data.grid.flatMap { it.asIterable() }
            .forEach {
                Cell(value = it)
            }
    }
}

@Composable
fun CustomGrid(
    size: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val columnHeights = Array(size) { 0 }
        val columnWidths = Array(size) { 0 }
        val padding = 2.dp.roundToPx()
        placeables.forEachIndexed { index, placeable ->
            val row = index / size
            columnWidths[row] += placeable.width + padding

            val column = index % size
            columnHeights[column] += placeable.height + padding
        }

        val width = (columnWidths.maxOrNull()?.minus(padding) ?: constraints.minWidth).coerceAtMost(constraints.maxWidth)
        val height = (columnHeights.maxOrNull()?.minus(padding) ?: constraints.minHeight).coerceAtMost(constraints.maxHeight)

        layout(width, height) {
            val columnX = Array(size) { 0 }
            val columnY = Array(size) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index / size
                val column = index % size

                placeable.placeRelative(
                    x = columnX[row],
                    y = columnY[column]
                )

                columnX[row] += placeable.width + padding
                columnY[column] += placeable.height + padding
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GridPreview() {
    SudokuTheme {
        SudokuGrid(data = Grid())
    }
}
