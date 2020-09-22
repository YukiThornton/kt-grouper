package project.grouper.domain

data class PairingRequests(override val list: List<PairingRequest>) : Evaluator, FCC<PairingRequest> {

    private val pairingRequestsForEachPair: Map<MemberPair, List<PairingRequest>> by lazy {
        list.groupBy(
            { request -> request.toMemberPair() },
            { request -> request })
    }

    override fun evaluate(group: Group): Score {
        val matchedPairingRequests = matchedPairingRequests(group)
        return if (matchedPairingRequests.isEmpty()) {
            Score(0)
        } else {
            matchedPairingRequests.asSequence()
                .map { it.baseScore() }
                .reduce{ acc, baseScore -> acc + baseScore }
        }
    }

    private fun matchedPairingRequests(group: Group): List<PairingRequest> {
        return group.allPossiblePairings()
            .filter { pairingRequestsForEachPair.containsKey(it) }
            .flatMap { pairingRequestsForEachPair[it]!! }
    }
}