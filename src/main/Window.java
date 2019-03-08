package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import interfaces.Menu;

public class Window extends Canvas {
    private static final long serialVersionUID = -4810618286807932601L;

    private Game game;

    public Window(int width, int height, String title, Game game, Menu menu) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    menu.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
}
