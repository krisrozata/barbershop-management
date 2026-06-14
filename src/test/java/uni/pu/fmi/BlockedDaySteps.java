package uni.pu.fmi;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uni.pu.fmi.models.BlockedDay;
import uni.pu.fmi.repo.BlockedDayRepo;
import uni.pu.fmi.service.BlockedDayService;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BlockedDaySteps {

    private LocalDate date;
    private String reason;
    private BlockedDay blockedDay;
    private String errorMessage;

    @Before
    public void resetData() {
        BlockedDayRepo.seed();

    }

    @Given("Барбър отваря панела за управление")
    public void openManagementPanel() {
    }

    @And("денят след {int} дни вече е блокиран")
    public void preBlockDay(int days) {
        BlockedDayRepo.save(new BlockedDay(0L, LocalDate.now().plusDays(days), "Предишно блокиране"));
    }

    @When("барбърът избере дата след {int} дни за блокиране")
    public void selectFutureDate(int days) {
        this.date = LocalDate.now().plusDays(days);
    }

    @When("барбърът избере дата преди {int} дни за блокиране")
    public void selectPastDate(int days) {
        this.date = LocalDate.now().minusDays(days);
    }

    @When("барбърът не въведе дата")
    public void noDate() {
        this.date = null;
    }

    @When("барбърът въведе причина {string}")
    public void enterReason(String reason) {
        this.reason = reason;
    }

    @When("барбърът натисне бутона за блокиране")
    public void clickBlockButton() {
        BlockedDayService blockedDayService = new BlockedDayService();
        try {
            blockedDay = blockedDayService.blockDay(date, reason);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("денят е блокиран успешно")
    public void blockedSuccessfully() {
        assertNotNull("Очаквах денят да бъде блокиран", blockedDay);
        assertNull("Не очаквах грешка", errorMessage);
    }

    @And("причината за блокирането е {string}")
    public void checkReason(String expectedReason) {
        assertNotNull(blockedDay);
        assertEquals(expectedReason, blockedDay.getReason());
    }

    @Then("блокирането е отказано със съобщение {string}")
    public void blockingRejected(String expectedMessage) {
        assertNull("Не очаквах денят да бъде блокиран", blockedDay);
        assertNotNull("Очаквах съобщение за грешка", errorMessage);
        assertEquals(expectedMessage, errorMessage);
    }
}
