package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.apache.commons.codec.language.bm.Lang;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class KinopoiskTest {
    //Test1 with ValueSource
    @ValueSource(strings = {"Джуманджи", "Бойфренд из будущего"})
    @ParameterizedTest(name = "Check search results for {0}")
    void searchMoviesNotNull(String TestData) {
        open("https://www.kinopoisk.ru/");
        $("[type=\"text\"]").setValue(TestData).pressEnter();
        //  $$(".element most_wanted").shouldBe(CollectionCondition.sizeGreaterThan(0));
        $$(".search_results").shouldBe(CollectionCondition.sizeGreaterThan(0));
    }

    //Test2
    @CsvSource(value = {
            "Джуманджи| 7.9",
            "Бойфренд из будущего| 7.9",
    }, delimiter = '|')

    @ParameterizedTest(name = "Check search results {1} for {0}")
    void searchMovieswithResult(String testData, String expectedResult) {
        open("https://www.kinopoisk.ru/");
        $("[type=\"text\"]").setValue(testData).pressEnter();
        $$(".ratingGreenBG").first().shouldHave(text(expectedResult));

    }

    //Test3
    static Stream<Arguments> dataProviderForKinopoiskMenu() {
        return Stream.of(
                Arguments.of("Джуманджи", List.of("актеры трейлеры кадры постеры сеансы сайты"))
        );
    }

    @MethodSource("dataProviderForKinopoiskMenu")
    @ParameterizedTest(name = "Check that local {0} has buttons {1}")
    void searchMovieswithResult(String film, List<String> expectedButtons) {
        open("https://www.kinopoisk.ru/");
        $("[type=\"text\"]").setValue(film).pressEnter();
        $$(".most_wanted ul.links").shouldHave(CollectionCondition.texts(expectedButtons));

    }
}


