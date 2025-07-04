package org.gabrielfigueiredol.utils

import org.gabrielfigueiredol.domain.Car
import org.gabrielfigueiredol.domain.Client
import org.gabrielfigueiredol.domain.Transaction
import org.gabrielfigueiredol.enums.Brand
import org.gabrielfigueiredol.enums.TransactionType
import org.gabrielfigueiredol.enums.TransmissionType
import java.sql.ResultSet
import java.time.LocalDateTime

object ResultSetToEntity {
    fun toCar(resultSet: ResultSet): Car {
        return Car(
            id = resultSet.getInt("id"),
            model = resultSet.getString("model"),
            modelYear = resultSet.getInt("model_year"),
            manufactureYear = resultSet.getInt("manufacture_year"),
            km = resultSet.getInt("km"),
            priceInCents = resultSet.getInt("price_in_cents"),
            color = resultSet.getString("color"),
            chassis = resultSet.getString("chassis"),
            brand = Brand.valueOf(resultSet.getString("brand").uppercase()),
            transmissionType = TransmissionType.valueOf(resultSet.getString("transmission_type").uppercase())
        )
    }

    fun toClient(resultSet: ResultSet): Client {
        return Client(
            id = resultSet.getInt("id"),
            name = resultSet.getString("name"),
            document = resultSet.getString("document"),
            phone = resultSet.getString("phone"),
            street = resultSet.getString("street"),
            complement = resultSet.getString("complement"),
            neighborhood = resultSet.getString("neighborhood"),
            city = resultSet.getString("city"),
            cep = resultSet.getString("cep"),
        )
    }

    fun toTransaction(resultSet: ResultSet): Transaction {
        return Transaction(
            id = resultSet.getInt("id"),
            transactionType = TransactionType.valueOf(resultSet.getString("transaction_type").uppercase()),
            carId = resultSet.getInt("car_id"),
            clientId = resultSet.getInt("client_id"),
            priceInCents = resultSet.getInt("price_in_cents"),
            dateTime = DateConverter.timeStampToKotlinLocalDateTime(resultSet.getTimestamp("date_time")),
        )
    }
}