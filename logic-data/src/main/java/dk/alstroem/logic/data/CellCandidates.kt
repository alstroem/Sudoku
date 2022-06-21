package dk.alstroem.logic.data

data class CellCandidates(
    val userCandidates: Map<Int, Boolean> = emptyMap(),
    val autoCandidates: Map<Int, Boolean> = emptyMap()
)
