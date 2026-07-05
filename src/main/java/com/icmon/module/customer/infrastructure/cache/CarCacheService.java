package com.icmon.module.customer.infrastructure.cache;

import com.icmon.module.customer.domain.MCar;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarCacheService {

    @Cacheable(value = "cars", key = "#carId")
    public MCar getCar(UUID carId) {
        return null;
    }

    @Cacheable(value = "car_plate", key = "#licensePlate")
    public MCar getCarByPlate(String licensePlate) {
        return null;
    }

    @CachePut(value = "cars", key = "#car.id")
    public MCar saveCar(MCar car) {
        return car;
    }

    @CacheEvict(value = {"cars", "car_plate"}, key = "#carId")
    public void evictCar(UUID carId) {
    }
}
