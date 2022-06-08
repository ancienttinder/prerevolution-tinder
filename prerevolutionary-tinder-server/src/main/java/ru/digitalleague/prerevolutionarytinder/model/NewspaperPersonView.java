package ru.digitalleague.prerevolutionarytinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewspaperPersonView {
    private File newspaperImage;
    private String caption;
    private Long personId;
}
