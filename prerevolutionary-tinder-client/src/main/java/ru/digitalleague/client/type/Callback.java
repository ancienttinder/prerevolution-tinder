package ru.digitalleague.client.type;

public enum Callback {
    MADAM("callback.madam"),
    SIR("callback.sir"),
    SIR_SEARCH("callback.sir.search"),
    MADAM_SEARCH("callback.madam.search"),
    ALL_SEARCH("callback.all.search"),
    YES("callback.yes"),
    NO("callback.no"),
    LIKE_HISTORY("callback.like.history"),
    FIND_SUITABLE_PERSON("callback.find.suitable.person"),
    PREVIOUS("callback.previous"),
    NEXT("callback.next"),
    PREV_PERSON("callback.person.previous"),
    NEXT_PERSON("callback.person.next"),
    MENU("callback.menu"),
    EDIT("callback.edit");

    private String messageId;

    Callback(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
