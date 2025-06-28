package org.gabrielfigueiredol.service.interfaces

import org.gabrielfigueiredol.domain.Car

interface ICarService {
    fun registerCar(car: Car);
    fun getCar(id: Int);
    fun getAllCars();
    fun updateCar(id: Int, updatedCar: Car);
    fun deleteCar(id: Int);
}