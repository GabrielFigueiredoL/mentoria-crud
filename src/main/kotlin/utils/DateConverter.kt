package org.gabrielfigueiredol.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.sql.Timestamp

object DateConverter {
    fun timeStampToKotlinLocalDateTime(timestamp: Timestamp): LocalDateTime {
        val localDateTime = timestamp.toLocalDateTime()
        return localDateTime.toKotlinLocalDateTime()
    }
}