package uni.pu.fmi;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"html:target/report.html", "pretty"},
        monochrome = true)
public class CucumberStarter {
}
