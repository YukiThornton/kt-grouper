package project.grouper.port

import project.grouper.domain.History

interface HistoryPort {
    fun getHistory(): History
}