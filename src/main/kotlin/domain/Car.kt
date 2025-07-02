package org.gabrielfigueiredol.domain

import kotlinx.serialization.Serializable
import org.gabrielfigueiredol.enums.Brand
import org.gabrielfigueiredol.enums.TransmissionType

@Serializable
data class Car (
    val id: Int? = null,
    val model: String,
    val modelYear: Int,
    val manufactureYear: Int,
    val km: Int,
    val priceInCents: Int,
    val color: String,
    val chassis: String,
    val brand: Brand,
    val transmissionType: TransmissionType,
)