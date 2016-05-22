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
    private JTextField filename = new JTextField(), dir = new JTextField();

    private JButton open = new JButton("Open"), save = new JButton("Save"), compress = new JButton("Compress"),
            decompress = new JButton("Decompress");

    private    BufferedImage img;
    private JPanel imagePanel;

    public FileChooserTest() {
        JPanel p = new JPanel();
        JPanel q = new JPanel();
        JPanel r = new JPanel();
        open.addActionListener(new OpenL());
        p.add(open);
        save.addActionListener(new SaveL());
        r.add(compress);
        r.add(decompress);
        q.add(r);
        q.add(save);

        add(p, BorderLayout.NORTH);
        add(q,BorderLayout.SOUTH);
//        dir.setEditable(false);
//        filename.setEditable(false);
        p = new JPanel();
        p.setLayout(new GridLayout(3, 1));
//        p.add(filename);
//        p.add(dir);
//        cp.add(p, BorderLayout.CENTER);
        compress.setActionCommand("compress");
        decompress.setActionCommand("decompress");
        compress.addActionListener(new CoreFunction());
        decompress.addActionListener(new CoreFunction());

    }

    class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            revalidate();
            JFileChooser c = new JFileChooser();
            // Demonstrate "Open" dialog:
            int rVal = c.showOpenDialog(FileChooserTest.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                try{
                    int width = 100, height = 100;
                    img = ImageIO.read(c.getSelectedFile());
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

    public static int[][] convertToPixels(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] pixel = new int[width][height];

        for(int i = 0; i < width; i++){

            for(int j = 0; j < height; j++){

                int rgb = img.getRGB(i, j);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                pixel[i][j] = (r + g + b) / 3;
            }
        }
        return pixel;
    }

 public static Double[][] finalMat = new Double[1][196];
    class CoreFunction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            int height = img.getHeight();
            int width = img.getWidth();

            FileRead read = new FileRead();
            if(command.equals("compress")){
                if(height != 28 || width != 28){
                    JOptionPane.showMessageDialog(null, "Image cannot be compressed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Image can be compressed", "No Error", JOptionPane.INFORMATION_MESSAGE);
                    int[][] convPixel = convertToPixels(img);
                    Double[][] finalPixel = convert1D(convPixel);
                    Double[][] matrix1 = new Double[784][500];

                    matrix1 = read.convertToMatrix("Theta1.txt", "theta1");
                    Double[][] midMatrix = new Double[1][500];
                    midMatrix = read.multiplyByMatrix(finalPixel, matrix1);
                    Double[][] matrix2 = new Double[500][196];
                    matrix2 = read.convertToMatrix("Theta2.txt", "theta2");

                    finalMat = read.multiplyByMatrix(midMatrix, matrix2);
//                    for(int i=0;i<finalMat.length;i++){
//                        for(int j=0;j<finalMat[0].length;j++) {
//                            System.out.println(finalMat[i][j]);
//                        }
//                    }
                    try {
                        convertBackCom(finalMat);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
            if(command.equals("decompress")){
//                if(height != 14 || width != 14){
//                    JOptionPane.showMessageDialog(null, "Image cannot be decompressed", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//                else {
//                    JOptionPane.showMessageDialog(null, "Image can be decompressed", "No Error", JOptionPane.INFORMATION_MESSAGE);
//
//                }

                Double[][] matrix3 = new Double[196][500];
                matrix3 = read.convertToMatrix("Theta3.txt", "theta3");
                Double[][] midMatrix1 = new Double[1][500];
                midMatrix1 = read.multiplyByMatrix(finalMat, matrix3);
                Double[][] matrix4 = new Double[500][784];
                matrix4 =  read.convertToMatrix("Theta4.txt", "theta4");
                Double[][] decomMat = new Double[1][784];
                decomMat= read.multiplyByMatrix(midMatrix1, matrix4);
                try {
                    convertBackDecom(decomMat);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void convertBackDecom(Double[][] decomMat) throws IOException{
        Double[][] decom = new Double[28][28];
        int index =0;
        for(int i=0;i<28;i++){
            for(int j=0;j<28;j++){
                decom[i][j] = 255* decomMat[0][index];
                index+=1;
//                System.out.print(decom[i][j]);
//                System.out.print(" ");
            }
//            System.out.println("\n");
        }
        returnImage(decom);

    }

    public static void convertBackCom(Double[][] finalMat) throws IOException {
    Double[][] compressed = new Double[14][14];
        int index =0;
        for(int i=0;i<14;i++){
            for(int j=0;j<14;j++){
               compressed[i][j] = finalMat[0][index];
                index+=1;
//                System.out.print(compressed[i][j]);
//                System.out.print(" ");
            }
//            System.out.println("\n");
        }
//        returnImage(compressed);
    }


    public static void returnImage(Double[][] compressed) throws IOException {
        BufferedImage b = new BufferedImage(compressed.length, compressed[0].length, 3);
        Graphics c = b.getGraphics();


        PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
        for(int i=0; i< compressed.length; i++){
            for (int j=0; j<compressed[0].length; j++){
                c.drawString(String.valueOf(compressed[i][j]), i, j);
                writer.print(compressed[i][j] + " \t");
            }
            writer.println();
        }

        ImageIO.write(b, "jpg", new File("CustomImage.jpg"));
        System.out.println("end");
    }

    public static Double[][] convert1D(int[][] convPixel){
        int elementsNumber = 0;
        for (int i = 0; i < convPixel.length; i++) {
            elementsNumber += convPixel[i].length;
        }

        int[] newPixel = new int[elementsNumber];
        int j = 0;
        for (int i = 0; i < convPixel.length; i++) {
            System.arraycopy(convPixel[i], 0, newPixel, j, convPixel[i].length);
            j += convPixel[i].length;
        }
        Double[][] newPxF = new Double[1][elementsNumber];
        for(int i=0;i<elementsNumber;i++){
            newPxF[0][i] = (double) newPixel[i];
//            System.out.println(newPxF[0][i]);
        }
        return newPxF;

    }

    class SaveL implements ActionListener {
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

    public static void main(String[] args) {
        run(new FileChooserTest(), 500, 400);
    }

    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    public static void printArray(int[][] a){

        for(int i = 0; i < a.length - 1; i++){

            for(int j = 0; j < a[i].length - 1; j++){

                System.out.printf(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static final boolean isGreyscaleImage(PixelGrabber pg) {
        return pg.getPixels() instanceof byte[];
    }
}
