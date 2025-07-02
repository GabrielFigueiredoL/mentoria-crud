package org.gabrielfigueiredol.service

import org.gabrielfigueiredol.domain.Car
import org.gabrielfigueiredol.repository.postgres.CarRepository
import org.gabrielfigueiredol.service.interfaces.ICarService

class CarService : ICarService {
    private val carRepository = CarRepository()

    override fun registerCar(car: Car): Int {
        return carRepository.saveCar(car)
    }

    override fun getCarById(id: Int): Car? {
        return carRepository.getCarById(id)
    }

    override fun getAllCars(): List<Car> {
        return carRepository.getAllCars()
    }

    override fun updateCar(id: Int, updatedCar: Car): Boolean {
        return carRepository.updateCar(id, updatedCar)
    }

    override fun deleteCarById(id: Int): Boolean {
        return carRepository.deleteCarById(id)
    }
}