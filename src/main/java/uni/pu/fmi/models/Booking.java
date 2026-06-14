package uni.pu.fmi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private long id;
    private String clientName;
    private String phone;
    private Service service;
    private LocalDate date;
    private LocalTime time;
}
