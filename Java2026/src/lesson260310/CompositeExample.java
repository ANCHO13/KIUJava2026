package lesson260310;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CompositeExample {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Composite Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel(); // composite
		JPanel nestedPanel = new JPanel(); // composite

		JButton button1 = new JButton("OK"); // leaf
		JButton button2 = new JButton("Cancel"); // leaf

		button1.addActionListener(e -> System.out.println("clicked!"));

        nestedPanel.add(button1);
        nestedPanel.add(button2);

        mainPanel.add(nestedPanel);

        frame.add(mainPanel);

        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}