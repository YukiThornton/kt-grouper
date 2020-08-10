package project.grouper.domain

data class PairedMembers(val first: Member, val second: Member) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return isSame(other as PairedMembers)
    }

    private fun isSame(other: PairedMembers): Boolean {
        if (first == other.first && second == other.second) return true
        if (first == other.second && second == other.first) return true
        return false
    }

    override fun hashCode(): Int {
        return 31 * first.hashCode() + 31 * second.hashCode()
    }
}