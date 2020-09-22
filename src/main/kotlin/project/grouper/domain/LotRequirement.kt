package project.grouper.domain

import kotlin.math.ceil

data class LotRequirement private constructor(val groupCount: GroupCount, val members: Members) {
    companion object {
        fun of(groupCount: GroupCount, members: Members): LotRequirement {
            if (groupCount.count >= members.size()) throw InvalidLotRequirement("group count is same or bigger than member size")
            if (groupCount.count == 1) throw InvalidLotRequirement("group count 1 is meaningless for grouping")
            return LotRequirement(groupCount, members)
        }
    }

    fun generateRandomLots(generationCount: Int): Lots {
        return (1..generationCount).map { generateRandomLot() }.let(::Lots)
    }

    fun generateRandomLot(): Lot {
        val shuffled = members.shuffled()
        return groupDividingBoundaryIndexPairs().map{ (start, end) -> Group(Members(shuffled.subList(start, end)))}.let(::Lot)
    }

    private fun groupDividingBoundaryIndexPairs(): List<Pair<Int, Int>> {
        var indexPairs = mutableListOf<Pair<Int, Int>>()
        groupSizes().forEachIndexed { index, groupSize ->
            if (index == 0) {
                indexPairs.add(0 to groupSize)
            } else {
                indexPairs.add(indexPairs.last().second to indexPairs.last().second + groupSize)
            }
        }
        return indexPairs.toList()
    }

    private fun groupSizes(): List<Int> {
        val baseGroupSize = divideAndCeil(members.size(), groupCount.count)
        return if (members.size() % groupCount.count == 0) {
            List(groupCount.count) { baseGroupSize }
        } else {
            val biggerGroupCount = members.size() % groupCount.count
            List(biggerGroupCount) {baseGroupSize} + List(groupCount.count - biggerGroupCount) {baseGroupSize - 1}
        }
    }

    private fun divideAndCeil(one: Int, other: Int): Int {
        return ceil(one / other.toDouble()).toInt()
    }
}
