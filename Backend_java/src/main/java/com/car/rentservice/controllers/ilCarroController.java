package com.car.rentservice.controllers;

import java.util.Map;

import com.car.rentservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.car.rentservice.service.ilCarroService;

@RestController
@CrossOrigin(origins = "*")
public class ilCarroController {
	@Autowired
	private ilCarroService ilCarroService;

	@GetMapping("/user")
	public ResponseModel getUser(Authentication authentication) {
		return ilCarroService.getUser(authentication.getName());
	}

	@DeleteMapping("/user")
	public ResponseModel deleteUser(Authentication authentication) {
		return ilCarroService.deleteUser(authentication.getName());
	}

	@PutMapping("/user")
	public ResponseModel updateUser(@RequestBody UpdateUserInputDTO userInputDTO, Authentication authentication) {
		return ilCarroService.updateUser(authentication.getName(), userInputDTO);
	}

	@PostMapping("/car")
	public ResponseModel addCar(Authentication authentication, @RequestBody CarInputDTO carInputDTO) {
		return ilCarroService.addCar(authentication.getName(), carInputDTO);
	}

	@PutMapping("/car/update/{serialNumber}")
	public ResponseModel updateCar(Authentication authentication, @PathVariable String serialNumber,
			@RequestBody CarInputDTO carInputDTO) {
		return ilCarroService.updateCar(authentication.getName(), serialNumber, carInputDTO);
	}

	@DeleteMapping("/car/delete/{serialNumber}")
	public ResponseModel deleteCar(Authentication authentication, @PathVariable String serialNumber) {
		return ilCarroService.deleteCar(authentication.getName(), serialNumber);
	}

	@GetMapping("/car/get/{serialNumber}")
	public ResponseModel getCarBySerialNumber(@PathVariable String serialNumber) {
		return ilCarroService.getCarBySerialNumber(serialNumber);
	}

	@GetMapping("/user/cars")
	public ResponseModel getOwnerCars(Authentication authentication) {
		return ilCarroService.getOwnerCars(authentication.getName());
	}

	@GetMapping("user/cars/car/{serialNumber}")
	public ResponseModel getOwnerCarBySerialNumber(Authentication authentication, @PathVariable String serialNumber) {
		return ilCarroService.getOwnerCarBySerialNumber(authentication.getName(), serialNumber);
	}

	@GetMapping("/user/cars/periods/{serialNumber}")
	public ResponseModel getOwnerBookedPeriodsBySerialNumber(Authentication authentication,
			@PathVariable String serialNumber) {
		return ilCarroService.getOwnerBookedPeriodsBySerialNumber(authentication.getName(), serialNumber);
	}

	@GetMapping("/comments")
	public ResponseModel getLastComments() {
		return ilCarroService.getLatestComments();
	}

	@PostMapping("/comment/{serialNumber}")
	public ResponseModel addComment(Authentication authentication, @PathVariable String serialNumber,
			@RequestBody CommentInputDTO commentInputDTO) {
		return ilCarroService.addComment(authentication.getName(), serialNumber, commentInputDTO);
	}

	@GetMapping("comment/{serialNumber}")
	public ResponseModel getLastThreeCommentsBySerialNumber(@PathVariable String serialNumber) {
		return ilCarroService.getThreeLastCommentsOfCarBySerialNumber(serialNumber);
	}

	@GetMapping("car/best")
	public ResponseModel getBestCars() {
		return ilCarroService.getBestBooked();
	}

	@GetMapping("/search/filters/{make}/{modal}/{year}/{engine}/{fuel}/{gear}/{wheelsDrive}")
	public ResponseModel searchByFilters(@PathVariable String make, @PathVariable String modal,
			@PathVariable String year, @PathVariable String engine, @PathVariable String fuel,
			@PathVariable String gear, @PathVariable String wheelsDrive) {
		return ilCarroService.searchCarByFilters(make, modal, year, engine, fuel, gear, wheelsDrive);
	}

	@PostMapping("/search")
	public ResponseModel searchCar(@RequestBody Map<String, String> data, Pageable pageable) {
		return ilCarroService.searchCar(data, pageable);
	}

	@PostMapping("/search/geo")
	public ResponseModel searchByCoordinates(@RequestBody Map<String, Double> data, Pageable pageable) {
		return ilCarroService.searchByCoordinates(data.get("latitude"), data.get("longitude"), data.get("radius"),
				pageable);
	}

	@PostMapping("/car/reservation/{serialNumber}")
	public ResponseModel makeReservation(@RequestBody ReservationInputDTO reservationInputDTO,
			@PathVariable String serialNumber, Authentication authentication) {
		return ilCarroService.makeReservation(serialNumber, reservationInputDTO, authentication.getName());
	}

	// Need to create a Webhook end point, which accepts the Json in the form of
	// string. Based on this perform the logic and update the payment status.
	@PostMapping("/reservation/confirm")
	public HttpStatus paymentConfirmation(@RequestBody PaymentConfirmInputDTO confirmInputDTO) {
		return ilCarroService.paymentConfirmation(confirmInputDTO);
	}
}