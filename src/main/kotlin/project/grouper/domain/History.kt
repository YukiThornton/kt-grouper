package project.grouper.domain

data class History(private val groups: List<Group>): Evaluator {

    companion object {
        private const val HISTORICAL_DUPLICATE_SCORE = -70
    }

    private val pairedTimesCountMap : Map<PairedMembers, Int> by lazy {
        groups.allPairCombinationsWithDuplicates().groupingBy { it }.eachCount()
    }

    override fun score(group: Group): Score {
        val pairs = group.uniquePairCombinations()
        val pairedCountTotal = pairs.map { countPairedTimes(it) }.sum()
        return Score(pairedCountTotal * HISTORICAL_DUPLICATE_SCORE)
    }

    fun countPairedTimes(targetPair: PairedMembers): Int {
        return pairedTimesCountMap.getOrElse(targetPair, { 0 })
    }

    private fun List<Group>.allPairCombinationsWithDuplicates(): List<PairedMembers> {
        return map { it.uniquePairCombinations().toList() }.flatten()
    }


}