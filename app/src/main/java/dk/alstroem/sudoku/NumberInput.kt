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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dk.alstroem.logic.data.GridSize
import dk.alstroem.sudoku.ui.theme.SudokuTheme

@Composable
fun NumberInput(
    size: GridSize,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    onNumberSelected: (Int) -> Unit,
    onNumberDeleted: () -> Unit
) {
    val cellModifier = Modifier.size(52.dp)

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(size.valueRange.toList()) { item ->
            NumberCell(
                value = item,
                textStyle = textStyle,
                modifier = cellModifier,
                onClick = onNumberSelected
            )
        }

        item {
            NumberErase(
                size = with(LocalDensity.current) { textStyle.fontSize.toDp() },
                modifier = cellModifier,
                onClick = onNumberDeleted
            )
        }
    }
}

@Composable
fun NumberCell(
    value: Int,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    onClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick(value) }
    ) {
        Text(
            text = value.toString(),
            style = textStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun NumberErase(
    size: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.number_erase),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            contentDescription = "Number erase button",
            modifier = Modifier.size(size)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberInputPreview() {
    SudokuTheme {
        NumberInput(
            size = GridSize.Normal,
            onNumberSelected = {}
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun NumberCellPreview() {
    SudokuTheme {
        NumberCell(value = 1) { }
    }
}
