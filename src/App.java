import javax.swing.*;
public class App {
    public static void main(String[] args) {
        int boardSize = 600;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardSize, boardSize);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardSize, boardSize);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}