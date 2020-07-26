package project.grouper.domain

import kotlin.math.ceil

data class Requirement(val groupCount: GroupCount, val members: Members) {
    fun maxGroupSize(): MaxGroupSize {
        return divideAndCeil(members.size(), groupCount.count).let(::MaxGroupSize)
    }

    private fun divideAndCeil(one: Int, other: Int): Int {
        return ceil(one / other.toDouble()).toInt()
    }
}
