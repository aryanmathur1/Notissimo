package Notissimo.views.gpaCalculator;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RoundedButtonUI extends BasicButtonUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
        c.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();

        // Antialiasing for smoother shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Button background
        if (b.getModel().isPressed()) {
            g2.setColor(b.getBackground().darker());
        } else if (b.getModel().isRollover()) {
            g2.setColor(b.getBackground().brighter());
        } else {
            g2.setColor(b.getBackground());
        }

        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), 20, 20);

        // Button label (text)
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = b.getBounds();
        String text = b.getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        g2.setColor(b.getForeground());
        g2.setFont(b.getFont());
        g2.drawString(
                text,
                (r.width - textWidth) / 2,
                (r.height + textHeight / 2) / 2
        );

        g2.dispose();
    }
}
