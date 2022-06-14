package dk.alstroem.sudoku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.alstroem.logic.data.GridSize

@Composable
fun CandidatesInput(
    size: GridSize,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        items(size.valueRange.toList()) { item ->
            NumberCell(
                value = item,
                modifier = Modifier.size(40.dp),
                onClick = {}
            )
        }
    }
}
