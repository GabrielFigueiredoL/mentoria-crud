package org.gabrielfigueiredol.service.interfaces

import org.gabrielfigueiredol.domain.Car

interface ICarService {
    fun registerCar(car: Car): Int
    fun getCarById(id: Int): Car?
    fun getAllCars(): List<Car>
    fun updateCar(id: Int, updatedCar: Car): Boolean
    fun deleteCarById(id: Int): Boolean
}