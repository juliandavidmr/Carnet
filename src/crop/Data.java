/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crop;

import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author jul.mora
 */
public class Data {

    Image input, output;
    Point center;
    int radius;

    public Data(Image input, Image output, Point center, int radius) {
        this.input = input;
        this.output = output;
        this.center = center;
        this.radius = radius;
    }

}
