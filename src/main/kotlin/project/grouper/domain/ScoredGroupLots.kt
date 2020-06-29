package project.grouper.domain

data class ScoredGroupLots(override val list: List<ScoredGroupLot>): FCC<ScoredGroupLot> {
    fun pickHighest(): ScoredGroupLot {
        TODO()
    }
}