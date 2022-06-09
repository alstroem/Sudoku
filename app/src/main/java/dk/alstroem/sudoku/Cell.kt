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

@Composable
fun Cell(
    data: GridCell,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelected: ((Int, Int) -> Unit)? = null
) {
    val color = getBackgroundColor(hasValue = data.value > 0, isSelected = isSelected)

    Box(
        modifier = modifier
            .size(40.dp)
            .background(color)
            .clickable(enabled = data.value == 0) { onSelected?.invoke(data.row, data.column) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (data.value > 0) data.value.toString() else "",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun getBackgroundColor(hasValue: Boolean, isSelected: Boolean): Color {
    return when {
        hasValue -> MaterialTheme.colorScheme.primaryContainer
        isSelected -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.background
    }
}

@Preview(showBackground = true)
@Composable
fun CellPreview() {
    SudokuTheme {
        Cell(data = GridCell())
    }
}
