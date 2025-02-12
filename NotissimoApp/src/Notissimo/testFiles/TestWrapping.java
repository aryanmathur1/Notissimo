package Notissimo.testFiles;

import javax.swing.*;

public class TestWrapping extends JFrame  {
    public TestWrapping() {
        setSize(10,100);
        setLocation(500,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel jp = new JPanel();
        String labelText = "ThisIsJustOneBigWordForTestingJLabel";
        labelText = labelText.replaceAll(".{1,12}","\\<br\\>$0").substring(4);
        jp.add(new JLabel("<html>"+labelText+"</html>"));
        getContentPane().add(jp);
    }

    public static void main(String args[]) {
        new TestWrapping().setVisible(true);
    }
}