package org.gabrielfigueiredol.service.interfaces

import org.gabrielfigueiredol.domain.Transaction

interface ITransactionService {
    fun registerTransaction(transaction : Transaction): Int;
    fun getTransactionById(id: Int): Transaction?;
    fun getAllTransactions(): List<Transaction>;
    fun deleteTransactionById(id: Int): Boolean;
}