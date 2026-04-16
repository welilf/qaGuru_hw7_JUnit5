package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class WikipediaSearchTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "Selenium", "JUnit", "QA"
    })
    @DisplayName("Поиск в Wikipedia: проверка заголовка (ValueSource)")
    @Tag("WEB")
    void wikipediaSearchShouldDisplayCorrectTitle (String searchQuery) {
    open("https://www.wikipedia.org");
    $("#searchInput").setValue(searchQuery).pressEnter();
    $("#firstHeading").shouldHave(text(searchQuery));
    }


    @ParameterizedTest
    @CsvSource(value = {
            "Selenium, /wiki/Selenium",
            "JUnit, /wiki/JUnit",
            "QA, /wiki/QA"
    })
    @DisplayName("Поиск в Wikipedia: проверка заголовка и URL (CsvSource)")
    @Tag("WEB")
    void wikipediaSearchShouldDisplayCorrectTitleAndUrl (String searchQuery, String expectedUrlPart) {
        open("https://www.wikipedia.org");
        $("#searchInput").setValue(searchQuery).pressEnter();
        $("#firstHeading").shouldHave(text(searchQuery));
        webdriver().shouldHave(urlContaining(expectedUrlPart));
    }

    static Stream<Arguments> wikipediaSearchShouldReturnCorrectTitleAndUrl () {
        return Stream.of(
                Arguments.of("Selenium", "/wiki/Selenium"),
                Arguments.of("JUnit", "/wiki/JUnit"),
                Arguments.of("QA", "/wiki/QA")
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("Поиск в Wikipedia: проверка заголовка и URL (MethodSource)")
    @Tag("WEB")
    void wikipediaSearchShouldReturnCorrectTitleAndUrl (String searchQuery, String expectedUrlPart) {
        open("https://www.wikipedia.org");
        $("#searchInput").setValue(searchQuery).pressEnter();
        $("#firstHeading").shouldHave(text(searchQuery));
        webdriver().shouldHave(urlContaining(expectedUrlPart));
    }
}
