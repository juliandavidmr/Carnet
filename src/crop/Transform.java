/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crop;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
public class Transform {

    public static Data prepare(final File f, Point center, boolean save, File dest) throws IOException {
        BufferedImage img;
        //read image
        try {
            img = ImageIO.read(f);

            int width = img.getWidth();
            int height = img.getHeight();

            if (center == null) {
                center = getCenter(width, height);
            }

            // System.out.println("W:" + width + "\tH:" + height);

            Circle circle = new Circle(center, getRadius(width, height));
            Image img2 = processing(img, new java.awt.Color(200, 200, 200), circle);

            //write image
            if (save) {
                try {                    
                    ImageIO.write(toBufferedImage(img2), "png", dest);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
            
            return new Data(img, img2, center);
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String args[]) throws IOException {
        BufferedImage img;
        File f;

        //read image
        try {
            f = new File("C:\\Users\\jul.mora\\Documents\\NetBeansProjects\\Crop\\src\\images\\yhully.png");
            img = ImageIO.read(f);

            int width = img.getWidth();
            int height = img.getHeight();

            System.out.println("W:" + width + "\tH:" + height);

            Circle circle = new Circle(getCenter(width, height), getRadius(width, height));
            Image img2 = processing(img, new java.awt.Color(200, 200, 200), circle);

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
    }

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
     * Obtener centro de un cuadrado
     *
     * @param width
     * @param height
     * @return
     */
    public static Point getCenter(int width, int height) {
        return new Point(width / 2, height / 2);
    }

    /**
     * Calcular el radio aceptable para recorte
     *
     * @param width
     * @param height
     * @return
     */
    public static int getRadius(int width, int height) {
        Point p = getCenter(width, height);
        return p.x >= p.y ? p.y : p.x;
    }

    /**
     * Aplica transparencia en imagen. Aplica redondeo a la foto
     *
     * @param im
     * @param min_color Color minimo a detectar. Todos los colores por encima de
     * este seran eliminados.
     * @return
     */
    public static Image processing(Image im, final java.awt.Color min_color, Circle circle) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = min_color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                java.awt.Color temp = new java.awt.Color(rgb);
                if ((temp.getRed() >= min_color.getRed()
                        && temp.getBlue() >= min_color.getBlue()
                        && temp.getGreen() >= min_color.getGreen())
                        || !circle.contains(new Point(x, y))) {
                    return 0x00FFFFFF & rgb;
                }
                return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}
