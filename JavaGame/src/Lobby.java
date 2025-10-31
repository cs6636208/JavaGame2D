
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Lobby extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Game gamePanel;
    private JPanel lobbyPanel;
    private JPanel gameOverPanel;
    private JPanel startPanel;
    private Image lobbyBackgroundImage;
    private Image gameOverBackgroundImage;
    private JLabel gameOverScoreLabel;

    public Lobby() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        lobbyBackgroundImage = new ImageIcon(getClass().getResource("startgamelobby.png")).getImage();
        gameOverBackgroundImage = new ImageIcon(getClass().getResource("gameover.png")).getImage();

        startPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(lobbyBackgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Lobby");
            }
        });
        startPanel.add(Box.createVerticalGlue());
        startPanel.add(startGameButton);
        startPanel.add(Box.createVerticalGlue());
        lobbyPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(lobbyBackgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        lobbyPanel.setLayout(new BoxLayout(lobbyPanel, BoxLayout.Y_AXIS));
        JButton Level1Button = new JButton("Level 1");
        Level1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(1);
            }
        });
        JButton Level2Button = new JButton("Level 2");
        Level2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(2);
            }
        });
        JButton Level3Button = new JButton("Level 3");
        Level3Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(3);
            }
        });
        JButton Level4Button = new JButton("Level 4");
        Level4Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(4);
            }
        });
        JButton Level5Button = new JButton("Level 5");
        Level5Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(5);
            }
        });
        lobbyPanel.add(Box.createVerticalGlue());
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        lobbyPanel.add(Level1Button);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(Level2Button);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(Level3Button);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(Level4Button);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(Level5Button);
        lobbyPanel.add(Box.createVerticalGlue());
        gamePanel = new Game();
        gameOverPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(gameOverBackgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        JButton yesButton = new JButton("Yes (Play Again)");
        yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton noButton = new JButton("No (Back to Lobby)");
        noButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(gamePanel.difficultyLevel);
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLobby();
            }
        });
        gameOverScoreLabel = new JLabel("Score: 0");
        gameOverScoreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gameOverScoreLabel.setForeground(Color.WHITE);
        gameOverScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverPanel.add(Box.createVerticalGlue());
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        gameOverPanel.add(gameOverScoreLabel);
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameOverPanel.add(yesButton);
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameOverPanel.add(noButton);
        gameOverPanel.add(Box.createVerticalGlue());

        mainPanel.add(startPanel, "Start");
        mainPanel.add(lobbyPanel, "Lobby");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(gameOverPanel, "GameOver");

        add(mainPanel);
        cardLayout.show(mainPanel, "Start");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame(int difficulty) {
        gamePanel.difficultyLevel = difficulty;
        gamePanel.setDifficulty(difficulty);
        gamePanel.restartGame();
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
    }

    public void showLobby() {
        cardLayout.show(mainPanel, "Start");
    }

    public void showGameOver() {
        int finalScore = gamePanel.getScore();
        gameOverScoreLabel.setText("Score: " + finalScore);
        cardLayout.show(mainPanel, "GameOver");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lobby();
            }
        });
    }
}
