package project.grouper.domain

data class Group(val members: Members) {
    fun uniquePairCombinations(): Set<PairedMembers> {
        return members.uniquePairCombinations()
    }
}