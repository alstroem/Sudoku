package dk.alstroem.logic.data

sealed class Level(val providedDigits: Int) {
    object Easy : Level(30)
    object Medium : Level(25)
    object Hard : Level(21)
}
