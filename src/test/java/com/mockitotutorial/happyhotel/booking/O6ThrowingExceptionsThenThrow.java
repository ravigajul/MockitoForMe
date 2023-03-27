package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class O6ThrowingExceptionsThenThrow {

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
	void should_throw_Exception_WhenNoRoomsAvailable() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),
				2, false);
		when(roomServiceMock.findAvailableRoomId(bookingRequest)).thenThrow(BusinessException.class);

		// when
		Executable executable = () -> bookingService.makeBooking(bookingRequest);

		assertThrows(BusinessException.class, executable);
	}

}
