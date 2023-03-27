package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OIIMokitoAnnotations {
	@Mock
	private PaymentService paymentServiceMock;
	@Mock
	private MailSender mailSenderMock;
	@Mock
	private RoomService roomServiceMock;
	@Spy
	private BookingDAO bookingDAOMock;
	@InjectMocks
	private BookingService bookingServiceMock;
	@Captor
	private ArgumentCaptor<Double> doublecaptor;

	/*
	 * @BeforeEach void setup() { this.paymentServiceMock =
	 * mock(PaymentService.class); this.mailSenderMock = mock(MailSender.class);
	 * this.roomServiceMock = mock(RoomService.class); this.bookingDAOMock =
	 * mock(BookingDAO.class); this.bookingServiceMock = new
	 * BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock,
	 * mailSenderMock); this.doublecaptor = ArgumentCaptor.forClass(Double.class);
	 * 
	 * }
	 */

	@Test
	void should_PayCorrectPrice_WhenInputOK() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);
			
		
		//Wheno
		bookingServiceMock.makeBooking(bookingRequest);
		
		//then
		//raw values and argument matchers cannot be used in combinatation.Hence making it a matcher by adding eq.
		verify(paymentServiceMock,times(1)).pay(eq(bookingRequest), doublecaptor.capture());
		double capturedArgument = doublecaptor.getValue();
		System.out.println(capturedArgument);
		assertEquals(200.00, capturedArgument);
		
	}
	
	@Test
	void should_PayCorrectPrice_WhenMultipleCalls() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);
		BookingRequest bookingRequest2 = new BookingRequest("2", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),1, true);
		List<Double> expectedList=Arrays.asList(200.0,100.0);
		List<Double> capturedArguments;
		
		//Wheno
		bookingServiceMock.makeBooking(bookingRequest);
		bookingServiceMock.makeBooking(bookingRequest2);
		
		//then
		//raw values and argument matchers cannot be used in combinatation.Hence making it a matcher by adding eq.
		verify(paymentServiceMock,times(2)).pay(any(), doublecaptor.capture());
		capturedArguments = doublecaptor.getAllValues();
		
		/*
		 * // alternatively assertAll(()->assertEquals(200.0, capturedArguments.get(0)),
		 * ()->assertEquals(100.0, capturedArguments.get(1)));
		 */
		
		assertEquals(expectedList, capturedArguments);
		
	}
}
