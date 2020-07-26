package project.grouper.port

import project.grouper.domain.Lot

interface LotPort {
    fun saveLot(lot: Lot)
}