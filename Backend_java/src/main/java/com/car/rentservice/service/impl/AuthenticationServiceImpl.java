package com.car.rentservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.car.rentservice.dto.BookedCarsOutputDTO;
import com.car.rentservice.dto.BookedPeriodDTO;
import com.car.rentservice.dto.BookedPeriodsDTO;
import com.car.rentservice.dto.BookedPersonOutputDTO;
import com.car.rentservice.dto.CarOutputDTO;
import com.car.rentservice.dto.CommentsOutputDTO;
import com.car.rentservice.dto.PickUpPlaceDTO;
import com.car.rentservice.dto.RegisterUserInputDTO;
import com.car.rentservice.dto.UserSuccessResponseDTO;
import com.car.rentservice.modal.Car;
import com.car.rentservice.modal.Comments;
import com.car.rentservice.modal.PickUpPlace;
import com.car.rentservice.modal.Reservation;
import com.car.rentservice.modal.User;
import com.car.rentservice.repositories.UserRepository;
import com.car.rentservice.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bCryptEncoder;

	@Override
	@Transactional
	public UserSuccessResponseDTO register(RegisterUserInputDTO registerUserInputDTO) {
		if (userRepository.findByEmail(registerUserInputDTO.getEmail()) != null) {
			System.out.println("Error will come later.");
			return null;
		}

		User newUser = new User();
		newUser.setEmail(registerUserInputDTO.getEmail());
		newUser.setFirstName(registerUserInputDTO.getFirstName());
		newUser.setSecondName(registerUserInputDTO.getSecondName());
		newUser.setPhotoUrl(registerUserInputDTO.getPhotoUrl());
		newUser.setPhone(registerUserInputDTO.getPhone());
		newUser.setPassword(bCryptEncoder.encode(registerUserInputDTO.getPassword()));
		newUser.setComments(new ArrayList<Comments>());
		newUser.setCars(new ArrayList<Car>());
		newUser.setReservations(new ArrayList<Reservation>());

		userRepository.save(newUser);
		return toUserSuccessResponseDto(newUser);
	}

	private UserSuccessResponseDTO toUserSuccessResponseDto(User newUser) {
		return new UserSuccessResponseDTO(newUser.getFirstName(), newUser.getSecondName(), newUser.getCreatedAt(),
				toCommentsListOutputDto(newUser.getComments()), toCarListOutputDTO(newUser.getCars()),
				toBookedListCarsOutputDto(newUser.getReservations()),
				toBookedListCarsOutputDto(newUser.getReservations()));
	}

	private List<BookedCarsOutputDTO> toBookedListCarsOutputDto(List<Reservation> reservations) {
		return reservations.stream().map(this::toBookedCarsOutputDto).collect(Collectors.toList());
	}

	private BookedCarsOutputDTO toBookedCarsOutputDto(Reservation reservation) {
		return new BookedCarsOutputDTO(reservation.getSerialNumber(),
				toBookedListPeriodDto(reservation.getUser().getReservations()));
	}

	private List<BookedPeriodDTO> toBookedListPeriodDto(List<Reservation> reservation) {
		return reservation.stream().map(this::toBookedPeriodDto).collect(Collectors.toList());
	}

	private BookedPeriodDTO toBookedPeriodDto(Reservation reservation) {
		return new BookedPeriodDTO(reservation.getOrderNumber(), reservation.getStartDateTime(),
				reservation.getEndDateTime(), reservation.isPaid(), reservation.getAmount(),
				reservation.getBookingDate());
	}

	private List<CarOutputDTO> toCarListOutputDTO(List<Car> cars) {
		return cars.stream().map(this::toCarOutputDto).collect(Collectors.toList());
	}

	private CarOutputDTO toCarOutputDto(Car car) {
		return new CarOutputDTO(car.getSerialNumber(), car.getMake(), car.getModal(), car.getYear(), car.getEngine(),
				car.getFuel(), car.getGear(), car.getWheelsDrive(), car.getDoors(), car.getSeats(),
				car.getFuelConsumption(), car.getFeatures(), car.getCarClass(), car.getPricePerDay(),
				car.getDistanceIncluded(), car.getAbout(), toPickUpPlaceDto(car.getPickUpPlace()), car.getImageUrl(),
				toBookedListPeriodsDto(car.getUser().getReservations()));
	}

	private List<BookedPeriodsDTO> toBookedListPeriodsDto(List<Reservation> reservations) {
		return reservations.stream().map(this::toBookedPeriodsDto).collect(Collectors.toList());
	}

	private BookedPeriodsDTO toBookedPeriodsDto(Reservation reservation) {
		return new BookedPeriodsDTO(reservation.getOrderNumber(), reservation.getStartDateTime(),
				reservation.getEndDateTime(), reservation.isPaid(), reservation.getAmount(),
				reservation.getBookingDate(), toBookedPersonDto(reservation.getUser()));
	}

	private BookedPersonOutputDTO toBookedPersonDto(User user) {
		return new BookedPersonOutputDTO(user.getEmail(), user.getFirstName(), user.getSecondName(), user.getPhone());
	}

	private PickUpPlaceDTO toPickUpPlaceDto(PickUpPlace pickUpPlace) {
		return new PickUpPlaceDTO(pickUpPlace.getPlaceId(), pickUpPlace.getPlaceName(), pickUpPlace.getLatitude(),
				pickUpPlace.getLongitude());
	}

	private List<CommentsOutputDTO> toCommentsListOutputDto(List<Comments> comments) {
		return comments.stream().map(this::toCommentsOutputDto).collect(Collectors.toList());
	}

	private CommentsOutputDTO toCommentsOutputDto(Comments comments) {
		return new CommentsOutputDTO(comments.getUser().getFirstName(), comments.getUser().getSecondName(),
				comments.getUser().getPhotoUrl(), comments.getCreatedAt(), comments.getPost());
	}
}
