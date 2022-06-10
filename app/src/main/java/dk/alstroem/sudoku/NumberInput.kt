package dk.alstroem.sudoku

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.alstroem.logic.data.GridSize
import dk.alstroem.sudoku.ui.theme.SudokuTheme

@Composable
fun NumberInput(
    size: GridSize,
    modifier: Modifier = Modifier,
    onNumberSelected: (Int) -> Unit,
    onNumberDeleted: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(size.valueRange.toList()) { item ->
            NumberCell(
                value = item,
                onClick = onNumberSelected
            )
        }
        item { NumberErase(onClick = onNumberDeleted) }
    }
}

@Composable
fun NumberCell(
    value: Int,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(52.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick(value) }
    ) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun NumberErase(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = R.drawable.number_erase),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
        contentDescription = "Number erase button",
        modifier = modifier.size(52.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun NumberInputPreview() {
    SudokuTheme {
        NumberInput(
            size = GridSize.Normal,
            onNumberSelected = {},
            onNumberDeleted = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberCellPreview() {
    SudokuTheme {
        NumberCell(value = 1) { }
    }
}
