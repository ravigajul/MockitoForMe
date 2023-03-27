# MockitoForMe

## Check the Coverage
RightClick - > CoverageAs - > JunitTest
## What to Mock?
One should create mocks for all the dependent classes but not the class itself
For example: BookingService class has payment,room and email dependencies. So we can create mocks for these dependent classes but not for the bookingservice class itself.

## Mockito uses nice mocks to return below default values
When we don't specify the return values mockito uses nice mocks to return below default values respectively.
1. Object -> null (null object)

2. List -->[] empty (empty list)

3. int/boolean -->0/false (0 or false primitives)

## Returning Custom Values
```java

//given returning one room
List<Room> rooms = Arrays.asList(new Room("1",2));
when(roomServiceMock.getAvailableRooms()).thenReturn(rooms);
int expectedPlaceCount=2;
//when
int actualPlaceCount=bookingServiceMock.getAvailablePlaceCount();
//then
assertEquals(expectedPlaceCount, actualPlaceCount);

```

## Multiple ThenReturns
This is for returning a particular value when the method is called for the first time and subsequent values when they are called for the other time. First thenreturn value for the first time, 2nd thenreturn value when called second time and so on and so forth.

```java
when(roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room1", 2))).thenReturn(Collections.emptyList());
```

## AssertAll

```mockito
assertAll(()-> assertEquals(actualPlaceCount, expectedPlaceCount),()->assertEquals(actualPlaceCountGain,0)
);
```

## Throwing Exceptions

```java
// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),
				2, false);
when(roomServiceMock.findAvailableRoomId(bookingRequest)).thenThrow(BusinessException.class);
		
//when
Executable executable = ()->bookingServiceMock.makeBooking(bookingRequest);

assertThrows(BusinessException.class, executable);	
```

## Argument Matchers
In the above example when is configured to accept bookingrequest only as an argument. If we created another instance,bookingrequest2 and call the method with this parameter, we would see the method would fail as the when is configured such that only bookingrequest is expected as a parameter. So to address this object or primitive specific matchers like any() or anyDouble() are used.

```java
// given
BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);

//any() --object matcher, anyDouble --primitive type matcher. Here pay method accepts bookingrequest object and price (double) as params.

when(paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);
		
//when
Executable executable = ()->bookingServiceMock.makeBooking(bookingRequest);
		
assertThrows(BusinessException.class, executable);

```
any(),400.0 is not accepted and throws error.However to make it work we need to adjust the code to below. Best practise is not use a combination of argument matcher with real values, either use all argument matchers or all values

```java
when(paymentServiceMock.pay(any(), eq(400.0)))
//argument matchers can be used in then part of the test code as well
verify(paymentServiceMock, never()).pay(any(),anyDouble());
```


## Behaviour Verification

This is to verify if a sub function calls were made or not  when the parent function was called

```java
/**
 * Allows verifying that certain behavior happened at least once / exact number
 * of times / never. E.g:
 *
 * <pre class="code"><code class="java">
 * verify(mock, times(5)).someMethod(&quot;was called five times&quot;);
 *
 * verify(mock, never()).someMethod(&quot;was never called&quot;);
 *
 * verify(mock, atLeastOnce()).someMethod(&quot;was called at least once&quot;);
 *
 * verify(mock, atLeast(2)).someMethod(&quot;was called at least twice&quot;);
 *
 * verify(mock, atMost(3)).someMethod(&quot;was called at most 3 times&quot;);
 *
 * </code></pre>
 *
 * <b>times(1) is the default</b> and can be omitted
 * <p>
 */
//Verifies certain behavior happened once. Alias to verify(mock, times(1)) E.g: verify(mock).someMethod("some arg");
//verify if the pay method was called with 200.0 payment
verify(paymentServiceMock).pay(bookingRequest, 200.0);
		
//Allows verifying exact number of invocations. E.g:   verify(mock, times(2)).someMethod("some arg");
		verify(paymentServiceMock, times(2)).pay(bookingRequest, 200.0);

//Verifies that no interactions happened on given mocks. verifyNoInteractions(mockOne, mockTwo);

verifyNoInteractions(paymentServiceMock);

//You can use this method after you verified your mocks - to make sure that nothingelse was invoked on your mocks.
verifyNoMoreInteractions(paymentServiceMock);

```


## PartialMocks (SPIES)
Mocks are dummy object with no really data and simply returns null or default values
spy are real objects with real logic that we can modify and it can be modified to be a partial mock

```java
this.bookingDAOMock = mock(BookingDAO.class); //mock
this.bookingDAOMock = spy(BookingDAO.class); //spy

//returning the values from mock and spies
mocks: when(mock.method()).thenReturn()
spies: doReturn().when(spy).method())


```
The below method will not error out as we modified the behaviour of the dao service to a spy (partial mock). Here bookingrequest is returned without causing an error by the invalid id "1" when bookingDAOMock tries to fetch the booking id 1 which doesn't exist and continuous forward without any error (doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);)

```java
void should_cancelBooking() {
// given
BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);
bookingRequest.setRoomId("1.3");
String bookingId="1";
doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

// When		
bookingService.cancelBooking(bookingId);
// then		
}
```

## Mocking Void Methods	
One cannot use when().thenThrow for methods that return void. Mockito doesn't support this. We shall use doThrow(<new exceptionclass()>).when(<mockserviceclass>).<method(any())>

## Argument Captors
```java
private ArgumentCaptor<Double> doublecaptor;
this.doublecaptor = ArgumentCaptor.forClass(Double.class);
verify(paymentServiceMock,times(1)).pay(eq(bookingRequest), doublecaptor.capture());
double capturedArgument = doublecaptor.getValue();
System.out.println(capturedArgument);
assertEquals(200.00, capturedArgument);

//When captor returns multiple values
BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),2, true);
BookingRequest bookingRequest2 = new BookingRequest("2", LocalDate.of(2023, 03, 25), LocalDate.of(2023, 03, 27),1, true);
List<Double> capturedArguments;	
//Wheno
bookingServiceMock.makeBooking(bookingRequest);
bookingServiceMock.makeBooking(bookingRequest2);

//then
//raw values and argument matchers cannot be used in combinatation.Hence making it a matcher by adding eq.
verify(paymentServiceMock,times(2)).pay(any(), doublecaptor.capture());
capturedArguments = doublecaptor.getAllValues();
assertAll(()->assertEquals(200.0, capturedArguments.get(0)),
()->assertEquals(100.0, capturedArguments.get(1)));
```

## Mockito Extensions
@Mock

@Captor

@InjectMocks

@ExtendWith
@Spy