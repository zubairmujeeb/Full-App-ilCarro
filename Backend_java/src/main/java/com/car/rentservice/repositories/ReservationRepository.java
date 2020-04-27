package com.car.rentservice.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.car.rentservice.modal.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findBySerialNumber(String serialNumber);

	@Query(value = "select new map( r.serialNumber as serialNumber ,count(r.serialNumber) as numberOfCar) FROM Reservation r  group by r.serialNumber order by numberOfCar Desc")
	List<Object> countBySerialNumber(Pageable paging);

	@Query(value = "SELECT RESERVATION_SEQ.next_val FROM reservation_seq", nativeQuery = true)
	long findNextOrderNumberSequence();

	Reservation findByOrderNumber(String orderNumber);
}