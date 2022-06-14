package dk.alstroem.logic.data

data class GridCell(
    val row: Int = -1,
    val column: Int = -1,
    val value: Int = 0,
    val isGenerated: Boolean = false,
    val selected: Boolean = false,
    val error: Boolean = false,
    val candidates: Set<Int> = emptySet()
)
