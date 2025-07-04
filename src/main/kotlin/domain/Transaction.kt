package org.gabrielfigueiredol.domain

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.gabrielfigueiredol.enums.TransactionType

@Serializable
data class Transaction (
    val id: Int? = null,
    val transactionType: TransactionType,
    val carId: Int,
    val clientId: Int,
    val priceInCents: Int,
    val dateTime: LocalDateTime? = null
)