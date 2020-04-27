package com.car.rentservice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

}

@Component
class DataInsertingCommandLineRunner implements CommandLineRunner {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String isDdl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private PickUpPlaceRepository pickUpPlaceRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private PasswordEncoder bCryptEncoder;

	@Override
	public void run(String... args) throws Exception {

		if (isDdl.equals("create") || isDdl.equals("create-drop")) {
			System.out.println(isDdl);
			User newUser = new User();
			newUser.setEmail("nir.simkin@gmail.com");
			newUser.setFirstName("nir");
			newUser.setSecondName("simkin");
			newUser.setPhotoUrl("img.com");
			newUser.setPhone("0524070215");
			newUser.setPassword(bCryptEncoder.encode("12345"));
			newUser.setCars(new ArrayList<>());
			newUser.setComments(new ArrayList<>());
			newUser.setReservations(new ArrayList<>());
			userRepository.save(newUser);

			User newUser2 = new User();
			newUser2.setEmail("nir.simkin2@gmail.com");
			newUser2.setFirstName("nir2");
			newUser2.setSecondName("simkin2");
			newUser2.setPhotoUrl("img2.com");
			newUser2.setPhone("05240702152");
			newUser2.setPassword(bCryptEncoder.encode("12345"));
			newUser2.setCars(new ArrayList<>());
			newUser2.setComments(new ArrayList<>());
			newUser2.setReservations(new ArrayList<>());
			userRepository.save(newUser2);

			User newUser3 = new User();
			newUser3.setEmail("nir.simkin3@gmail.com");
			newUser3.setFirstName("nir2");
			newUser3.setSecondName("simkin2");
			newUser3.setPhotoUrl("img2.com");
			newUser3.setPhone("05240702152");
			newUser3.setPassword(bCryptEncoder.encode("12345"));
			newUser3.setCars(new ArrayList<>());
			newUser3.setComments(new ArrayList<>());
			newUser3.setReservations(new ArrayList<>());
			userRepository.save(newUser3);

			User newUser4 = new User();
			newUser4.setEmail("nir.simkin4@gmail.com");
			newUser4.setFirstName("nir2");
			newUser4.setSecondName("simkin2");
			newUser4.setPhotoUrl("img2.com");
			newUser4.setPhone("05240702152");
			newUser4.setPassword(bCryptEncoder.encode("12345"));
			newUser4.setCars(new ArrayList<>());
			newUser4.setComments(new ArrayList<>());
			newUser4.setReservations(new ArrayList<>());
			userRepository.save(newUser4);

			PickUpPlace pickUpPlace = new PickUpPlace();
			pickUpPlace.setPlaceId("123");
			pickUpPlace.setPlaceName("netanya");
			pickUpPlace.setLatitude(new BigDecimal(123.3));
			pickUpPlace.setLongitude(new BigDecimal(145.65));
			pickUpPlaceRepository.save(pickUpPlace);

			PickUpPlace pickUpPlace2 = new PickUpPlace();
			pickUpPlace2.setPlaceId("124");
			pickUpPlace2.setPlaceName("netanya2");
			pickUpPlace2.setLatitude(new BigDecimal(1623.3));
			pickUpPlace2.setLongitude(new BigDecimal(1145.65));
			pickUpPlaceRepository.save(pickUpPlace2);

			Comments comment1 = new Comments();
			comment1.setPost("Hey try1");
			comment1.setSerialNumber("1234567");
			comment1.setUser(newUser);
			commentRepository.save(comment1);

			Car car1 = new Car();
			car1.setSerialNumber("1234567");
			car1.setMake("Mercedes1");
			car1.setModal("Mercedes benz");
			car1.setYear("2001");
			car1.setEngine("Engine V6");
			car1.setFuel("fuel");
			car1.setGear("VWR");
			car1.setWheelsDrive("RWD");
			car1.setDoors(4);
			car1.setSeats(5);
			car1.setFuelConsumption(new BigDecimal(13.5));
			car1.setFeatures(Arrays.asList("Try1", "Try2"));
			car1.setPricePerDay(new BigDecimal(40.5));
			car1.setDistanceIncluded(133);
			car1.setAbout("About car");
			car1.setPickUpPlace(pickUpPlace);
			car1.setImageUrl(Arrays
					.asList("https://images.auto.co.il/Attachment/Gallery/227750/1588670/BMW-i8%203.jpg?width=600"));
			car1.setUser(newUser);
			carRepository.save(car1);
			Car car2 = new Car();

			car2.setSerialNumber("1234568");
			car2.setMake("Mercedes");
			car2.setModal("Mercedes benz");
			car2.setYear("2002");
			car2.setEngine("Engine V6");
			car2.setFuel("fuel");
			car2.setGear("VWR");
			car2.setWheelsDrive("RWD");
			car2.setDoors(4);
			car2.setSeats(5);
			car2.setFuelConsumption(new BigDecimal(13.5));
			car2.setFeatures(Arrays.asList("Try1", "Try2"));
			car2.setPricePerDay(new BigDecimal(40.5));
			car2.setDistanceIncluded(133);
			car2.setAbout("About car2");
			car2.setPickUpPlace(pickUpPlace2);
			car2.setImageUrl(Arrays
					.asList("https://images.auto.co.il/Attachment/Gallery/227750/1588670/BMW-i8%203.jpg?width=600"));
			car2.setUser(newUser2);
			carRepository.save(car2);

			Car car3 = new Car();
			car3.setSerialNumber("1234569");
			car3.setMake("Mercedes");
			car3.setModal("Mercedes benz");
			car3.setYear("2002");
			car3.setEngine("Engine V6");
			car3.setFuel("fuel");
			car3.setGear("VWR");
			car3.setWheelsDrive("RWD");
			car3.setDoors(4);
			car3.setSeats(5);
			car3.setFuelConsumption(new BigDecimal(13.5));
			car3.setFeatures(Arrays.asList("Try1", "Try2"));
			car3.setPricePerDay(new BigDecimal(40.5));
			car3.setDistanceIncluded(133);
			car3.setAbout("About car2");
			car3.setPickUpPlace(pickUpPlace2);
			car3.setImageUrl(Arrays
					.asList("https://images.auto.co.il/Attachment/Gallery/227750/1588670/BMW-i8%203.jpg?width=600"));
			car3.setUser(newUser3);
			carRepository.save(car3);

			Car car4 = new Car();
			car4.setSerialNumber("1234561");
			car4.setMake("Mercedes");
			car4.setModal("Mercedes benz");
			car4.setYear("2002");
			car4.setEngine("Engine V6");
			car4.setFuel("fuel");
			car4.setGear("VWR");
			car4.setWheelsDrive("RWD");
			car4.setDoors(4);
			car4.setSeats(5);
			car4.setFuelConsumption(new BigDecimal(13.5));
			car4.setFeatures(Arrays.asList("Try1", "Try2"));
			car4.setPricePerDay(new BigDecimal(40.5));
			car4.setDistanceIncluded(133);
			car4.setAbout("About car2");
			car4.setPickUpPlace(pickUpPlace2);
			car4.setImageUrl(Arrays
					.asList("https://images.auto.co.il/Attachment/Gallery/227750/1588670/BMW-i8%203.jpg?width=600"));
			car4.setUser(newUser4);
			carRepository.save(car4);

			Reservation reservation1 = new Reservation();
			reservation1.setAmount(new BigDecimal(123.5));
			reservation1.setBookingDate(LocalDateTime.now().toLocalDate());		
			reservation1.setStartDateTime(LocalDateTime.now().toLocalDate());
			reservation1.setEndDateTime(LocalDateTime.now().toLocalDate().plusDays(1));
			reservation1.setOrderNumber("1");
			// reservation1.setConfirmationCode("c234512");
			reservation1.setSerialNumber("1234567");
			reservation1.setUser(newUser);
			reservationRepository.save(reservation1);

			Reservation reservation2 = new Reservation();
			reservation2.setAmount(new BigDecimal(123.5));
			reservation2.setBookingDate(LocalDateTime.now().toLocalDate());
			reservation2.setStartDateTime(LocalDateTime.now().toLocalDate());
			reservation2.setEndDateTime(LocalDateTime.now().toLocalDate().plusDays(1));
			reservation2.setSerialNumber("1234568");
			reservation2.setOrderNumber("1");
			// reservation2.setConfirmationCode("c234513");
			reservation2.setUser(newUser2);
			reservationRepository.save(reservation2);

			Reservation reservation3 = new Reservation();
			reservation3.setAmount(new BigDecimal(123.5));
			reservation3.setBookingDate(LocalDateTime.now().toLocalDate());
			reservation3.setStartDateTime(LocalDateTime.now().toLocalDate());
			reservation3.setEndDateTime(LocalDateTime.now().toLocalDate().plusDays(1));
			reservation3.setSerialNumber("1234568");
			reservation3.setOrderNumber("1");
			// reservation2.setConfirmationCode("c234513");
			reservation3.setUser(newUser2);
			reservationRepository.save(reservation3);

		}
	}
}
