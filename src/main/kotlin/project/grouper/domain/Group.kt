package project.grouper.domain

data class Group(val members: Members) {
    fun allPossiblePairings(): Set<MemberPair> {
        return members.allPossiblePairings()
    }
}