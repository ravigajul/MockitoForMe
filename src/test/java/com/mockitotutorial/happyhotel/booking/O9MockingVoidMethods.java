package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;


import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class O9MockingVoidMethods {

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
	void should_throwException_whenMailNotReady() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);
		//used for mocking void methods
		doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());	
		
		//When
		Executable executable = ()->bookingServiceMock.makeBooking(bookingRequest);
		
		//then
		assertThrows(BusinessException.class,executable);
		
	}
	
	@Test
	void should_notthrowException_whenMailNotReady() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);
		doNothing().when(mailSenderMock).sendBookingConfirmation(any());	
		
		//When
		bookingServiceMock.makeBooking(bookingRequest);
		//then
		//donothing
		
	}
}
