package com.car.rentservice.repositories;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.car.rentservice.modal.Car;
import com.car.rentservice.modal.PickUpPlace;
import com.car.rentservice.modal.User;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
	Optional<Car> findBySerialNumber(String serialNumber);

	@Query(value = "SELECT * FROM Car c WHERE c.user_id = :userId", nativeQuery = true)
	List<Car> findByUserId(@Param("userId") Long userId);

	Car findByUserIdAndSerialNumber(Long id, String serialNumber);

	default List<Car> searchByFilters(String make, String modal, String year, String engine, String fuel, String gear,
			String wheelsDrive) {
		return findAll(new Specification<Car>() {
			@Override
			public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (make != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("make"), make)));
				}
				if (modal != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("modal"), modal)));
				}
				if (year != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("year"), year)));
				}
				if (engine != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("engine"), engine)));
				}
				if (fuel != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("fuel"), fuel)));
				}
				if (gear != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("gear"), gear)));
				}
				if (wheelsDrive != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("wheelsDrive"), wheelsDrive)));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}

	default List<Car> searchCar(String city, LocalDateTime startDateTime, LocalDateTime endDateTime, int minAmount,
			int maxAmount) {
		return findAll(new Specification<Car>() {
			@Override
			public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				Join<Car, PickUpPlace> pickUpPlace = root.join("pickUpPlace");
				Join<Car, User> user = root.join("user").join("reservations");
				List<Predicate> predicates = new ArrayList<>();
				if (city != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(pickUpPlace.get("placeName"), city)));
				}
				if (startDateTime != null) {
					predicates
							.add(criteriaBuilder.and(criteriaBuilder.equal(user.get("startDateTime"), startDateTime)));
				}
				if (endDateTime != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(user.get("endDateTime"), endDateTime)));
				}
				if (minAmount > 0) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(user.get("amount"), minAmount)));
				}
				if (maxAmount > 0) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(user.get("amount"), maxAmount)));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}

	default Page<Car> searchCar(Map<String, String> data, Pageable pageable) {
		return findAll(new Specification<Car>() {
			@Override
			public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				Join<Car, PickUpPlace> pickUpPlace = root.join("pickUpPlace");
				Join<Car, User> user = root.join("user").join("reservations");
				List<Predicate> predicates = new ArrayList<>();
				if (data != null && !data.isEmpty()) {
					for (Map.Entry<String, String> filterData : data.entrySet()) {
						if (filterData.getKey() != null) {
							if (filterData.getKey().equals("placeName")) {
								predicates.add(criteriaBuilder.and(
										criteriaBuilder.equal(pickUpPlace.get("placeName"), filterData.getValue())));
							}
							if (filterData.getKey().equals("startDateTime")) {
								try {
									predicates.add(criteriaBuilder.and(criteriaBuilder.equal(user.get("startDateTime"),
											converDate(filterData.getValue()))));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if (filterData.getKey().equals("endDateTime")) {
								try {
									predicates.add(criteriaBuilder.and(criteriaBuilder.equal(user.get("endDateTime"),
											converDate(filterData.getValue()))));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if (filterData.getKey().equals("minAmount")) {
								predicates.add(criteriaBuilder.and(criteriaBuilder
										.greaterThanOrEqualTo(user.get("amount"), filterData.getValue())));
							}
							if (filterData.getKey().equals("maxAmount")) {
								predicates.add(criteriaBuilder.and(
										criteriaBuilder.lessThanOrEqualTo(user.get("amount"), filterData.getValue())));
							}
							if (filterData.getKey().equals("latitude")) {
								predicates.add(criteriaBuilder.and(
										criteriaBuilder.equal(pickUpPlace.get("latitude"), filterData.getValue())));
							}
							if (filterData.getKey().equals("longitude")) {
								predicates.add(criteriaBuilder.and(
										criteriaBuilder.equal(pickUpPlace.get("longitude"), filterData.getValue())));
							}
							if (filterData.getKey().equals("make")) {
								predicates.add(criteriaBuilder
										.and(criteriaBuilder.equal(root.get("make"), filterData.getValue())));
							}
							if (filterData.getKey().equals("modal")) {
								predicates.add(criteriaBuilder
										.and(criteriaBuilder.equal(root.get("modal"), filterData.getValue())));
							}
							if (filterData.getKey().equals("year")) {
								predicates.add(criteriaBuilder
										.and(criteriaBuilder.equal(root.get("year"), filterData.getValue())));
							}
							if (filterData.getKey().equals("engine")) {
								predicates.add(criteriaBuilder
										.and(criteriaBuilder.equal(root.get("engine"), filterData.getValue())));
							}
							if (filterData.getKey().equals("fuel")) {
								predicates.add(criteriaBuilder
										.and(criteriaBuilder.equal(root.get("fuel"), filterData.getValue())));
							}
						}
					}
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}, pageable);
	}

	static final String HAVERSINE_PART = "(6371 * acos(cos(radians(:latitude)) * cos(radians(m.latitude)) * cos(radians(m.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(m.latitude))))";

	@Query("SELECT c FROM Car c INNER JOIN PickUpPlace m ON c.id = m.id WHERE " + HAVERSINE_PART
			+ " < :distance ORDER BY " + HAVERSINE_PART + " DESC")
	List<Car> searchByCoordinates(Double latitude, Double longitude, Double distance, Pageable pageable);

	default LocalDate converDate(String providedStringDate) throws ParseException {

		DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ld = LocalDate.parse(providedStringDate, DATEFORMATTER);
		System.out.println(ld);
		return ld;
	}
}
