package dk.alstroem.logic.data

fun Grid.isUnused(row: Int, column: Int, value: Int): Boolean {
    val rowStart = size.getNonetStart(row)
    val columnStart = size.getNonetStart(column)
    return isUnusedInNonet(rowStart, columnStart, value)
            && isUnusedInRow(row, value)
            && isUnusedInColumn(column, value)
}

fun GridSize.getNonetStart(index: Int) = index - index % nonetSize

fun Grid.isUnusedInNonet(rowStart: Int, columnStart: Int, digit: Int): Boolean {
    for (row in size.nonetRange) {
        for (column in size.nonetRange) {
            if (this[rowStart + row, columnStart + column].value == digit) {
                return false
            }
        }
    }

    return true
}

private fun Grid.isUnusedInRow(row: Int, digit: Int): Boolean {
    for (column in size.indexRange) {
        if (this[row, column].value == digit) {
            return false
        }
    }
    return true
}

private fun Grid.isUnusedInColumn(column: Int, digit: Int): Boolean {
    for (row in size.indexRange) {
        if (this[row, column].value == digit) {
            return false
        }
    }
    return true
}