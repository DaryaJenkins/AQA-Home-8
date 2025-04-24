package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private final SelenideElement dashboard = $("[data-test-id=dashboard]");

    public DashboardPage() {
        dashboard.shouldHave(text("Личный кабинет")).shouldBe(Condition.visible);
    }
}