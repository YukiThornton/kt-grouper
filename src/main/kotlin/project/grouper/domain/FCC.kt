package project.grouper.domain

interface FCC<T> {
    val list: List<T>
    fun size() = list.size
}