package ru.digitalleague.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.prerevolutionarytinder.api.TranslateSlavonicService;

import javax.annotation.PostConstruct;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

@Component
@RequiredArgsConstructor
public class DefaultTranslateSlavonicService implements TranslateSlavonicService {

    private final HashMap<String, String> replaceSlavonic = new HashMap<>();

    @PostConstruct
    private void init() {
        replaceSlavonic.put("Агафья", "Агаѳья");
        replaceSlavonic.put("Анфим", "Анѳим");
        replaceSlavonic.put("Афанасий", "Аѳанасий");
        replaceSlavonic.put("Афина", "Аѳина");
        replaceSlavonic.put("Варфоломей", "Варѳоломей");
        replaceSlavonic.put("Голиаф", "Голиаѳ");
        replaceSlavonic.put("Евфимий", "Евѳимий");
        replaceSlavonic.put("Марфа", "Марѳа");
        replaceSlavonic.put("Матфей", "Матѳей");
        replaceSlavonic.put("Мефодий", "Меѳодий");
        replaceSlavonic.put("Нафанаил", "Наѳанаил");
        replaceSlavonic.put("Парфенон", "Парѳенон");
        replaceSlavonic.put("Пифагор", "Пиѳагор");
        replaceSlavonic.put("Руфь", "Руѳь");
        replaceSlavonic.put("Саваоф", "Саваоѳъ");
        replaceSlavonic.put("Тимофей", "Тимоѳей");
        replaceSlavonic.put("Эсфирь", "Эсѳирь");
        replaceSlavonic.put("Иудифь", "Іудиѳь");
        replaceSlavonic.put("Фаддей", "Ѳаддей");
        replaceSlavonic.put("Фекла", "Ѳекла");
        replaceSlavonic.put("Фемида", "Ѳемида");
        replaceSlavonic.put("Фемистокл", "Ѳемистоклъ");
        replaceSlavonic.put("Феодор", "Ѳеодоръ");
        replaceSlavonic.put("Фёдор", "Ѳёдоръ");
        replaceSlavonic.put("Федя", "Ѳедя");
        replaceSlavonic.put("Феодосий", "Ѳеодосій");
        replaceSlavonic.put("Федосий", "Ѳедосій");
        replaceSlavonic.put("Феодосия", "Ѳеодосія");
        replaceSlavonic.put("Феодот", "Ѳеодотъ");
        replaceSlavonic.put("Федот", "Ѳедотъ");
        replaceSlavonic.put("Феофан", "Ѳеофанъ");
        replaceSlavonic.put("Феофил", "Ѳеофилъ");
        replaceSlavonic.put("Ферапонт", "Ѳерапонтъ");
        replaceSlavonic.put("Фома", "Ѳома");
        replaceSlavonic.put("Фоминична", "Ѳоминична");

        replaceSlavonic.put("еда", "ѣда");
        replaceSlavonic.put("ем", "ѣм");
        replaceSlavonic.put("есть", "ѣсть");
        replaceSlavonic.put("обед", "обѣд");
        replaceSlavonic.put("обедня", "обѣдня");
        replaceSlavonic.put("сыроежка", "сыроѣжка");
        replaceSlavonic.put("сыроега", "сыроѣга");
        replaceSlavonic.put("медведь", "медвѣдь");
        replaceSlavonic.put("снедь", "снѣдь");
        replaceSlavonic.put("едкий ехать", "ѣдкий ѣхать");
        replaceSlavonic.put("езда", "ѣзда");
        replaceSlavonic.put("уезд", "уѣзд");
        replaceSlavonic.put("еду", "ѣду");
        replaceSlavonic.put("ездить", "ѣздить");
        replaceSlavonic.put("поезд", "поѣзд");
        replaceSlavonic.put("бег", "бѣг");
        replaceSlavonic.put("беглец", "бѣглец");
        replaceSlavonic.put("беженец", "бѣженец");
        replaceSlavonic.put("забегаловка", "забѣгаловка");
        replaceSlavonic.put("избегать", "избѣгать");
        replaceSlavonic.put("избежать", "избѣжать");
        replaceSlavonic.put("набег", "набѣг");
        replaceSlavonic.put("перебежчик", "перебѣжчик");
        replaceSlavonic.put("пробег", "пробѣг");
        replaceSlavonic.put("разбег", "разбѣг");
        replaceSlavonic.put("убежище", "убѣжище");
        replaceSlavonic.put("центробежная сила", "центробѣжная сила");
        replaceSlavonic.put("беда", "бѣда");
        replaceSlavonic.put("бедный", "бѣдный");
        replaceSlavonic.put("победить", "побѣдить");
        replaceSlavonic.put("убедить", "убѣдить");
        replaceSlavonic.put("убеждение", "убѣждение");
        replaceSlavonic.put("белый", "бѣлый");
        replaceSlavonic.put("бельё", "бѣльё");
        replaceSlavonic.put("белка", "бѣлка");
        replaceSlavonic.put("бельмо", "бѣльмо");
        replaceSlavonic.put("белуга", "бѣлуга");
        replaceSlavonic.put("бес", "бѣс");
        replaceSlavonic.put("бешеный", "бѣшеный");
        replaceSlavonic.put("обет", "обѣт");
        replaceSlavonic.put("обещать", "обѣщать");
        replaceSlavonic.put("веять", "вѣять");
        replaceSlavonic.put("веер", "вѣер");
        replaceSlavonic.put("ветер", "вѣтер");
        replaceSlavonic.put("ветвь", "вѣтвь");
        replaceSlavonic.put("веха", "вѣха");
        replaceSlavonic.put("ведать", "вѣдать");
        replaceSlavonic.put("веди", "вѣди");
        replaceSlavonic.put("весть", "вѣсть");
        replaceSlavonic.put("повесть", "повѣсть");
        replaceSlavonic.put("ведение", "вѣ́дѣние");
        replaceSlavonic.put("вежливый", "вѣжливый");
        replaceSlavonic.put("невежда", "невѣжда");
        replaceSlavonic.put("вежды", "вѣжды");
        replaceSlavonic.put("вежа", "вѣжа");
        replaceSlavonic.put("век", "вѣк");
        replaceSlavonic.put("вечный", "вѣчный");
        replaceSlavonic.put("увечить", "увѣчить");
        replaceSlavonic.put("веко", "вѣко");
        replaceSlavonic.put("венок", "вѣнок");
        replaceSlavonic.put("венец", "вѣнец");
        replaceSlavonic.put("вениквенок", "вѣниквѣнок");
        replaceSlavonic.put("веник", "вѣник");
        replaceSlavonic.put("вено", "вѣно");
        replaceSlavonic.put("вера", "вѣра");
        replaceSlavonic.put("вероятно", "вѣроятно");
        replaceSlavonic.put("суеверие", "суевѣрие");
        replaceSlavonic.put("вес", "вѣс");
        replaceSlavonic.put("вешать", "вѣшать");
        replaceSlavonic.put("повеса", "повѣса");
        replaceSlavonic.put("равновесие", "равновѣсие");
        replaceSlavonic.put("звезда", "звѣзда");
        replaceSlavonic.put("зверь", "звѣрь");
        replaceSlavonic.put("невеста", "невѣста");
        replaceSlavonic.put("ответ", "отвѣт");
        replaceSlavonic.put("совет", "совѣт");
        replaceSlavonic.put("привет", "привѣт");
        replaceSlavonic.put("завет", "завѣт");
        replaceSlavonic.put("вещать", "вѣщать");
        replaceSlavonic.put("вече", "вѣче");
        replaceSlavonic.put("свежий", "свѣжий");
        replaceSlavonic.put("свежеть", "свѣжѣть");
        replaceSlavonic.put("свет", "свѣт");
        replaceSlavonic.put("свеча", "свѣча");
        replaceSlavonic.put("просвещение", "просвѣщение");
        replaceSlavonic.put("светец", "свѣтец");
        replaceSlavonic.put("светёлка", "свѣтёлка");
        replaceSlavonic.put("Светлана", "Свѣтлана");
        replaceSlavonic.put("цвет", "цвѣт");
        replaceSlavonic.put("цветы", "цвѣты");
        replaceSlavonic.put("цвести", "цвѣсти");
        replaceSlavonic.put("человек", "человѣк");
        replaceSlavonic.put("человеческий", "человѣческий");
    }

    @Override
    public String translate(String rusString) {
        final String[] keys = replaceSlavonic.keySet().toArray(new String[0]);
        final String[] values = replaceSlavonic.values().toArray(new String[0]);
        return StringUtils.replaceEach(rusString, keys, values);
    }
}
