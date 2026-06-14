package uni.pu.fmi.models;

import java.util.Arrays;

public enum Service {
    HAIRCUT("Подстригване"),
    BEARD("Брада"),
    HAIRCUT_AND_BEARD("Подстригване + брада");

    private final String bgName;

    Service(String bgName) {
        this.bgName = bgName;
    }

    public String getBgName() {
        return bgName;
    }

    public static Service getByText(String text) {
        return Arrays.stream(values())
                .filter(s -> s.getBgName().equalsIgnoreCase(text))
                .findFirst().orElse(null);
    }
}
