package Notissimo.views.CustomElements;

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton {

    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 0;

    public MyButton() {
        setContentAreaFilled(false);
    }

    public boolean isOver() {
        return over;
    }

    public Color getColor() {
        return color;
    }

    public Color getColorOver() {
        return colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        

        super.paintComponent(g);

    }
}
