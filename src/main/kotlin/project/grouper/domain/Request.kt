package project.grouper.domain

data class Request(val requestedPairs: List<RequestedPair>) : Evaluator {

    private val groupedRequestedPairs: Map<PairedMembers, List<RequestType>> by lazy {
        requestedPairs.groupBy(
            { pair -> PairedMembers(pair.requester, pair.requestee) },
            { pair -> pair.requestType })
    }

    override fun evaluate(group: Group): Score {
        val matchedRequests = group.allPossiblePairings()
            .filter { groupedRequestedPairs.containsKey(it) }
            .flatMap { groupedRequestedPairs[it]!! }
        return if (matchedRequests.isEmpty()) {
            Score(0)
        } else {
            matchedRequests.map { it.score }.sum().let(::Score)
        }
    }
}