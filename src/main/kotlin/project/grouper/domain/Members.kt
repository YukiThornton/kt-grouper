package project.grouper.domain

data class Members(override val list: List<Member>): FCC<Member> {
    fun shuffled(): Members {
        return list.shuffled().let(::Members)
    }

    fun splitInto(maxGroupSize: MaxGroupSize): List<Members> {
        return list.chunked(maxGroupSize.value).map(::Members)
    }
}