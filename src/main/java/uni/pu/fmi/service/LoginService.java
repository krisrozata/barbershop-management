package uni.pu.fmi.service;

import org.apache.commons.lang3.StringUtils;
import uni.pu.fmi.models.Barber;
import uni.pu.fmi.repo.BarberRepo;

public class LoginService {

    public Barber login(String username, String password) {
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Потребителското име е задължително.");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Паролата е задължителна.");
        }

        return BarberRepo.getAll().stream()
                .filter(b -> b.getUsername().equals(username)
                        && b.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Грешно потребителско име или парола."));
    }
}
