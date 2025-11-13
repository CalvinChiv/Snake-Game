import javax.swing.*;
public class App {
    public static void main(String[] args) {
        int boardSize = 600;

        JFrame frame = new JFrame("Snake");
        initializeFrame(frame, boardSize);

        SnakeGame snakeGame = new SnakeGame(boardSize, boardSize);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }

    private static void initializeFrame(JFrame frame, int boardSize) {
        frame.setVisible(true);
        frame.setSize(boardSize, boardSize);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}