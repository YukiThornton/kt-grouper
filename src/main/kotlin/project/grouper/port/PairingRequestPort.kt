package project.grouper.port

import project.grouper.domain.PairingRequests

interface PairingRequestPort {
    fun getPairingRequests(): PairingRequests
}