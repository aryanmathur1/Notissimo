package Notissimo.views.CustomElements;

import javax.swing.*;
import java.awt.*;

public class HintTextField extends JTextField {
    private String hint;

    public HintTextField(String hint) {
        this.hint = hint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !isFocusOwner()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(255, 255, 255, 95));
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.drawString(hint, getInsets().left + 5, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hint Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        HintTextField textField = new HintTextField("Enter your name...");
        textField.setColumns(20);

        frame.add(textField);
        frame.setVisible(true);
    }
}
