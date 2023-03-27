package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;


import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class O7Matchers {

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
		this.bookingServiceMock = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,
				mailSenderMock);

	}

	@Test
	void should_throw_Exception_PriceIsTooHigh() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),
				2, true);
		//any() --object matcher, anyDouble --primitive type matcher. Here pay method accepts bookingrequest object and price (double) as params.
		when(paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);
		
		//when
		Executable executable = ()->bookingServiceMock.makeBooking(bookingRequest);
		
		assertThrows(BusinessException.class, executable);		
	}
	
}
