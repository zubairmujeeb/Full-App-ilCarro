package com.car.rentservice.repositories;

import com.car.rentservice.modal.PickUpPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickUpPlaceRepository extends JpaRepository<PickUpPlace, Long> {
}