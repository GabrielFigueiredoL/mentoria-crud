package org.gabrielfigueiredol.service

import org.gabrielfigueiredol.domain.Transaction
import org.gabrielfigueiredol.repository.postgres.CarRepository
import org.gabrielfigueiredol.repository.postgres.ClientRepository
import org.gabrielfigueiredol.repository.postgres.TransactionRepository
import org.gabrielfigueiredol.service.interfaces.ITransactionService
import java.lang.IllegalArgumentException

class TransactionService : ITransactionService {
    val carRepository = CarRepository()
    val clientRepository = ClientRepository()
    val transactionRepository = TransactionRepository()

    override fun registerTransaction(transaction: Transaction): Int {
        try {
            if (carRepository.getCarById(transaction.carId) == null) {
                throw IllegalArgumentException("Carro inválido")
            }

            if (clientRepository.getClientById(transaction.clientId) == null) {
                throw IllegalArgumentException("Cliente inválido")
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

        return transactionRepository.saveTransaction(transaction)
    }

    override fun getTransactionById(id: Int): Transaction? {
        return transactionRepository.getTransactionById(id)
    }

    override fun getAllTransactions(): List<Transaction> {
        return transactionRepository.getAllTransactions()
    }

    override fun deleteTransactionById(id: Int): Boolean {
        return transactionRepository.deleteTransactionById(id)
    }

}