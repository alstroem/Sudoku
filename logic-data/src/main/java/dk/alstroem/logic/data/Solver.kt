package dk.alstroem.logic.data

object Solver {

    private lateinit var grid: Grid

    fun isSolvable(grid: Grid): Boolean {
        Solver.grid = grid.copy()
        return solve()
    }

    private fun solve(): Boolean {
        for (row in grid.size.indexRange) {
            for (column in grid.size.indexRange) {
                if (grid[row, column].value == 0) {
                    val availableDigits = getAvailableDigits(row, column)
                    for (digit in availableDigits) {
                        grid[row, column] = GridCell(row, column, digit)
                        if (solve()) {
                            return true
                        }
                        grid[row, column] = GridCell(row, column, 0)
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun getAvailableDigits(row: Int, column: Int): List<Int> {
        val availableDigits = mutableListOf<Int>()
        availableDigits.addAll(grid.size.valueRange)

        availableDigits.truncateByDigitsUsedInRow(row)
        if (availableDigits.size > 1) {
            availableDigits.truncateByDigitsUsedInColumn(column)
        }

        if (availableDigits.size > 1) {
            availableDigits.truncateByDigitsUsedInBox(row, column)
        }

        return availableDigits.toList()
    }

    private fun MutableList<Int>.truncateByDigitsUsedInRow(row: Int) {
        for (column in grid.size.indexRange) {
            removeBlockIfUsed(row, column)
        }
    }

    private fun MutableList<Int>.truncateByDigitsUsedInColumn(column: Int) {
        for (row in grid.size.indexRange) {
            removeBlockIfUsed(row, column)
        }
    }

    private fun MutableList<Int>.truncateByDigitsUsedInBox(row: Int, column: Int) {
        val rowStart = getBoxStart(row)
        val rowEnd = getBoxEnd(rowStart)
        val columnStart = getBoxStart(column)
        val columnEnd = getBoxEnd(columnStart)

        for (rowIndex in rowStart until rowEnd) {
            for (columnIndex in columnStart until columnEnd) {
                removeBlockIfUsed(rowIndex, columnIndex)
            }
        }
    }

    private fun MutableList<Int>.removeBlockIfUsed(row: Int, column: Int) {
        val block = grid[row, column].value
        if (block != 0) {
            remove(block)
        }
    }

    private fun getBoxStart(index: Int) = index - (index % grid.size.nonetSize)
    private fun getBoxEnd(index: Int) = index + grid.size.nonetSize - 1

}
