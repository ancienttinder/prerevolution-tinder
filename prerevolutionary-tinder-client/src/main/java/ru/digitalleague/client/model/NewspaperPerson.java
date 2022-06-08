package ru.digitalleague.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewspaperPerson {
    private File newspaperImage;

    private String caption;

    private Long personId;
}
