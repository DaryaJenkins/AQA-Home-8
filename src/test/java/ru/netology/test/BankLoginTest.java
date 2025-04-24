package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BankLoginTest {

    LoginPage loginPage;
    DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldSuccessfullyLogin() {
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldFailIfUserNotInDb() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.login(authInfo);
        loginPage.verifyErrorNotification("Ошибка\n" +
                "Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldFailLoginWithWrongCode() {
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.generateRandomCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка\n" +
                "Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}