package ru.netology.web.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.platform.engine.support.discovery.SelectorResolver;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.VerificationPage;

import static ru.netology.web.data.DataHelper.getAuthInfo;

public class TemplateSteps {
    private static DashboardPage dashboardPage;
    private static LoginPage loginPage;
    private static TransferPage transferPage;
    private static VerificationPage verificationPage;

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь залогинен с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(new DataHelper.AuthInfo(login, password));
    }

    //пусть пользователь залогинен с именем «vasya» и паролем «qwerty123»,
    @И("пользователь вводит проверочный код {string}")
    public void setValidCode(String code) {
        dashboardPage = verificationPage.validVerify(new DataHelper.VerificationCode(code));
    }

//    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет' и видит баланс {string} 1 карты и баланс {string} 2 карты")
//    public void verifyDashboardPage() {
//        dashboardPage.firstCardBalance;
//        dashboardPage.getCardBalance(secondCardInfo);
//    }

    @Когда("когда пользователь переводит {string} рублей с карты с номером {string} на свою 1 карту с главной страницы")
    public void transferTheAmountToTheCard(String amount, DataHelper.CardInfo secondCardInfo) {
        dashboardPage = transferPage.makeValidTransfer(amount, secondCardInfo);
    }
    //когда пользователь переводит 5 000 рублей с карты с номером 5559 0000 0000 0002 на свою 1 карту с главной страницы,

    @Тогда("баланс его 1 карты из списка на главной странице должен стать {string} рублей")
    public  void actualBalanceFirstCard(String firstCardInfo){
        dashboardPage.getCardBalance(Integer.parseInt(firstCardInfo));
    }

    // тогда баланс его 1 карты из списка на главной странице должен стать 15 000 рублей.
}
