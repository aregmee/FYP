package com.fyp;

import javax.swing.*;

/**
 * Created by asim on 5/22/16.
 */
public class App {

    public static void main(String[] args) {
        run(new FileChooserTest(), 500, 400);
    }

    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
