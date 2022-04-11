package ru.digitalleague.prerevolutionarytinder.client;

public class Dto {
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Dto() {
    }

    public Dto(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "file='" + file + '\'' +
                '}';
    }
}
