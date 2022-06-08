package ru.digitalleague.prerevolutionarytinder.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.digitalleague.prerevolutionarytinder.api.TranslateSlavonicService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TranslateSlavonicServiceTest {

    @Autowired
    private TranslateSlavonicService translateSlavonicService;

    @Test
    void checkTranslate() {
        String rus = "Агафья несла снедь на синий стол";
        String oldSlavonic = "Агаѳья несла снѣдь на синiй столъ";
        assertEquals(oldSlavonic,translateSlavonicService.translateRusToSlavonic(rus));
    }

}