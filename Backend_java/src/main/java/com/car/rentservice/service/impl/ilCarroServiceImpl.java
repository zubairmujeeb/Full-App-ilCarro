package com.car.rentservice.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.car.rentservice.dto.BookedCarsOutputDTO;
import com.car.rentservice.dto.BookedPeriodDTO;
import com.car.rentservice.dto.BookedPeriodsDTO;
import com.car.rentservice.dto.BookedPersonOutputDTO;
import com.car.rentservice.dto.CarInputDTO;
import com.car.rentservice.dto.CarOutputDTO;
import com.car.rentservice.dto.CarOwnerOutputDTO;
import com.car.rentservice.dto.CommentInputDTO;
import com.car.rentservice.dto.CommentsOutputDTO;
import com.car.rentservice.dto.OwnerOutputDTO;
import com.car.rentservice.dto.PaymentConfirmInputDTO;
import com.car.rentservice.dto.PickUpPlaceDTO;
import com.car.rentservice.dto.ReservationInputDTO;
import com.car.rentservice.dto.ReservationOutputDTO;
import com.car.rentservice.dto.ResponseModel;
import com.car.rentservice.dto.UpdateUserInputDTO;
import com.car.rentservice.dto.UserSuccessResponseDTO;
import com.car.rentservice.modal.Car;
import com.car.rentservice.modal.Comments;
import com.car.rentservice.modal.PickUpPlace;
import com.car.rentservice.modal.Reservation;
import com.car.rentservice.modal.User;
import com.car.rentservice.repositories.CarRepository;
import com.car.rentservice.repositories.CommentRepository;
import com.car.rentservice.repositories.PickUpPlaceRepository;
import com.car.rentservice.repositories.ReservationRepository;
import com.car.rentservice.repositories.UserRepository;
import com.car.rentservice.service.ilCarroService;
import com.car.rentservice.utils.CommonConstants;

