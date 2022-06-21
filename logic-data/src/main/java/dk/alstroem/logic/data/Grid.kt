package dk.alstroem.logic.data

import timber.log.Timber

data class Grid(
    val size: GridSize = GridSize.Normal,
    private val grid: Array<Array<GridCell>> = Array(size.count) { Array(size.count) { GridCell() } },
    val candidates: Array<Array<CellCandidates>> = Array(size.count) { Array(size.count) { CellCandidates() } }
) {

    operator fun get(rowIndex: Int, columnIndex: Int) = grid[rowIndex][columnIndex]
    operator fun set(rowIndex: Int, columnIndex: Int, value: GridCell) {
        grid[rowIndex][columnIndex] = value
    }

    fun print() {
        Timber.d("Size: ${size.count}")
        for (ints in grid) {
            Timber.d(ints.contentToString())
        }
    }

    fun copy(): Grid {
        return Grid(size, grid.map { it.copyOf() }.toTypedArray(), candidates.map { it.copyOf() }.toTypedArray())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grid

        if (size != other.size) return false
        if (!grid.contentDeepEquals(other.grid)) return false
        if (!candidates.contentDeepEquals(other.candidates)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size.hashCode()
        result = 31 * result + grid.contentDeepHashCode()
        result = 31 * result + candidates.contentDeepHashCode()
        return result
    }
}
