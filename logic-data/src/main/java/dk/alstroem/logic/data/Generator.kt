package dk.alstroem.logic.data

class Generator {

    private lateinit var grid: Grid

    fun generate(level: Level): Grid {
        grid = Grid()
        fillGrid()
        removeDigits(level)
        return grid
    }

    private fun fillGrid() {
        fillDiagonalNonets()
        fillRemainingNonets()
    }

    private fun fillDiagonalNonets() {
        for (index in grid.size.indexRange step grid.size.nonetSize) {
            fillNonet(index, index)
        }
    }

    private fun fillNonet(rowStart: Int, columnStart: Int) {
        var generatedDigit: Int
        for (row in grid.size.nonetRange) {
            for (column in grid.size.nonetRange) {
                do {
                    generatedDigit = getRandomValue(grid.size.minValue, grid.size.maxValue)
                } while (!grid.isUnusedInNonet(rowStart, columnStart, generatedDigit))

                val rowIndex = rowStart + row
                val columnIndex = columnStart + column

                grid[rowIndex, columnIndex] = GridCell(rowIndex, columnIndex, generatedDigit, true)
            }
        }
    }

    private fun fillRemainingNonets(
        rowStart: Int = 0,
        columnStart: Int = grid.size.nonetSize
    ): Boolean {
        var row = rowStart
        var column = columnStart

        if (row < grid.size.count - 1 && column >= grid.size.count) {
            row += 1
            column = 0
        }

        if (row >= grid.size.count && column >= grid.size.count) {
            return true
        }

        if (row < grid.size.nonetSize) {
            if (column < grid.size.nonetSize) {
                column = grid.size.nonetSize
            }
        } else if (row < grid.size.count - grid.size.nonetSize) {
            if (column == (row / grid.size.nonetSize) * grid.size.nonetSize) {
                column += grid.size.nonetSize
            }
        } else {
            if (column == grid.size.count - grid.size.nonetSize) {
                row += 1
                column = 0
                if (row >= grid.size.count) {
                    return true
                }
            }
        }

        for (digit in grid.size.valueRange) {
            if (grid.isUnused(row, column, digit)) {
                grid[row, column] = GridCell(row, column, digit, true)
                if (fillRemainingNonets(row, column + 1)) {
                    return true
                }
                grid[row, column] = GridCell(row, column, 0, false)
            }
        }

        return false
    }

    private fun removeDigits(level: Level) {
        var digitsToRemove = grid.size.count * grid.size.count - level.providedDigits

        while (digitsToRemove > 0) {
            val randomRow = getRandomValue(grid.size.minIndex, grid.size.maxIndex)
            val randomColumn = getRandomValue(grid.size.minIndex, grid.size.maxIndex)

            if (grid[randomRow, randomColumn].value != 0) {
                val digitToRemove = grid[randomRow, randomColumn]
                grid[randomRow, randomColumn] = GridCell(randomRow, randomColumn, 0, false)
                if (!Solver.isSolvable(grid)) {
                    grid[randomRow, randomColumn] = digitToRemove
                } else {
                    digitsToRemove --
                }
            }
        }
    }

    private fun getRandomValue(minValue: Int, maxValue: Int) = (minValue..maxValue).shuffled().first()

}
