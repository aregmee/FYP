package com.fyp;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.swing.*;

public class FileChooserTest extends JFrame {
    //private JTextField filename = new JTextField(), dir = new JTextField();
    private BufferedImage img;
    private JPanel imagePanel;
    private JButton compress = new JButton("Compress")
            , decompress = new JButton("Decompress");

    public FileChooserTest() {

        JButton open = new JButton("Open")
                , save = new JButton("Save");

        JPanel openPanel = new JPanel();
        JPanel savePanel = new JPanel();
        JPanel compressPanel = new JPanel();
        open.addActionListener(new OpenL());
        openPanel.add(open);
        save.addActionListener(new SaveL());
        compressPanel.add(compress);
        compressPanel.add(decompress);
        savePanel.add(compressPanel);
        savePanel.add(save);

        add(openPanel, BorderLayout.NORTH);
        add(savePanel,BorderLayout.SOUTH);
        compress.setActionCommand("compress");
        decompress.setActionCommand("decompress");
    }

    private class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            revalidate();
            JFileChooser c = new JFileChooser();
            // Demonstrate "Open" dialog:
            int rVal = c.showOpenDialog(FileChooserTest.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                try{
                    int width = 100, height = 100;
                    img = ImageIO.read(c.getSelectedFile());
                    compress.addActionListener(new CoreFunction(img));
                    decompress.addActionListener(new CoreFunction(img));
                    Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    JLabel picLabel = new JLabel(new ImageIcon(dimg));
                    imagePanel = new JPanel();
                    imagePanel.add(picLabel);
                    add(imagePanel, BorderLayout.CENTER);
                }catch(Exception io){
                    io.printStackTrace();
                }
                revalidate();
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
//                filename.setText("You pressed cancel");
//                dir.setText("");
            }
        }
    }

    private class SaveL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(FileChooserTest.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
//                filename.setText(c.getSelectedFile().getName());
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
//                filename.setText("You pressed cancel");
//                dir.setText("");
            }
        }
    }
}
