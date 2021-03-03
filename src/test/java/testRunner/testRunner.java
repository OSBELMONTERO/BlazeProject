package testRunner;


import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"html:reports/cucumber-html-report",
        "json:reports/cucumber.json",
        "pretty"},
        tags= "@purchaseTest",
        features = "src/test/resources",
        dryRun = false,
        glue="stepDefinitions"
)

public class testRunner {
}
