package project.grouper.port

import project.grouper.domain.Lot
import project.grouper.domain.ScoredLot

interface LotPort {
    fun saveLot(lot: Lot)
    fun saveScoredLot(scoredLot: ScoredLot)
}