package project.grouper.domain

import com.marcinmoskala.math.combinations

data class Members(override val list: List<Member>): FCC<Member> {
    private val uniqueCombinations: Set<PairedMembers> by lazy {
        toSet().combinations(2).map{ PairedMembers(it.first(), it.last()) }.toSet()
    }
    fun shuffled(): Members {
        return list.shuffled().let(::Members)
    }

    fun splitInto(maxGroupSize: MaxGroupSize): List<Members> {
        return list.chunked(maxGroupSize.value).map(::Members)
    }

    fun uniquePairCombinations(): Set<PairedMembers> {
        return uniqueCombinations
    }
}