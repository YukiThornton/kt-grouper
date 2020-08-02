package project.grouper.domain

data class Group(val members: Members) {
    fun allPairCombinations(): Set<PairedMembers> {
        return members.allPairCombinations()
    }
}