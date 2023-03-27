package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.*;

class O3ReturningCustomValue {

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
	void testCalculatePriceEuro() {
		
		//Given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),
				2, false);
		double expectedPrice = CurrencyConverter.toEuro(2 * 2 * 50.0);

		// when
		double actualPrice = bookingService.calculatePriceEuro(bookingRequest);
		// then
		assertEquals(expectedPrice, actualPrice);
	}

}
