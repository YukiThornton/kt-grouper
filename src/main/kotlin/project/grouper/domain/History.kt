package project.grouper.domain

import java.util.*

data class History(private val groups: List<Group>) {

    companion object {
        private const val HISTORICAL_DUPLICATE_SCORE = -70
    }

    private val allPairCombinations : List<PairedMembers> by lazy {
        groups.allPairCombinationsWithDuplicates()
    }

    fun score(lots: Lots): ScoredLots {
        return lots.map(::score).let(::ScoredLots)
    }

    fun score(lot: Lot): ScoredLot {
        return lot.map(::score).let(::ScoredLot)
    }

    fun score(group: Group): ScoredGroup {
        val pairs = group.allPairCombinations()
        val pairedCountTotal = pairs.map { countPairedTimes(it) }.sum()
        return ScoredGroup(group, Score(pairedCountTotal * HISTORICAL_DUPLICATE_SCORE))
    }

    fun countPairedTimes(targetPair: PairedMembers): Int {
        return Collections.frequency(allPairCombinations, targetPair)
    }

    private fun List<Group>.allPairCombinationsWithDuplicates(): List<PairedMembers> {
        return map { it.allPairCombinations().toList() }.flatten()
    }


}