package com.constantine.domain.repository

interface ConfigRepository {
    fun initialize()

    fun getInstallTimestamp(): Long
}
