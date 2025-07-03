package org.gabrielfigueiredol.service

import org.gabrielfigueiredol.domain.Client
import org.gabrielfigueiredol.repository.postgres.ClientRepository
import org.gabrielfigueiredol.service.interfaces.IClientService

class ClientService : IClientService {
    private val clientRepository = ClientRepository()

    override fun registerClient(client: Client): Int {
        if (client.document.length != 11 && client.document.length != 14) {
            throw IllegalArgumentException()
        }

        if (client.cep.length != 8) {
            throw IllegalArgumentException()
        }

        return clientRepository.saveClient(client)
    }

    override fun getClientById(id: Int): Client? {
        return clientRepository.getClientById(id)
    }

    override fun getAllClients(): List<Client> {
        return clientRepository.getAllClients()
    }

    override fun updateClient(id: Int, updatedClient: Client): Boolean {
        if (updatedClient.document.length != 11 && updatedClient.document.length != 14) {
            println("caiu if 1")
            throw IllegalArgumentException()
        }

        if (updatedClient.cep.length != 8) {
            println("caiu if 2")
            throw IllegalArgumentException()
        }

        return clientRepository.updateClient(id, updatedClient)
    }

    override fun deleteClientById(id: Int): Boolean {
        return clientRepository.deleteClientById(id)
    }
}