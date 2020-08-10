package project.grouper.domain

data class Group(val members: Members) {
    fun allSortedPairCombinations(): Set<PairedMembers> {
        return members.allSortedPairCombinations()
    }
}