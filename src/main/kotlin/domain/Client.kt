package org.gabrielfigueiredol.domain

import kotlinx.serialization.Serializable

@Serializable
data class Client (
    val id: Int? = null,
    val name: String,
    val document: String,
    val phone: String,
    val street: String,
    val complement: String,
    val neighborhood: String,
    val city: String,
    val cep: String,
)