package main;

import java.awt.Dimension;

import javax.swing.JFrame;

import display.MainWindow;

public class Main {

	public static void main(String s[]) {
		MainWindow mw = new MainWindow();
		mw.setTitle("FindString (BETA VERSION)");
		mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mw.setPreferredSize(new Dimension(800, 600));
		mw.setLocationRelativeTo(null);
		mw.pack();
		mw.setLocation(0, 0);
		mw.setVisible(true);
	}
}
