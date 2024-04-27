import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;
    int playerStartPosX;
    int playerStartPosY;
    int playerWidth;
    int playerHeight;
    Player player;
    int pipeStartPosX;
    int pipeStartPosY;
    int pipeWidth;
    int pipeHeight;
    ArrayList<Pipe> pipes;
    Timer gameloop;
    Timer pipesCooldown;
    int gravity;

    JLabel scoreLabel;
    int score;

    public FlappyBird() {
        this.playerStartPosX = this.frameWidth / 8;
        this.playerStartPosY = this.frameHeight / 2;
        this.playerWidth = 34;
        this.playerHeight = 24;
        this.pipeStartPosX = this.frameWidth;
        this.pipeStartPosY = 0;
        this.pipeWidth = 64;
        this.pipeHeight = 512;
        this.gravity = 1;
        this.setPreferredSize(new Dimension(this.frameWidth, this.frameHeight));
        this.setFocusable(true);
        this.addKeyListener(this);
        this.backgroundImage = new ImageIcon(this.getClass().getResource("Assets/background.png")).getImage();
        this.birdImage = new ImageIcon(this.getClass().getResource("Assets/bird.png")).getImage();
        this.lowerPipeImage = new ImageIcon(this.getClass().getResource("Assets/lowerPipe.png")).getImage();
        this.upperPipeImage = new ImageIcon(this.getClass().getResource("Assets/upperPipe.png")).getImage();
        this.player = new Player(this.playerStartPosX, this.playerStartPosY, this.playerWidth, this.playerHeight, this.birdImage);
        this.pipes = new ArrayList<>();
        this.pipesCooldown = new Timer(5000, e -> placePipes());
        this.pipesCooldown.start();
        this.gameloop = new Timer(16, this);
        this.gameloop.start();

        // Inisialisasi skor
        score = 0;
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.WHITE);
        this.add(scoreLabel);
        scoreLabel.setBounds(10, 10, 100, 30);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, this.frameWidth, this.frameHeight, null);
        g.drawImage(this.player.getImage(), this.player.getPosX(), this.player.getPosY(), this.player.getWidth(), this.player.getHeight(), null);

        for (Pipe pipe : this.pipes) {
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        scoreLabel.setText("Score: " + score);
    }

    public void placePipes() {
        int randompipeStartPosY = (int)((double)(this.pipeStartPosY - this.pipeHeight / 4) - Math.random() * (double)(this.pipeHeight / 2));
        int openingspace = this.frameHeight / 4;
        Pipe upperPipe = new Pipe(this.pipeStartPosX, randompipeStartPosY, this.pipeWidth, this.pipeHeight, this.upperPipeImage);
        this.pipes.add(upperPipe);
        Pipe lowerPipe = new Pipe(this.pipeStartPosX, randompipeStartPosY + this.pipeHeight + openingspace, this.pipeWidth, this.pipeHeight, this.lowerPipeImage);
        this.pipes.add(lowerPipe);
    }

    public void move() {
        this.player.setVelocityY(this.player.getVelocityY() + this.gravity);
        this.player.setPosY(this.player.getPosY() + this.player.getVelocityY());
        this.player.setPosY(Math.max(this.player.getPosY(), 0));

        for (Pipe pipe : this.pipes) {
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());


            if (!pipe.isPassed() && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                pipe.setPassed(true);
                score += 1;
            }


            if (player.getPosX() + player.getWidth() > pipe.getPosX() && player.getPosX() < pipe.getPosX() + pipe.getWidth() &&
                    (player.getPosY() < pipe.getPosY() + pipe.getHeight() || player.getPosY() + player.getHeight() > pipe.getPosY() + pipe.getHeight() + frameHeight / 4)) {

            }
        }

        // Cek jika pemain menabrak/menyentuh batas bawah
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            gameOver();
        }

        this.repaint();
    }




    public void actionPerformed(ActionEvent e) {
        this.move();
        this.repaint();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 32) {
            this.player.setVelocityY(-10);
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
        }
    }

    public void keyReleased(KeyEvent e) {}

    private void restartGame() {
        this.player.setPosY(playerStartPosY);
        this.pipes.clear();
        this.score = 0;
        this.scoreLabel.setText("Score: " + score);
        this.pipesCooldown.restart();
        this.gameloop.restart();
    }

    private void gameOver() {
        this.pipesCooldown.stop();
        this.gameloop.stop();
        int choice = JOptionPane.showConfirmDialog(null, "Game Over! Do you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }
}
