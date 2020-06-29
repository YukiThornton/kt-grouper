package project.grouper.domain

interface Evaluator {
    fun score(lot: GroupLot): ScoredGroupLot
}
