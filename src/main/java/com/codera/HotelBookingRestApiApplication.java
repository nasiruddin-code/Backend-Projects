package com.codera;



//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.codera.hotelbooking.config.CorsConfig;
//import com.codera.hotelbooking.model.authentication.User;
//import com.codera.hotelbooking.model.master.Booking;
//import com.codera.hotelbooking.model.master.Hotel;
//import com.codera.hotelbooking.model.master.Room;
//import com.codera.hotelbooking.model.transactional.BookingDetails;
//import com.codera.hotelbooking.model.transactional.Payment;
//import com.codera.hotelbooking.repository.BookingDetailsRepository;
//import com.codera.hotelbooking.repository.BookingRepository;
//import com.codera.hotelbooking.repository.HotelRepository;
//import com.codera.hotelbooking.repository.PaymentRepository;
//import com.codera.hotelbooking.repository.RoomRepository;
//import com.codera.hotelbooking.repository.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server ;



@SpringBootApplication
@Import(CorsConfig.class)
@OpenAPIDefinition(
		info= @Info(
				title="Hotel Booking Application OPEN API",
				version="1.0.0",
				description="Hotel Booking Website OPEN API Documentation,"
						+ "Made by Nasiruddin Manihar "
				
				),
		
		servers=@Server(
				url="http://localhost:8083/hotelbooking",
				description="Hotel Booking OPEN API url"
				)
		
		)
public class HotelBookingRestApiApplication  {

//	private final BookingDetailsRepository bookingDetailsRepository;
//	private final PaymentRepository paymentRepository;

//	  private final HotelRepository hotelRepository;
//    private final RoomRepository roomRepository;
//    private final BookingRepository bookingRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public HotelBookingRestApiApplication (HotelRepository hotelRepository, RoomRepository roomRepository,
//                               BookingRepository bookingRepository, UserRepository userRepository) {
//        this.hotelRepository = hotelRepository;
//        this.roomRepository = roomRepository;
//        this.bookingRepository = bookingRepository;
//        this.userRepository = userRepository;
//    }

	
//	@Autowired
//	public HotelBookingRestApiApplication(BookingDetailsRepository bookingDetailsRepository,
//			PaymentRepository paymentRepository) {
//		this.bookingDetailsRepository = bookingDetailsRepository;
//		this.paymentRepository = paymentRepository;
//
//	}

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingRestApiApplication.class, args);
	}

//	public void run(String... strings) throws Exception {

//		User user1 = new User(null, "john_doe", "password123", "john.doe@example.com", "user");
//		User user2 = new User(null, "jane_smith", "password456", "jane.smith@example.com", "admin");
//
//		userRepository.save(user1);
//		userRepository.save(user2);

//		Hotel hotel1 = new Hotel(null, "Hotel ABC", "123 Main Street, City A");
//		Hotel hotel2 = new Hotel(null, "Hotel XYZ", "456 Park Avenue, City B");
//
//		hotelRepository.save(hotel1);
//		hotelRepository.save(hotel2);
//
//		Room room1 = new Room(null, hotel1, "101", 2, 100.00);
//		Room room2 = new Room(null, hotel2, "201", 4, 150.00);
//
//		roomRepository.save(room1);
//		roomRepository.save(room2);
//		
//		 Booking booking1 = new Booking(null, user1, room1, LocalDateTime.now(), LocalDateTime.now().plusDays(3));        
//       Booking booking2 = new Booking(null, user2, room1, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(5));
//       bookingRepository.save(booking1);
//       bookingRepository.save(booking2);
//	}
//		BookingDetails bookingDetails1 = new BookingDetails(null, null, "John Doe", "john.doe@example.com");
//		BookingDetails bookingDetails2 = new BookingDetails(null, null, "Alice Smith", "alice.smith@example.com");
//
//		bookingDetailsRepository.save(bookingDetails1);
//		bookingDetailsRepository.save(bookingDetails2);
//
//		Payment payment1 = new Payment(null, null, 100.0, LocalDateTime.now(), "Credit Card");
//		Payment payment2 = new Payment(null, null, 150.0, LocalDateTime.now(), "PayPal");
//
//		paymentRepository.save(payment1);
//		paymentRepository.save(payment2);

	
}
