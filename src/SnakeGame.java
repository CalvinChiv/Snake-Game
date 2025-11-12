import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile {
        int x;
        int y;
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    private final int TILESIZE;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //Game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        TILESIZE = 25;

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(0, 0);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 1;

        gameOver = false;
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Grid
//        for(int i = 0; i < boardWidth / TILESIZE; i++) {
//            g.drawLine(i * TILESIZE, 0, i * TILESIZE, boardHeight);
//            g.drawLine(0, i * TILESIZE, boardWidth, i * TILESIZE);
//        }


        //Food
        g.setColor(Color.RED);
//        g.fillRect(food.x * TILESIZE, food.y * TILESIZE, TILESIZE, TILESIZE);
        g.fill3DRect(food.x * TILESIZE, food.y * TILESIZE, TILESIZE, TILESIZE, true);


        //Snake
        g.setColor(Color.GREEN);
//        g.fillRect(snakeHead.x * TILESIZE, snakeHead.y * TILESIZE, TILESIZE, TILESIZE);
        g.fill3DRect(snakeHead.x * TILESIZE, snakeHead.y * TILESIZE, TILESIZE, TILESIZE, true);
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
//            g.fillRect(snakePart.x * TILESIZE, snakePart.y * TILESIZE, TILESIZE, TILESIZE);
            g.fill3DRect(snakePart.x * TILESIZE, snakePart.y * TILESIZE, TILESIZE, TILESIZE, true);

        }

        //Score
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        if (gameOver) {
            g.setColor(Color.ORANGE);
            g.drawString("GAME OVER: " + String.valueOf(snakeBody.size()), TILESIZE - 20, TILESIZE);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), TILESIZE - 20, TILESIZE);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth/TILESIZE); //600/25 = 24
        food.y = random.nextInt(boardHeight/TILESIZE);
    }

    public void move() {
        //Eat food
        if(collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake Body
        for(int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if(i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //gameOver conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //collide with snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * TILESIZE < 0 || snakeHead.x * TILESIZE > boardWidth ||
            snakeHead.y * TILESIZE < 0 || snakeHead.y * TILESIZE > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Tile tile, Tile tile2) {
        return tile.x == tile2.x && tile.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && velocityY != 1 ) {
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_A && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_S && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_D && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    //NOT NEEDED
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}