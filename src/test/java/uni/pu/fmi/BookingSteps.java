package uni.pu.fmi;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uni.pu.fmi.models.Booking;
import uni.pu.fmi.models.BlockedDay;
import uni.pu.fmi.models.Service;
import uni.pu.fmi.repo.BlockedDayRepo;
import uni.pu.fmi.repo.BookingRepo;
import uni.pu.fmi.service.BookingService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BookingSteps {

    private String clientName;
    private String phone;
    private String serviceText;
    private LocalDate date;
    private LocalTime time;

    private Booking createdBooking;
    private String errorMessage;

    @Before
    public void resetData() {
        BookingRepo.seed();
        BlockedDayRepo.seed();
        clientName = null;
        phone = null;
        serviceText = null;
        date = null;
        time = null;
        createdBooking = null;
        errorMessage = null;
    }

    @Given("Клиент отваря екрана за резервация")
    public void openBookingScreen() {
    }

    @And("съществува резервация за след {int} ден в час {string}")
    public void existingBooking(int days, String timeText) {
        Booking existing = new Booking(0L, "Стар Клиент", "0888000000",
                Service.HAIRCUT, LocalDate.now().plusDays(days), LocalTime.parse(timeText));
        BookingRepo.save(existing);
    }

    @And("денят след {int} дни е блокиран")
    public void blockDay(int days) {
        BlockedDayRepo.save(new BlockedDay(0L, LocalDate.now().plusDays(days), "Блокиран"));
    }

    @When("клиентът въведе име {string}")
    public void enterName(String name) {
        this.clientName = name;
    }

    @When("клиентът въведе телефон {string}")
    public void enterPhone(String phone) {
        this.phone = phone;
    }

    @When("клиентът избере услуга {string}")
    public void selectService(String service) {
        this.serviceText = service;
    }

    @When("клиентът избере дата след {int} дни")
    public void selectFutureDate(int days) {
        this.date = LocalDate.now().plusDays(days);
    }

    @When("клиентът избере дата след {int} ден")
    public void selectFutureDateSingular(int days) {
        this.date = LocalDate.now().plusDays(days);
    }

    @When("клиентът избере дата преди {int} дни")
    public void selectPastDate(int days) {
        this.date = LocalDate.now().minusDays(days);
    }

    @When("клиентът избере час {string}")
    public void selectTime(String timeText) {
        this.time = LocalTime.parse(timeText);
    }

    @When("клиентът натисне бутона за резервация")
    public void clickBookButton() {
        BookingService bookingService = new BookingService();
        try {
            createdBooking = bookingService.book(clientName, phone, serviceText, date, time);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("резервацията е създадена успешно")
    public void bookingCreated() {
        assertNotNull("Очаквах резервацията да бъде създадена", createdBooking);
        assertNull("Не очаквах грешка", errorMessage);
    }

    @Then("услугата на резервацията е {string}")
    public void checkService(String expectedService) {
        assertNotNull(createdBooking);
        assertEquals(expectedService, createdBooking.getService().getBgName());
    }

    @Then("резервацията е отказана със съобщение {string}")
    public void bookingRejected(String expectedMessage) {
        assertNull("Не очаквах резервацията да бъде създадена", createdBooking);
        assertNotNull("Очаквах съобщение за грешка", errorMessage);
        assertEquals(expectedMessage, errorMessage);
    }
}
