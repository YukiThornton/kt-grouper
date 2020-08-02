package project.grouper.domain

data class Member(val name: String): Comparable<Member> {
    override fun compareTo(other: Member): Int {
        return name.compareTo(other.name)
    }

}