package ru.digitalleague.prerevolutionarytinder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.digitalleague.prerevolutionarytinder.api.NewspaperImageService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultNewspaperImageService implements NewspaperImageService {

    @Value("${file.path}")
    private Resource backgroundPath;
    @Value("${file.location}")
    private Path imagesPath;

    public static final String FONT_NAME = "Old Standard TT";
    public static final String IMG_FORMAT = "jpg";
    public static final int BACKGROUND_WIDTH = 500;
    public static final int HEADER_LENGTH = 16;
    public static final int MAX_BODY_FONT_SIZE = 45;
    public static final int SYMBOLS_COUNT_IN_FONTSIZE_STEP = 10;
    public static final int MAX_HEADER_FONT_SIZE = 60;
    public static final int HEADER_X = 75;
    public static final int HEADER_Y = 100;
    public static final int DRAW_HEADER_Y = 150;
    public static final double HEADER_Y_STEP = 1.2;

    private BufferedImage bufferedImage;

    @Override
    public File getImage(String description) {
        log.info("Start image creation");
        File personImage = null;
        try {
            String imageName = imagesPath.toString() + "/" + UUID.randomUUID() + "." + IMG_FORMAT;
            personImage = new File(imageName);
            bufferedImage = ImageIO.read(backgroundPath.getInputStream());
            String header = extractHeader(description);
            Font headerFont = new Font(FONT_NAME, Font.BOLD, MAX_HEADER_FONT_SIZE - header.length() / 2);

            Graphics g = bufferedImage.createGraphics();
            g.setColor(Color.BLACK);
            g.setFont(headerFont);
            g.drawString(header, HEADER_X, HEADER_Y);
            g.dispose();
            if (header.length() < description.length()) {
                String body = description.replaceFirst(header, "");
                drawBodyText(g, body);
            }

            ImageIO.write(bufferedImage, IMG_FORMAT, personImage);
            log.info("End image creation");
        } catch (
                IOException e) {
            log.error("Error Image Creation");
            log.error(e.getMessage(), e);
        }
        return personImage;
    }

    private void drawBodyText(Graphics g, String body) {

        String[] bodyWords = extractBodyWords(body);
        int bodyFontSize = calculateFontSize(body.length());
        Font descriptionFont = new Font(FONT_NAME, Font.BOLD, bodyFontSize);

        log.debug("Draw body description");
        int symbolsInLine = BACKGROUND_WIDTH / bodyFontSize * 2;
        int headerY = DRAW_HEADER_Y + bodyFontSize;
        StringBuilder line = new StringBuilder();
        for (String word : bodyWords) {
            if (line.length() + word.length() < symbolsInLine) {
                line.append(word);
                line.append(" ");
            } else {
                g = bufferedImage.getGraphics();
                g.setFont(descriptionFont);
                g.setColor(Color.DARK_GRAY);
                g.drawString(line.toString(), HEADER_X, headerY);
                headerY += bodyFontSize * HEADER_Y_STEP;
                g.dispose();
                line.delete(0, line.length());
                line.append(word).append(" ");
            }
        }
        if (line.length() > 0) {
            g = bufferedImage.getGraphics();
            g.setFont(descriptionFont);
            g.setColor(Color.DARK_GRAY);
            g.drawString(line.toString(), HEADER_X, headerY);
            g.dispose();
        }
    }

    private int calculateFontSize(int symbolsCount) {
        return MAX_BODY_FONT_SIZE - symbolsCount / SYMBOLS_COUNT_IN_FONTSIZE_STEP;
    }

    private String[] extractBodyWords(String body) {
        return body.split(" ");
    }

    private String extractHeader(String description) {
        String[] words = description.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() > HEADER_LENGTH) {
                break;
            }
            line.append(word).append(" ");
        }
        return line.toString();
    }
}
