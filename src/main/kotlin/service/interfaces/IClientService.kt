package org.gabrielfigueiredol.service.interfaces

import org.gabrielfigueiredol.domain.Client

interface IClientService {
    fun registerClient(client: Client): Int;
    fun getClientById(id: Int): Client?;
    fun getAllClients(): List<Client>;
    fun updateClient(id: Int, updatedClient: Client): Boolean;
    fun deleteClientById(id: Int): Boolean;
}