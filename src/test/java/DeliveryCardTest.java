import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSuccessBookDelivery(){

        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE));
        $("[data-test-id='date'] input")
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Кинзябаев Данис");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();

        $("[data-test-id='notification']")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text(planDate));
    }
}
