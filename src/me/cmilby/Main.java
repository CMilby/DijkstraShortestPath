package me.cmilby;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
    }

    private static void createAndShowGUI() {
		JFrame frame = new JFrame("Shortest Path");
		PathPanel path = new PathPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.add(path);

		frame.getContentPane().setPreferredSize(new Dimension(600, 600));
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
    }
}
