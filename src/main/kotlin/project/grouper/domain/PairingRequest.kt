package project.grouper.domain

data class PairingRequest(val requester: Member, val requestee: Member, val type: PairingRequestType) {
    fun toMemberPair(): MemberPair {
        return MemberPair(requester, requestee)
    }

    fun baseScore(): Score {
        return type.baseScore
    }
}