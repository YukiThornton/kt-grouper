package project.grouper.domain

data class ScoredLots (override val list: List<ScoredLot>): FCC<ScoredLot> {
    fun highest(): ScoredLot {
        return maxBy { it.score }!!
    }
}