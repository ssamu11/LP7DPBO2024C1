import javax.swing.*;

public class App {
    public static void main(String[] args) {
        showStartForm();
    }

    private static void showStartForm() {
        JFrame startFrame = new JFrame("Start Game");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(300, 200);
        startFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            startFrame.dispose();
            startGame();
        });

        panel.add(startButton);
        startFrame.add(panel);
        startFrame.setVisible(true);
    }

    private static void startGame() {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}
