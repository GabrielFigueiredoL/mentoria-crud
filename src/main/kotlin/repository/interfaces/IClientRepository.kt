package org.gabrielfigueiredol.repository.interfaces

import org.gabrielfigueiredol.domain.Client

interface IClientRepository {
    fun saveClient(client: Client): Int
    fun getClientById(id: Int): Client?
    fun getAllClients(): List<Client>
    fun deleteClientById(id: Int): Boolean
    fun updateClient(id: Int, updatedClient: Client): Boolean
}