package project.grouper.domain

data class History(private val groups: List<Group>): Evaluator {

    companion object {
        private const val HISTORICAL_DUPLICATE_BASE_SCORE = -70
    }

    private val groupedTimesCountForEachPair : Map<MemberPair, Int> by lazy {
        val allGroupedPairsInHistory = groups.map { it.allPossiblePairings().toList() }.flatten()
        allGroupedPairsInHistory.groupingBy { it }.eachCount()
    }

    override fun evaluate(group: Group): Score {
        val pairsWithinGroup = group.allPossiblePairings()
        val groupedCountTotal = pairsWithinGroup.map { pair -> countGroupedTimesInHistory(pair) }.sum()
        return Score(groupedCountTotal * HISTORICAL_DUPLICATE_BASE_SCORE)
    }

    fun countGroupedTimesInHistory(targetPair: MemberPair): Int {
        return groupedTimesCountForEachPair.getOrElse(targetPair, { 0 })
    }


}