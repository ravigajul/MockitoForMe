package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;

class TestBookingService {


	private PaymentService paymentServiceMock;
	private MailSender mailSenderMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private BookingService bookingServiceMock;

	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.mailSenderMock = mock(MailSender.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.bookingServiceMock= new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
		

	}

	@Test
	void testCalculatePrice() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27), 2, false);
		double expectedPrice=2*2*50.0;
				
		//when
		double actualPrice=bookingServiceMock.calculatePrice(bookingRequest);
		//then
		assertEquals(expectedPrice, actualPrice);
	}

	@Test
	void testCalculatePriceEuro() {
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27), 2, false);
		double expectedPrice=CurrencyConverter.toEuro(2*2*50.0);
				
		//when
		double actualPrice=bookingServiceMock.calculatePriceEuro(bookingRequest);
		//then
		assertEquals(expectedPrice, actualPrice);
	}
	
	@Test
	void testGetAvailablePlaceCount() {
		
		//given returning one room
		List<Room> rooms = Arrays.asList(new Room("1",2));
		when(roomServiceMock.getAvailableRooms()).thenReturn(rooms);
		int expectedPlaceCount=2;
		//when
		int actualPlaceCount=bookingServiceMock.getAvailablePlaceCount();
		
		//then
		assertEquals(expectedPlaceCount, actualPlaceCount);
		
	}
}
