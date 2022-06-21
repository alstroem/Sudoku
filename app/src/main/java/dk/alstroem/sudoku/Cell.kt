package dk.alstroem.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.alstroem.logic.data.CellCandidates
import dk.alstroem.logic.data.GridCell
import dk.alstroem.sudoku.ui.theme.SudokuTheme
import timber.log.Timber

@Composable
fun Cell(
    data: GridCell,
    candidates: CellCandidates,
    modifier: Modifier = Modifier,
    showAutoCandidates: Boolean = false,
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

        if (data.value > 0) {
            Text(
                text = data.value.toString(),
                style = MaterialTheme.typography.headlineSmall,
                color = if (data.error) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onBackground
            )
        } else {
            CandidatesGrid(
                candidates = if (showAutoCandidates) candidates.autoCandidates else candidates.userCandidates
            )
        }
    }
}

@Composable
fun CandidatesGrid(
    candidates: Map<Int, Boolean>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        candidates.forEach {
            if (it.value) {
                Candidate(
                    value = it.key,
                    modifier = Modifier.align(getCandidateAlignment(it.key))
                )
            }
        }
    }
}

@Composable
fun Candidate(
    value: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = value.toString(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.padding(start = 2.dp, end = 2.dp)
    )
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

private fun getCandidateAlignment(value: Int): Alignment {
    return when (value) {
        1 -> Alignment.TopStart
        2 -> Alignment.TopCenter
        3 -> Alignment.TopEnd
        4 -> Alignment.CenterStart
        5 -> Alignment.Center
        6 -> Alignment.CenterEnd
        7 -> Alignment.BottomStart
        8 -> Alignment.BottomCenter
        9 -> Alignment.BottomEnd
        else -> throw IllegalArgumentException()
    }
}

@Preview(showBackground = true)
@Composable
fun CellPreview() {
    SudokuTheme {
        Cell(data = GridCell(), candidates = CellCandidates()) { _, _ -> Timber.d("Cell clicked") }
    }
}
