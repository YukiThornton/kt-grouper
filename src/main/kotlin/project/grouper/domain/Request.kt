package project.grouper.domain

data class Request(override val list: List<RequestedPair>): FCC<RequestedPair>, Evaluator {
    private val pairMap: Map<PairedMembers, List<RequestType>> by lazy {
        list.groupBy(
            {pair -> listOf(pair.requester, pair.requestee).sorted().let { it.first() to it.last() }},
            {pair -> pair.requestType})
    }
    override fun score(group: Group): Score {
        val matchedRequestTypes = group.allSortedPairCombinations()
            .filter { pairMap.containsKey(it) }
            .flatMap { pairMap[it]!! }
        return if (matchedRequestTypes.isEmpty()) {
                Score(0)
            } else {
               matchedRequestTypes.map { it.score }
                .reduce { acc, score -> acc + score }
                .let(::Score)
        }
    }
}