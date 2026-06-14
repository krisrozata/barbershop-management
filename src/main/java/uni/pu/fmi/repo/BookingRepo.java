package uni.pu.fmi.repo;

import uni.pu.fmi.models.Booking;
import uni.pu.fmi.models.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingRepo {

    private static final List<Booking> bookings = new ArrayList<>();
    private static long nextId = 1L;

    static {
        seed();
    }

    public static void seed() {
        bookings.clear();
        nextId = 1L;
        bookings.add(new Booking(nextId++, "Георги Петров", "0888111222",
                Service.HAIRCUT, LocalDate.now().plusDays(1), LocalTime.of(10, 0)));
        bookings.add(new Booking(nextId++, "Иван Иванов", "0888333444",
                Service.BEARD, LocalDate.now().plusDays(1), LocalTime.of(11, 0)));
    }

    public static List<Booking> getAll() {
        return new ArrayList<>(bookings);
    }

    public static Booking save(Booking booking) {
        if (booking.getId() == 0L) {
            booking.setId(nextId++);
        }
        bookings.add(booking);
        return booking;
    }

    public static boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return bookings.stream()
                .anyMatch(b -> b.getDate().equals(date) && b.getTime().equals(time));
    }
}
