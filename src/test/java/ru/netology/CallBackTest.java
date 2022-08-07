package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CallBackTest {
    @BeforeEach
    void openApplication(){
        open("http://localhost:9999/");
    }

    @Test
    void shouldSubmitRequest(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+70001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
    @Test
    void shouldSubmitRequestWithDashSurname(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иван Иванов-Петров");
        form.$("[data-test-id=phone] input").setValue("+70001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void errorNameNonRussianSymbolsField(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Ivan Ivanov");
        form.$("[data-test-id=phone] input").setValue("+70001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void errorNameSpecialSymbolsField(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иван%Иван Ивнов");
        form.$("[data-test-id=phone] input").setValue("+70001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void emptyNameField(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+70001234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void errorPhoneField(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("1234567");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void emptyPhoneField(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void emptyAgreetmentCheck(){
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+70001234567");
        form.$("button").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
