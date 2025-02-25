package Notissimo.views.CustomElements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyButton extends JButton {

    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 0;

    public MyButton() {
        // init color
        color = Color.WHITE;
        colorOver = new Color(183, 160, 250);
        colorClick = new Color(155, 144, 184);
        borderColor = new Color(60, 30, 136);

        setContentAreaFilled(false);

        // add event mouse
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(colorOver);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(color);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(colorClick);
                over = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (over) {
                    setBackground(colorOver);
                } else {
                    setBackground(color);
                }
            }

        });
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
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // paint border
        g2d.setColor(borderColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2d.setColor(getBackground());
        // border set 2 pixels
        g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);

        super.paintComponent(g);

    }
}
