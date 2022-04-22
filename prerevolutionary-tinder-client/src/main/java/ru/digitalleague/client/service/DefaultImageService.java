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
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultImageService implements ImageService {

    @Value("${file.path}")
    private Resource backgroundPath;
    @Value("${file.location}")
    private Path imagesPath;

    private BufferedImage bufferedImage;

    @Override
    public File getImage(String description) {
        File personImage = null;
        try {
            bufferedImage = ImageIO.read(backgroundPath.getInputStream());
            Graphics2D g = bufferedImage.createGraphics();
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", Font.BOLD, 52));
            //int x = g.getFontMetrics(new Font("Courier New", Font.BOLD, 52));
            g.drawString(description, (bufferedImage.getWidth() / 2), 50);
            String imageName = imagesPath.toString() + UUID.randomUUID() + ".jpg";
            personImage = new File(imageName);
            ImageIO.write(bufferedImage, "jpg", personImage);
        } catch (IOException e) {
            //todo сделать exception
            //log.error("Image Creation Error");
        }
        return personImage;
    }
}