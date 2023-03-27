package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class O5MultipleThenReturnCalls {

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
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

	}

	@Test
	void should_Count_AvailablePlacesCount_WhenCalledMultipleTimes() {

		// given returning multiple room

		when(roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room1", 2)))
				.thenReturn(Collections.emptyList());
		int expectedPlaceCount = 2;
		// when
		int actualPlaceCount = bookingService.getAvailablePlaceCount();
		int actualPlaceCountGain = bookingService.getAvailablePlaceCount();

		// then
		assertAll(() -> assertEquals(actualPlaceCount, expectedPlaceCount),
				() -> assertEquals(actualPlaceCountGain, 0));

	}

}
