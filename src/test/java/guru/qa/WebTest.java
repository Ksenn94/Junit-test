package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.apache.commons.codec.language.bm.Lang;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    @ValueSource(strings = {"Selenide", "Allure"})
    //@Disabled("comment") this annotation disables the test
    //displayname is used for allure reports, it shows the name of the test
    //@DisplayName("Search results are not empty for selenide")
    @ParameterizedTest(name = "Check that search results are not empty for {0}")
    void commonSearchTest(String testData) {
        open("https://ya.ru");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("li.serp-item").shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    @CsvSource(value = {
            "Selenide, это фреймворк для автоматизированного тестирования веб-приложений",
            "Allure java, успешно применяется в работе автоматизатора",
    })
    @ParameterizedTest(name = "Check that search results contain text {1} for {0}")
    void commonComplexSearchTest(String testData, String expectedResult) {
        open("https://ya.ru");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("li.serp-item").filter(not(text("Реклама")))
                .first()
                .shouldHave(text(expectedResult));
    }

}

