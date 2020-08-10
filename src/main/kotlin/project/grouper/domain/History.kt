package project.grouper.domain

import java.util.*

data class History(private val groups: List<Group>): Evaluator {

    companion object {
        private const val HISTORICAL_DUPLICATE_SCORE = -70
    }

    private val allPairCombinations : List<PairedMembers> by lazy {
        groups.allPairCombinationsWithDuplicates()
    }

    override fun score(group: Group): Score {
        val pairs = group.allSortedPairCombinations()
        val pairedCountTotal = pairs.map { countPairedTimes(it) }.sum()
        return Score(pairedCountTotal * HISTORICAL_DUPLICATE_SCORE)
    }

    fun countPairedTimes(targetPair: PairedMembers): Int {
        return Collections.frequency(allPairCombinations, targetPair)
    }

    private fun List<Group>.allPairCombinationsWithDuplicates(): List<PairedMembers> {
        return map { it.allSortedPairCombinations().toList() }.flatten()
    }


}