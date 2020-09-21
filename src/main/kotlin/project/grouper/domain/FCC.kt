package project.grouper.domain

interface FCC<T> {
    val list: List<T>
    fun size() = list.size
    fun toSet() = list.toSet()
    fun subList(fromIndex: Int, toIndex: Int) = list.subList(fromIndex, toIndex)
}

inline fun <T, R> FCC<T>.map(transform: (T) -> R): List<R> {
    return list.map(transform)
}

inline fun <T, R: Comparable<R>> FCC<T>.maxBy(selector: (T) -> R): T? {
    return list.maxBy(selector)
}

inline fun <T, R: Comparable<R>> FCC<T>.minBy(selector: (T) -> R): T? {
    return list.minBy(selector)
}