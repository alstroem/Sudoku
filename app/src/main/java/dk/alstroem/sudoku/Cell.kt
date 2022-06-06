package dk.alstroem.sudoku

import androidx.compose.foundation.background
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
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (data.value > 0) data.value.toString() else "",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CellPreview() {
    SudokuTheme {
        Cell(data = GridCell())
    }
}
