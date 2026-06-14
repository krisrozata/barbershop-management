package uni.pu.fmi;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uni.pu.fmi.models.Barber;
import uni.pu.fmi.service.LoginService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginSteps {

    private String username;
    private String password;
    private Barber loggedBarber;
    private String errorMessage;

    @Before
    public void resetData() {
        username = null;
        password = null;
        loggedBarber = null;
        errorMessage = null;
    }

    @Given("Барбър отваря екрана за вход")
    public void openLoginScreen() {
    }

    @When("барбърът въведе потребителско име {string}")
    public void enterUsername(String username) {
        this.username = username;
    }

    @When("барбърът въведе парола {string}")
    public void enterPassword(String password) {
        this.password = password;
    }

    @When("барбърът натисне бутона за вход")
    public void clickLoginButton() {
        LoginService loginService = new LoginService();
        try {
            loggedBarber = loginService.login(username, password);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("логинът е успешен")
    public void loginSuccessful() {
        assertNotNull("Очаквах успешен логин", loggedBarber);
        assertNull("Не очаквах грешка", errorMessage);
    }

    @And("името на барбъра е {string}")
    public void checkBarberName(String expectedName) {
        assertNotNull(loggedBarber);
        assertEquals(expectedName, loggedBarber.getFullName());
    }

    @Then("логинът е отказан със съобщение {string}")
    public void loginRejected(String expectedMessage) {
        assertNull("Не очаквах успешен логин", loggedBarber);
        assertNotNull("Очаквах съобщение за грешка", errorMessage);
        assertEquals(expectedMessage, errorMessage);
    }
}
