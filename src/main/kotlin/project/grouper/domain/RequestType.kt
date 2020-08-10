package project.grouper.domain

enum class RequestType(val score: Int) {
    SAME_GROUP(60), BLOCK(-50)
}