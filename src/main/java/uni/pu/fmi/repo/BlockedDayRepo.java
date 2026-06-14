package uni.pu.fmi.repo;

import uni.pu.fmi.models.BlockedDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlockedDayRepo {

    private static final List<BlockedDay> blockedDays = new ArrayList<>();
    private static long nextId = 1L;

    static {
        seed();
    }

    public static void seed() {
        blockedDays.clear();
        nextId = 1L;
        blockedDays.add(new BlockedDay(nextId++, LocalDate.now().plusDays(7), "Почивен ден"));
    }

    public static List<BlockedDay> getAll() {
        return new ArrayList<>(blockedDays);
    }

    public static BlockedDay save(BlockedDay blockedDay) {
        if (blockedDay.getId() == 0L) {
            blockedDay.setId(nextId++);
        }
        blockedDays.add(blockedDay);
        return blockedDay;
    }

    public static boolean existsByDate(LocalDate date) {
        return blockedDays.stream()
                .anyMatch(bd -> bd.getDate().equals(date));
    }

    public static Optional<BlockedDay> findByDate(LocalDate date) {
        return blockedDays.stream()
                .filter(bd -> bd.getDate().equals(date))
                .findFirst();
    }
}
