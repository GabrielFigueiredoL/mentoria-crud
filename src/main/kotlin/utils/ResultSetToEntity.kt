package org.gabrielfigueiredol.utils

import org.gabrielfigueiredol.domain.Car
import org.gabrielfigueiredol.enums.Brand
import org.gabrielfigueiredol.enums.TransmissionType
import java.sql.ResultSet

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
}