@Service
public class ilCarroServiceImpl implements ilCarroService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private PickUpPlaceRepository pickUpPlaceRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public ResponseModel getUser(String email) {
		try {
			User user = userRepository.findByEmail(email);
			if (user == null) {
				return generateResponse(HttpStatus.CONFLICT.toString(), "User does not exists", null);
			}
			return generateResponse(HttpStatus.OK.toString(), "User found",
					new ArrayList<>(Arrays.asList(toUserSuccessResponseDto(user))));

		} catch (Exception e) {
			return generateResponse(HttpStatus.UNAUTHORIZED.toString(), "Unexpected exception occurs:" + e.toString(),
					null);
		}
	}

	@Override
	@Transactional
	public ResponseModel updateUser(String email, UpdateUserInputDTO updateUserInputDTO) {
		try {
			User user = userRepository.findByEmail(email);
			if (user == null) {
				return generateResponse(HttpStatus.CONFLICT.toString(), "User does not exists", null);
			}
			user.setFirstName(updateUserInputDTO.getFirstName());
			user.setSecondName(updateUserInputDTO.getSecondName());
			user.setPhone(updateUserInputDTO.getPhone());
			user.setPhotoUrl(updateUserInputDTO.getPhotoUrl());

			userRepository.save(user);
			return generateResponse(HttpStatus.OK.toString(), "User is updated",
					new ArrayList<>(Arrays.asList(toUserSuccessResponseDto(user))));

		} catch (Exception e) {
			return generateResponse(HttpStatus.UNAUTHORIZED.toString(), "Unexpected exception occurs:" + e.toString(),
					null);
		}
	}

	@Override
	@Transactional
	public ResponseModel deleteUser(String email) {
		try {
			User user = userRepository.findByEmail(email);
			if (user == null) {
				return generateResponse(HttpStatus.CONFLICT.toString(), "User does not exists", null);
			} else {
				List<Car> cars = carRepository.findByUserId(user.getId());
				if (cars != null && !cars.isEmpty()) {
					for (Car car : cars) {
						deleteCar(car);
						System.out.println("Car id :" + car.getId() + " is deleted");
					}
				}

				List<Comments> comments = commentRepository.findByUserId(user.getId());
				if (comments != null && !comments.isEmpty()) {
					for (Comments comment : comments) {
						commentRepository.delete(comment);
						System.out.println("Comment id :" + comment.getId() + " is deleted");
					}
				}

				userRepository.delete(user);
			}
			return generateResponse(HttpStatus.OK.toString(), "User deleted.", null);
		} catch (Exception e) {
			return generateResponse(HttpStatus.UNAUTHORIZED.toString(), "Unexpected exception occurs:" + e.toString(),
					null);
		}
	}

	@Override
	@Transactional
	public ResponseModel addCar(String email, CarInputDTO carInputDTO) {
		Car car = new Car();
		try {
			if (carInputDTO != null && carInputDTO.getSerialNumber().length() < 7
					|| carInputDTO.getSerialNumber().length() > 8) {
				return generateResponse(HttpStatus.CONFLICT.toString(), "Enter valid serial number ", null);
			}
			User user = userRepository.findById(carInputDTO.getUserId()).orElse(null);
			if (user == null) {
				return generateResponse(HttpStatus.CONFLICT.toString(), "User does not exists", null);
			}
			PickUpPlace pickUpPlace = new PickUpPlace();
			pickUpPlace.setPlaceId(carInputDTO.getPickUpPlace().getPlaceId());
			pickUpPlace.setPlaceName(carInputDTO.getPickUpPlace().getPlaceName());
			pickUpPlace.setLatitude(carInputDTO.getPickUpPlace().getLatitude());
			pickUpPlace.setLongitude(carInputDTO.getPickUpPlace().getLongitude());
			pickUpPlaceRepository.save(pickUpPlace);

			car.setSerialNumber(carInputDTO.getSerialNumber());
			car.setMake(carInputDTO.getMake());
			car.setModal(carInputDTO.getModal());
			car.setYear(carInputDTO.getYear());
			car.setEngine(carInputDTO.getEngine());
			car.setFuel(carInputDTO.getFuel());
			car.setGear(carInputDTO.getGear());
			car.setWheelsDrive(carInputDTO.getWheelsDrive());
			car.setDoors(carInputDTO.getDoors());
			car.setSeats(carInputDTO.getSeats());
			car.setFuelConsumption(carInputDTO.getFuelConsumption());
			car.setFeatures(carInputDTO.getFeatures());
			car.setPricePerDay(carInputDTO.getPricePerDay());
			car.setDistanceIncluded(carInputDTO.getDistanceIncluded());
			car.setAbout(carInputDTO.getAbout());
			car.setPickUpPlace(pickUpPlace);
			car.setImageUrl(carInputDTO.getImageUrl());
			car.setUser(user);

			carRepository.save(car);

			userRepository.save(user);
			return generateResponse(HttpStatus.OK.toString(), "Car is added",
					new ArrayList<>(Arrays.asList(toCarOwnerDto(car))));
		} catch (Exception e) {
			return generateResponse(HttpStatus.UNAUTHORIZED.toString(), "Unexpected exception occurs:" + e.toString(),
					null);
		}

	}

	@Override
	@Transactional
	public ResponseModel updateCar(String email, String serialNumber, CarInputDTO carInputDTO) {
		User user = userRepository.findByEmail(email);
		Car car = carRepository.findBySerialNumber(serialNumber).orElse(null);
		// Checking if new serial number is exists for another car or not

		try {
			Car updatedCar = carRepository.findBySerialNumber(carInputDTO.getSerialNumber()).orElse(null);
			if (updatedCar != null && updatedCar.getSerialNumber() == car.getSerialNumber()
					&& car.getId() != updatedCar.getId()) {
				return generateResponse(HttpStatus.CONFLICT.toString(), "Serial Number is already", null);
			} else {
				if (car.getUser().getId() == user.getId()) {
					PickUpPlace pickUpPlace = new PickUpPlace();
					pickUpPlace.setPlaceId(carInputDTO.getPickUpPlace().getPlaceId());
					pickUpPlace.setPlaceName(carInputDTO.getPickUpPlace().getPlaceName());
					pickUpPlace.setLatitude(carInputDTO.getPickUpPlace().getLatitude());
					pickUpPlace.setLongitude(carInputDTO.getPickUpPlace().getLongitude());
					pickUpPlaceRepository.save(pickUpPlace);

					car.setSerialNumber(carInputDTO.getSerialNumber());
					car.setMake(carInputDTO.getMake());
					car.setModal(carInputDTO.getModal());
					car.setYear(carInputDTO.getYear());
					car.setEngine(carInputDTO.getEngine());
					car.setFuel(carInputDTO.getFuel());
					car.setGear(carInputDTO.getGear());
					car.setWheelsDrive(carInputDTO.getWheelsDrive());
					car.setDoors(carInputDTO.getDoors());
					car.setSeats(carInputDTO.getSeats());
					car.setFuelConsumption(carInputDTO.getFuelConsumption());
					car.setFeatures(carInputDTO.getFeatures());
					car.setPricePerDay(carInputDTO.getPricePerDay());
					car.setDistanceIncluded(carInputDTO.getDistanceIncluded());
					car.setAbout(carInputDTO.getAbout());
					car.setPickUpPlace(pickUpPlace);
					car.setImageUrl(carInputDTO.getImageUrl());
					car.setUser(user);
					carRepository.save(car);
					return generateResponse(HttpStatus.OK.toString(), "Car has been updated",
							new ArrayList<Object>(Arrays.asList(toCarOwnerDto(car))));
				} else {
					return generateResponse(HttpStatus.UNAUTHORIZED.toString(),
							"You are not authorized to update the car.", null);
				}
			}
		} catch (Exception e) {
			return generateResponse(HttpStatus.UNAUTHORIZED.toString(), "Unexpected exception occurs:" + e.toString(),
					null);
		}
	}

	@Override
	@Transactional
	public ResponseModel deleteCar(String email, String serialNumber) {
		try {
			User user = userRepository.findByEmail(email);
			List<Reservation> reservationList = reservationRepository.findBySerialNumber(serialNumber);
			for (Reservation reservation : reservationList) {
				if (LocalDate.now().isBefore(reservation.getEndDateTime())) {

					return generateResponse(HttpStatus.CONFLICT.toString(),
							"Car with Serial Number" + serialNumber + "cannot be deleted, as it is already reserved",
							null);
				}
			}
			Car car = carRepository.findBySerialNumber(serialNumber).orElse(null);
			if (car.getUser().getId() == user.getId()) {
				deleteCar(car);
				return generateResponse(HttpStatus.OK.toString(), "Car with pickup place is deleted", null);
			} else {
				return generateResponse(HttpStatus.UNAUTHORIZED.toString(), "You are not authorized to delete the car.",
						null);
			}
		} catch (Exception e) {
			return generateResponse(HttpStatus.UNAUTHORIZED.toString(), "Unexpected exception occurs:" + e.toString(),
					null);
		}
	}

	private void deleteCar(Car car) {
		PickUpPlace pickUpPlace = car.getPickUpPlace();
		carRepository.delete(car);
		pickUpPlaceRepository.delete(pickUpPlace);
	}

	@Override
	public ResponseModel getCarBySerialNumber(String serialNumber) {
		ResponseModel responseModel = new ResponseModel();
		Car car = carRepository.findBySerialNumber(serialNumber).orElse(null);
		if (car == null) {
			responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
			responseModel.setMessage("Car Not Found.");
			responseModel.setDataList(null);
		} else {
			responseModel.setStatus(HttpStatus.OK.toString());
			responseModel.setDataList(new ArrayList<>(Arrays.asList(toCarOwnerDto(car))));
		}

		return responseModel;
	}

	@Override
	public ResponseModel getOwnerCars(String email) {
		ResponseModel responseModel = new ResponseModel();
		User user = userRepository.findByEmail(email);
		List<Car> ownCars = carRepository.findByUserId(user.getId());
		if (ownCars == null) {
			responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
			responseModel.setMessage("No Cars");
			responseModel.setDataList(null);
		} else {
			responseModel.setStatus(HttpStatus.OK.toString());
			responseModel.setMessage(user.getFirstName() + user.getSecondName() + " cars will displayed down.");
			responseModel.setDataList(new ArrayList<>(toCarListOutputDTO(ownCars)));
		}
		return responseModel;
	}

	@Override
	public ResponseModel getOwnerCarBySerialNumber(String email, String serialNumber) {
		ResponseModel responseModel = new ResponseModel();
		User user = userRepository.findByEmail(email);
		Car car = carRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car Not Exists"));
		if (user.getCars().contains(car)) {
			responseModel.setStatus(HttpStatus.OK.toString());
			responseModel.setMessage("Car with serialNumber " + car.getSerialNumber());
			responseModel.setDataList(new ArrayList<>(Arrays.asList(toCarOutputDto(car))));
		} else {
			responseModel.setDataList(null);
			responseModel.setMessage("Car is own by some one else .");
			responseModel.setStatus(HttpStatus.CONFLICT.toString());
		}
		return responseModel;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseModel getOwnerBookedPeriodsBySerialNumber(String email, String serialNumber) {
		ResponseModel responseModel = new ResponseModel();
		User user = userRepository.findByEmail(email);
		List<Reservation> bookedPeriods = reservationRepository.findBySerialNumber(serialNumber);
		List<Reservation> reservations = bookedPeriods.stream()
				.filter(b -> b.getUser().getEmail().equals(user.getEmail())).collect(Collectors.toList());
		if (!reservations.isEmpty() && reservations != null) {
			responseModel.setStatus(HttpStatus.OK.toString());
			responseModel.setDataList(new ArrayList<>(toBookedListPeriodsDto(reservations)));
			responseModel.setMessage("Booked Periods of serialNumber " + serialNumber);
		} else {
			responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
			responseModel.setMessage("Booked Periods not Found for this serialNumber " + serialNumber);
		}
		return responseModel;
	}

	@Override
	public ResponseModel getLatestComments() {
		List<Comments> comments = commentRepository.findAll().stream()
				.sorted(Comparator.comparing(Comments::getCreatedAt).reversed()).limit(6).collect(Collectors.toList());
		return generateResponse(HttpStatus.OK.toString(), "Last 6 comments: ",
				new ArrayList<>(toCommentsListOutputDto(comments)));
	}

	@Override
	@Transactional
	public ResponseModel addComment(String email, String serialNumber, CommentInputDTO commentInputDTO) {
		ResponseModel responseModel = new ResponseModel();
		User commenter = userRepository.findByEmail(email);// logged in user.
		Car car = carRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car Not Exists"));
		User carOwnerUser = userRepository.findById(car.getUser().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Exists"));// Car owner
		Comments comment = new Comments();
		comment.setUser(commenter);
		comment.setPost(commentInputDTO.getPost());
		comment.setSerialNumber(car.getSerialNumber());

		commentRepository.save(comment);

		userRepository.save(carOwnerUser);

		responseModel.setStatus(HttpStatus.OK.toString());
		responseModel.setDataList(new ArrayList<Object>(Arrays.asList(toCommentsOutputDto(comment))));
		return responseModel;
	}

	@Override
	public ResponseModel getThreeLastCommentsOfCarBySerialNumber(String serialNumber) {
		ResponseModel responseModel = new ResponseModel();
		List<Comments> comments = commentRepository.findBySerialNumber(serialNumber).stream()
				.sorted(Comparator.comparing(Comments::getCreatedAt).reversed()).limit(3).collect(Collectors.toList());
		if (comments == null) {
			responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
			responseModel.setMessage("No Comments Found for this serialNumber");
			responseModel.setDataList(null);
		} else {
			responseModel.setMessage("Comments for serial Number" + serialNumber);
			responseModel.setStatus(HttpStatus.OK.toString());
			responseModel.setDataList(new ArrayList<>(toCommentsListOutputDto(comments)));
		}
		return responseModel;
	}

	@Override
	public ResponseModel getBestBooked() {
		ResponseModel responseModel = new ResponseModel();
		try {
			List<Object> bestBookedCarList = new ArrayList<>();
			Integer pageNo = 0;
			Integer pageSize = 3;
			Pageable paging = PageRequest.of(pageNo, pageSize);
			@SuppressWarnings({ "unchecked" })
			List<Map<String, Object>> bestBookedCars = (List<Map<String, Object>>) (List<?>) reservationRepository
					.countBySerialNumber(paging);

			for (Map<String, Object> bestBookedCarMap : bestBookedCars) {
				for (Map.Entry<String, Object> bestBookedCar : bestBookedCarMap.entrySet()) {
					if (bestBookedCar.getKey().equals("serialNumber")) {
						Car car = carRepository.findBySerialNumber((String) bestBookedCar.getValue()).orElse(null);
						bestBookedCarList.add(toCarOwnerDto(car));
					}
				}
			}
			responseModel.setMessage("Best 3 booked cars found");
			responseModel.setStatus(HttpStatus.OK.toString());
			responseModel.setDataList(bestBookedCarList);

			return responseModel;
		} catch (Exception e) {
			responseModel.setMessage("No car found or an exception occured: " + e.toString());
			responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
			responseModel.setDataList(null);
			return responseModel;
		}
	}

	@Override
	public ResponseModel searchCarByFilters(String make, String modal, String year, String engine, String fuel,
			String gear, String wheelsDrive) {
		List<Car> carList = carRepository.searchByFilters(make, modal, year, engine, fuel, gear, wheelsDrive);
		ResponseModel responseModel = new ResponseModel();
		responseModel.setDataList(new ArrayList<>(toCarOwnerListOutputDTO(carList)));
		return responseModel;
	}

	@Override
	public ResponseModel searchCar(Map<String, String> data, Pageable pageable) {
		ResponseModel responseModel = new ResponseModel();
		try {
			Page<Car> carList = carRepository.searchCar(data, pageable);
			if (carList != null && !carList.isEmpty()) {
				responseModel.setStatus(HttpStatus.OK.toString());
				responseModel.setMessage("Cars found");
				responseModel.setDataList(new ArrayList<>(toCarOwnerListOutputDTO(carList)));
				return responseModel;
			} else {
				responseModel.setStatus(HttpStatus.OK.toString());
				responseModel.setMessage("Cars not found, may be wrong filters are provided");
				responseModel.setDataList(null);
				return responseModel;
			}
		} catch (Exception e) {
			responseModel.setMessage("No car found or an exception occured: " + e.toString());
			responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
			responseModel.setDataList(null);
			return responseModel;
		}
	}

	@Override
	public ResponseModel searchByCoordinates(Double latitude, Double longitude, Double radius, Pageable pageable) {
		ResponseModel responseModel = new ResponseModel();
		try {
			List<Car> carList = carRepository.searchByCoordinates(latitude, longitude, radius, pageable);
			System.out.println(carList);
			if (carList != null && !carList.isEmpty()) {
				responseModel.setStatus(HttpStatus.OK.toString());
				responseModel.setMessage("Cars found");
				responseModel.setDataList(new ArrayList<>(toCarOwnerListOutputDTO(carList)));
				return responseModel;
			} else {
				responseModel.setStatus(HttpStatus.OK.toString());
				responseModel.setMessage("Cars not found, may be wrong filters are provided");
				responseModel.setDataList(null);
				return responseModel;
			}
		} catch (Exception e) {
			responseModel.setMessage("No car found or an exception occured: " + e.toString());
			responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
			responseModel.setDataList(null);
			return responseModel;
		}
	}

	@Override
	public ResponseModel makeReservation(String serialNumber, ReservationInputDTO reservationInputDTO,
			String userEmail) {
		ResponseModel responseModel = new ResponseModel();
		try {
			User user = userRepository.findByEmail(reservationInputDTO.getBookedPerson().getEmail());
			if (user == null) {
				responseModel.setMessage("User not found");
				responseModel.setStatus(HttpStatus.NOT_FOUND.toString());
				responseModel.setDataList(null);
				return responseModel;
			} else {
				Car car = carRepository.findBySerialNumber(serialNumber)
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car Not Found"));

				if (car != null && userEmail.equals(car.getUser().getEmail())) {
					responseModel.setMessage("You cannot book your own car");
					responseModel.setStatus(HttpStatus.CONFLICT.toString());
					responseModel.setDataList(null);
					return responseModel;
				}

				List<Reservation> reservationList = reservationRepository.findBySerialNumber(serialNumber);
				if (reservationList != null && !reservationList.isEmpty()) {
					for (Reservation reservation : reservationList) {
						if (LocalDate.now().isBefore(reservation.getEndDateTime())) {

							return generateResponse(HttpStatus.CONFLICT.toString(), "Car with Serial Number "
									+ serialNumber + " cannot be booked, as it is already reserved", null);
						}
					}
				}

				BigDecimal amount = new BigDecimal(ChronoUnit.DAYS.between(reservationInputDTO.getStartDateTime(),
						reservationInputDTO.getEndDateTime()) * car.getPricePerDay().longValue());

				Reservation reservation = new Reservation();
				reservation.setAmount(amount);
				reservation.setBookingDate(reservationInputDTO.getStartDateTime());
				reservation.setConfirmationCode(CommonConstants.PENDING);
				reservation.setStartDateTime(reservationInputDTO.getStartDateTime());
				reservation.setEndDateTime(reservationInputDTO.getEndDateTime());
				reservation.setOrderNumber("NUMBER" + reservationRepository.findNextOrderNumberSequence());
				reservation.setPaid(false);
				reservation.setUser(user);
				reservation.setSerialNumber(serialNumber);
				reservationRepository.save(reservation);
				carRepository.save(car);
				userRepository.save(user);
				responseModel.setStatus(HttpStatus.OK.toString());
				responseModel.setMessage("Car is reserved");
				responseModel.setDataList(new ArrayList<>(Arrays.asList(toReservationOutputDto(reservation))));
				return responseModel;
			}
		} catch (Exception e) {
			responseModel.setMessage("An Exception occurs: " + e.getMessage());
			responseModel.setStatus(HttpStatus.BAD_REQUEST.toString());
			responseModel.setDataList(null);
			return responseModel;
		}

	}

	public HttpStatus paymentConfirmation(PaymentConfirmInputDTO confirmInputDTO) {
		ResponseModel responseModel = new ResponseModel();
		try {
			Reservation reservation = reservationRepository.findByOrderNumber(confirmInputDTO.getOrderNumber());
			if (confirmInputDTO.getConfirmationCode().equals(CommonConstants.SUCCESS)) {
				reservation.setConfirmationCode(confirmInputDTO.getConfirmationCode());
				reservationRepository.save(reservation);
				return HttpStatus.OK;
			} else {
				responseModel.setStatus(HttpStatus.CONFLICT.toString());
				reservation.setConfirmationCode(confirmInputDTO.getConfirmationCode());
				reservationRepository.delete(reservation);
				return HttpStatus.CONFLICT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return HttpStatus.BAD_REQUEST;
		}
	}

	private ReservationOutputDTO toReservationOutputDto(Reservation reservation) {
		return new ReservationOutputDTO(reservation.getOrderNumber(), reservation.getAmount(),
				reservation.getBookingDate());
	}

	private List<CarOwnerOutputDTO> toCarOwnerListOutputDTO(Page<Car> carList) {
		return carList.stream().map(this::toCarOwnerDto).collect(Collectors.toList());
	}

	private List<CarOwnerOutputDTO> toCarOwnerListOutputDTO(List<Car> carsByCity) {
		return carsByCity.stream().map(this::toCarOwnerDto).collect(Collectors.toList());
	}

	private CarOwnerOutputDTO toCarOwnerDto(Car car) {
		return new CarOwnerOutputDTO(car.getSerialNumber(), car.getMake(), car.getModal(), car.getYear(),
				car.getEngine(), car.getFuel(), car.getGear(), car.getWheelsDrive(), car.getDoors(), car.getSeats(),
				car.getFuelConsumption(), car.getFeatures(), car.getCarClass(), car.getPricePerDay(),
				car.getDistanceIncluded(), car.getAbout(), toPickUpPlaceDto(car.getPickUpPlace()), car.getImageUrl(),
				new OwnerOutputDTO(car.getUser().getFirstName(), car.getUser().getSecondName(),
						car.getUser().getCreatedAt()),
				toBookedListPeriodsDto(car.getUser().getReservations()));
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

	private ResponseModel generateResponse(String status, String message, List<Object> data) {
		return new ResponseModel(status, message, data);
	}
}
