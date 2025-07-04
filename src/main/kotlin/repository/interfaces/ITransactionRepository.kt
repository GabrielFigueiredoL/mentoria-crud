package org.gabrielfigueiredol.repository.interfaces

import org.gabrielfigueiredol.domain.Transaction

interface ITransactionRepository {
    fun saveTransaction(transaction: Transaction): Int
    fun getTransactionById(id: Int): Transaction?
    fun getAllTransactions(): List<Transaction>
    fun deleteTransactionById(id: Int): Boolean
}