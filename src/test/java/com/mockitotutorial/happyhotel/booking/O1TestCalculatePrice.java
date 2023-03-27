package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class O1TestCalculatePrice {

	private PaymentService paymentServiceMock;
	private MailSender mailSenderMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private BookingService bookingService;

	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.mailSenderMock = mock(MailSender.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = spy(BookingDAO.class);
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,
				mailSenderMock);

	}

	@Test
	void testCalculatePrice() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),
				2, false);
		double expectedPrice = 2 * 2 * 50.0;
		System.out.println("ExpectedPrice="+expectedPrice);

		// when
		double actualPrice = bookingService.calculatePrice(bookingRequest);
		System.out.println("ActualPrice="+actualPrice);
		// then
		assertEquals(expectedPrice, actualPrice);
	}

	
}
