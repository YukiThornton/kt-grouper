package project.grouper.domain

enum class PairingRequestType(val baseScore: Score) {
    SAME_GROUP(Score(60)), BLOCK(Score(-50))
}