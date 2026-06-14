package uni.pu.fmi.service;

import uni.pu.fmi.models.BlockedDay;
import uni.pu.fmi.repo.BlockedDayRepo;

import java.time.LocalDate;

public class BlockedDayService {

    public BlockedDay blockDay(LocalDate date, String reason) {
        if (date == null) {
            throw new IllegalArgumentException("Датата е задължителна.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Не може да блокирате минала дата.");
        }
        if (BlockedDayRepo.existsByDate(date)) {
            throw new IllegalStateException("Този ден вече е блокиран.");
        }

        BlockedDay blockedDay = new BlockedDay(0L, date, reason == null ? "" : reason);
        return BlockedDayRepo.save(blockedDay);
    }
}
