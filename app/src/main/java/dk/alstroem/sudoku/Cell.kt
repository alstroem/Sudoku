package dk.alstroem.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.alstroem.logic.data.GridCell
import dk.alstroem.sudoku.ui.theme.SudokuTheme
import timber.log.Timber

@Composable
fun Cell(
    data: GridCell,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelected: ((Int, Int) -> Unit)
) {
    val color = getBackgroundColor(data = data, isSelected = isSelected)

    Box(
        modifier = modifier
            .size(40.dp)
            .background(color)
            .clickable(enabled = !data.isGenerated) { onSelected(data.row, data.column) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = data.value.takeIf { it > 0 }?.toString() ?: "",
            style = MaterialTheme.typography.headlineSmall,
            color = if (data.error) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun getBackgroundColor(data: GridCell, isSelected: Boolean): Color {
    return when {
        data.isGenerated -> MaterialTheme.colorScheme.primaryContainer
        isSelected -> MaterialTheme.colorScheme.tertiaryContainer
        data.error -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.background
    }
}

@Preview(showBackground = true)
@Composable
fun CellPreview() {
    SudokuTheme {
        Cell(data = GridCell()) { _, _ -> Timber.d("Cell clicked") }
    }
}
