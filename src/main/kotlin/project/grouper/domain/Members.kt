package project.grouper.domain

import com.marcinmoskala.math.combinations

data class Members(override val list: List<Member>): FCC<Member> {
    private val allPossiblePairings: Set<MemberPair> by lazy {
        toSet().combinations(2).map{ MemberPair(it.first(), it.last()) }.toSet()
    }

    fun shuffled(): Members {
        return list.shuffled().let(::Members)
    }

    fun allPossiblePairings(): Set<MemberPair> {
        return allPossiblePairings
    }
}