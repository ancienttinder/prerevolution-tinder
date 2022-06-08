package ru.digitalleague.prerevolutionarytinder.type;

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
}
