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
```

## Behaviour Verification

This is to verify if a sub function calls were made or not  when the parent function was called

```java
//Verifies certain behavior happened once. Alias to verify(mock, times(1)) E.g: verify(mock).someMethod("some arg");
verify(paymentServiceMock).pay(bookingRequest, 200.0);
		
//Allows verifying exact number of invocations. E.g:   verify(mock, times(2)).someMethod("some arg");
		verify(paymentServiceMock, times(2)).pay(bookingRequest, 200.0);

//Verifies that no interactions happened on given mocks. verifyNoInteractions(mockOne, mockTwo);

verifyNoInteractions(paymentServiceMock);

//You can use this method after you verified your mocks - to make sure that nothingelse was invoked on your mocks.
verifyNoMoreInteractions(paymentServiceMock);

```

