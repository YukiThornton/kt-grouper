package project.grouper.domain

data class Lot(override val list: List<Group>): FCC<Group> {
    companion object {
        fun of(splitMembers: List<Members>): Lot {
            return splitMembers.map(::Group).let(::Lot)
        }
    }
}