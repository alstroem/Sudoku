package dk.alstroem.logic.data

sealed class Level(val providedDigits: Int) {
    object Easy : Level(25)
    object Medium : Level(20)
    object Hard : Level(17)
}
