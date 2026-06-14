package uni.pu.fmi.service;

import org.apache.commons.lang3.StringUtils;
import uni.pu.fmi.models.Booking;
import uni.pu.fmi.models.Service;
import uni.pu.fmi.repo.BlockedDayRepo;
import uni.pu.fmi.repo.BookingRepo;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingService {

    public Booking book(String clientName, String phone, String serviceText,
                        LocalDate date, LocalTime time) {

        if (StringUtils.isBlank(clientName) || StringUtils.isBlank(phone)
                || StringUtils.isBlank(serviceText) || date == null || time == null) {
            throw new IllegalArgumentException("Всички полета са задължителни.");
        }

        Service service = Service.getByText(serviceText);
        if (service == null) {
            throw new IllegalArgumentException("Невалидна услуга: " + serviceText);
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Не може да резервирате минала дата.");
        }

        if (BlockedDayRepo.existsByDate(date)) {
            throw new IllegalStateException("Този ден е блокиран.");
        }

        if (BookingRepo.existsByDateAndTime(date, time)) {
            throw new IllegalStateException("Този час вече е зает.");
        }

        Booking booking = new Booking(0L, clientName, phone, service, date, time);
        return BookingRepo.save(booking);
    }
}
