package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;


import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class O8BehaviorVerification {

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
	void should_verify_the_paymentmethod_IsCalled() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);
			
		
		//Wheno
		bookingServiceMock.makeBooking(bookingRequest);
		
		//then
		//This ensures that the booking was made with 200 calcuated price for two days of accomodation for 2 guests
		//Verifies certain behavior happened once
		verify(paymentServiceMock,times(1)).pay(bookingRequest, 200.0);
		
		//Allows verifying exact number of invocations. E.g:   verify(mock, times(2)).someMethod("some arg");
		//verify(paymentServiceMock, times(2)).pay(bookingRequest, 200.0);
		
		//Verifies that no interactions happened on given mocks. verifyNoInteractions(mockOne, mockTwo);

			//verifyNoInteractions(paymentServiceMock);
		
		//You can use this method after you verified your mocks - to make sure that nothingelse was invoked on your mocks.
		verifyNoMoreInteractions(paymentServiceMock);
	}
}
