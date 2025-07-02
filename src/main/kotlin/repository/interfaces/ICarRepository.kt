package org.gabrielfigueiredol.repository.interfaces

import org.gabrielfigueiredol.domain.Car

interface ICarRepository {
    fun saveCar(car: Car): Int
    fun getCarById(id: Int): Car?
    fun getAllCars(): List<Car>
    fun deleteCarById(id: Int): Boolean
    fun updateCar(id: Int, updatedCar: Car): Boolean
}