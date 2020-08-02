package project.grouper.domain

interface FCC<T> {
    val list: List<T>
    fun size() = list.size
    fun toSet() = list.toSet()
}

inline fun <T, R> FCC<T>.map(transform: (T) -> R): List<R> {
    return list.map(transform)
}

inline fun <T, R: Comparable<R>> FCC<T>.maxBy(selector: (T) -> R): T? {
    return list.maxBy(selector)
}