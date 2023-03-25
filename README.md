# MockitoForMe

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