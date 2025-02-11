package Notissimo.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class NotesView extends JPanel {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;



    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("HelloGraphics");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(0, 0);

        //tells the java program to exit when the graphics window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //put the panel on the frame and make it visible
        frame.setContentPane(new NotesView());
        frame.setVisible(true);

    }

}
