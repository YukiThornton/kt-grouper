package project.grouper.domain

import com.marcinmoskala.math.combinations

data class Members(override val list: List<Member>): FCC<Member> {
    fun shuffled(): Members {
        return list.shuffled().let(::Members)
    }

    fun splitInto(maxGroupSize: MaxGroupSize): List<Members> {
        return list.chunked(maxGroupSize.value).map(::Members)
    }

    fun allPairCombinations(): Set<PairedMembers> {
        return toSet().combinations(2).map{ it.toSortedPair() }.toSet()
    }

    private fun Set<Member>.toSortedPair(): PairedMembers {
        this.toList().sorted().let {
            return Pair(it.first(), it.last())
        }
    }
}