package project.grouper.domain

data class ScoredLot(override val list: List<ScoredGroup>): FCC<ScoredGroup> {
    val score: Score by lazy {
        map { it.score }.reduce {total, score -> total + score}
    }
}