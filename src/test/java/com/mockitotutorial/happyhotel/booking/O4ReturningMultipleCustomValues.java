package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class O4ReturningMultipleCustomValues {

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
	void testGetAvailablePlacesCount() {

		// given returning multiple room
		List<Room> rooms = Arrays.asList(new Room("Room1", 2), new Room("Room2", 4));
		when(roomServiceMock.getAvailableRooms()).thenReturn(rooms);
		int expectedPlaceCount = 6;
		// when
		int actualPlaceCount = bookingService.getAvailablePlaceCount();

		// then
		assertEquals(expectedPlaceCount, actualPlaceCount);

	}

}
