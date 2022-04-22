package ru.digitalleague.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.digitalleague.client.api.FieldProvider;
import ru.digitalleague.client.type.Callback;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class DefaultFieldProvider implements FieldProvider {

    private final MessageService messageService;

    private final HashMap<Callback, String> callbackFieldMap = new HashMap<>();

    @PostConstruct
    private void init() {
        callbackFieldMap.put(Callback.MADAM, messageService.getMessage("callback.madam"));
        callbackFieldMap.put(Callback.SIR, messageService.getMessage("callback.sir"));
        callbackFieldMap.put(Callback.MADAM_SEARCH, messageService.getMessage("callback.madam.search"));
        callbackFieldMap.put(Callback.SIR_SEARCH, messageService.getMessage("callback.sir.search"));
        callbackFieldMap.put(Callback.ALL_SEARCH, messageService.getMessage("callback.all.search"));
        callbackFieldMap.put(Callback.YES, messageService.getMessage("callback.yes"));
        callbackFieldMap.put(Callback.NO, messageService.getMessage("callback.no"));
        callbackFieldMap.put(Callback.LIKE_HISTORY, messageService.getMessage("callback.like.history"));
        callbackFieldMap.put(Callback.FIND_SUITABLE_PERSON, messageService.getMessage("callback.find.suitable.person"));
        callbackFieldMap.put(Callback.PREVIOUS, messageService.getMessage("callback.previous"));
        callbackFieldMap.put(Callback.NEXT, messageService.getMessage("callback.next"));
        callbackFieldMap.put(Callback.PREV_PERSON, messageService.getMessage("callback.person.previous"));
        callbackFieldMap.put(Callback.NEXT_PERSON, messageService.getMessage("callback.person.next"));
        callbackFieldMap.put(Callback.MENU, messageService.getMessage("callback.menu"));
        callbackFieldMap.put(Callback.EDIT, messageService.getMessage("callback.edit"));
    }

    @Override
    public String field(Callback callback) {
        return callbackFieldMap.get(callback);
    }
}
