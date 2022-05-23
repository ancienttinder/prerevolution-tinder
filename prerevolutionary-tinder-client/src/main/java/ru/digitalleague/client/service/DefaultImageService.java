package ru.digitalleague.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.digitalleague.client.api.ImageService;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.io.*;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultImageService implements ImageService {

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

    private BufferedImage bufferedImage;

    @Override
    public File getImage(String description) {
        log.info("Start image creation");
        File personImage = null;
        try {
            bufferedImage = ImageIO.read(backgroundPath.getInputStream());
            String header = extractHeader(description);
            Font headerFont = new Font(FONT_NAME, Font.BOLD, MAX_HEADER_FONT_SIZE - header.length() / 2);

            Graphics g = bufferedImage.createGraphics();
            g.setColor(Color.BLACK);
            g.setFont(headerFont);
            g.drawString(header, 75, 100);
            g.dispose();
            if (header.length() < description.length()) {
                String body = description.replaceFirst(header, "");
                drawBodyText(g, body);
            }

            String imageName = imagesPath.toString() + UUID.randomUUID() + IMG_FORMAT;
            personImage = new File(imageName);
            ImageIO.write(bufferedImage, IMG_FORMAT, personImage);
            log.info("End image creation");
        } catch (IOException e) {
            log.error("Error Image Creation");
            log.error(Arrays.toString(e.getStackTrace()));
        }
        return personImage;
    }

    private void drawBodyText(Graphics g, String body) {

        String[] bodyWords = extractBodyWords(body);
        int bodyFontSize = calculateFontSize(body.length());
        Font descriptionFont = new Font(FONT_NAME, Font.BOLD, bodyFontSize);

        log.debug("Draw body description");
        int symbolsInLine = BACKGROUND_WIDTH / bodyFontSize * 2;
        int headerY = 150 + bodyFontSize;
        StringBuilder line = new StringBuilder();
        for (String word : bodyWords) {
            if (line.length() + word.length() < symbolsInLine) {
                line.append(word);
                line.append(" ");
            } else {
                g = bufferedImage.getGraphics();
                g.setFont(descriptionFont);
                g.setColor(Color.DARK_GRAY);
                g.drawString(line.toString(), 75, headerY);
                headerY += bodyFontSize * 1.2;
                g.dispose();
                line.delete(0, line.length());
                line.append(word).append(" ");
            }
        }
        if (line.length() > 0) {
            g = bufferedImage.getGraphics();
            g.setFont(descriptionFont);
            g.setColor(Color.DARK_GRAY);
            g.drawString(line.toString(), 75, headerY);
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