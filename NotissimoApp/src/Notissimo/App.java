package Notissimo;

import Notissimo.views.HomeScreen;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
         SwingUtilities.invokeLater(HomeScreen::new);
    }
}
