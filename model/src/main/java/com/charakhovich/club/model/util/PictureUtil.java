package com.charakhovich.club.model.util;

import com.mortennobel.imagescaling.ResampleOp;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * The Image modification
 *
 * @author Katerina Charakhovich
 * @version 1.0
 */
public class PictureUtil {
    private static final Logger logger = LogManager.getLogger(PictureUtil.class);
    private static final int IMAGE_WIDTH_MAIN = 300;
    private static final int IMAGE_WIDTH_ADDITIONAL = 600;
    private static final String IMAGE_TYPE = "jpg";

    /**
     * Modification image to string base 64 string.
     *
     * @param inputStream the input stream
     * @return the string
     */
    public static String imageToBase64(InputStream inputStream) {

        String resultImageBase64 = null;
        if (inputStream != null) {
            try {
                byte[] image = inputStream.readAllBytes();
                resultImageBase64 = Base64.getEncoder().encodeToString(image);
            } catch (IOException e) {
                logger.log(Level.ERROR, "Error during decrease image", e);
            }
        }
        return resultImageBase64;
    }

    /**
     * Resize image
     *
     * @param image      the inputStream
     * @param widthImage the int
     * @return the InputStream
     */
    public static InputStream resizeImage(InputStream image, int widthImage) {
        InputStream newImage = null;
        try {
            BufferedImage imageResize = ImageIO.read(image);
            int width = imageResize.getWidth();
            int height = imageResize.getHeight();
            float ratio = (float) width / (float) height;
            if (width > widthImage) {
                width = widthImage;
                height = (int) (widthImage / ratio);
            }
            ResampleOp reOp = new ResampleOp(width, height);
            BufferedImage newImg = reOp.filter(imageResize, null);

            ByteArrayOutputStream imgByte = new ByteArrayOutputStream();

            ImageIO.write(newImg, IMAGE_TYPE, imgByte);

            newImage = new ByteArrayInputStream(imgByte.toByteArray());

        } catch (IOException e) {
            logger.log(Level.ERROR, "Error when reducing the image", e);
        }
        return newImage;
    }
}
