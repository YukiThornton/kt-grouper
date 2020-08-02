package project.grouper.domain

data class Score(val value: Int): Comparable<Score> {
    operator fun plus(other: Score): Score {
        return Score(value + other.value)
    }

    override fun compareTo(other: Score): Int {
        return value.compareTo(other.value)
    }
}