package project.grouper.domain

data class Group(val members: Members) {
    fun allPossiblePairings(): Set<PairedMembers> {
        return members.allPossiblePairings()
    }
}