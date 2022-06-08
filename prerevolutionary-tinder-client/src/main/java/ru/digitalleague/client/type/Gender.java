package ru.digitalleague.client.type;

public enum Gender {
    MADAM("tinder.gender.madam"),
    SIR("tinder.gender.sir"),
    ALL("tinder.gender.all");

    String messageId;

    Gender(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public static Gender getFromSearchTerm(Callback callback) {
        switch (callback) {
            case MADAM_SEARCH:
                return MADAM;
            case SIR_SEARCH:
                return SIR;
            default:
                return ALL;
        }
    }
}
