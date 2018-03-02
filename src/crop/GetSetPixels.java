/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crop;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author jul.mora
 */
public class GetSetPixels {

    public static void main(String args[]) throws IOException {
        BufferedImage img = null;
        File f = null;

        //read image
        try {
            f = new File("C:\\Users\\jul.mora\\Documents\\NetBeansProjects\\Crop\\src\\images\\yhully.png");
            img = ImageIO.read(f);

            int width = img.getWidth();
            int height = img.getHeight();

            System.out.println("W:" + width + "\tH:" + height);

            Image img2 = makeColorTransparent2(img, new java.awt.Color(200, 200, 200));

            //write image
            try {
                f = new File("C:\\Users\\jul.mora\\Documents\\NetBeansProjects\\Crop\\src\\images\\paisa_sinfondo.png");
                ImageIO.write(toBufferedImage(img2), "png", f);
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        // some code goes here...
    }//main() ends here

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    /**
     *
     * @param im
     * @param min_color
     * @return
     */
    public static Image makeColorTransparent2(Image im, final java.awt.Color min_color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = min_color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                java.awt.Color temp = new java.awt.Color(rgb);
                if (temp.getRed() >= min_color.getRed()
                        && temp.getBlue() >= min_color.getBlue()
                        && temp.getGreen() >= min_color.getGreen()) {
                    return 0x00FFFFFF & rgb;
                }
                return rgb;
//                if ((rgb | 0xFF000000) == markerRGB) {
//                    // Mark the alpha bits as zero - transparent
//                    return 0x00FFFFFF & rgb;
//                } else {
//                    // nothing to do
//                    return rgb;
//                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static Image makeColorTransparent(Image im, final java.awt.Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}//class ends here
