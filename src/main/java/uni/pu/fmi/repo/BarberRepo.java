package uni.pu.fmi.repo;

import uni.pu.fmi.models.Barber;

import java.util.ArrayList;
import java.util.List;

public class BarberRepo {

    private static final List<Barber> barbers = new ArrayList<>();

    static {
        barbers.add(new Barber(1L, "admin", "admin123", "Главен Барбър"));
        barbers.add(new Barber(2L, "barber1", "pass123", "Стоян Стоянов"));
    }

    public static List<Barber> getAll() {
        return new ArrayList<>(barbers);
    }
}
