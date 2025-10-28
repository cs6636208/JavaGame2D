
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// คลาสหลักของโปรแกรมชื่อ Lobby สืบทอดคุณสมบัติมาจาก JFrame 
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
        // สร้าง object CardLayout เพื่อใช้จัดการสลับหน้า
        cardLayout = new CardLayout();
        // สร้าง mainPanel โดยใช้ CardLayout ที่เพิ่งสร้าง
        mainPanel = new JPanel(cardLayout);
        // โหลดรูปภาพพื้นหลัง "startgamelobby.png" จาก resource ของโปรแกรม
        lobbyBackgroundImage = new ImageIcon(getClass().getResource("startgamelobby.png")).getImage();
        // โหลดรูปภาพพื้นหลัง "gameover.png" จาก resource ของโปรแกรม
        gameOverBackgroundImage = new ImageIcon(getClass().getResource("gameover.png")).getImage();

        // สร้าง startPanel (หน้าแรก) 
        startPanel = new JPanel() {
            @Override
            // เพื่อ override เมธอด paintComponent (เมธอดที่ใช้วาด component)
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(lobbyBackgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        // ตั้งค่า Layout Manager ของ startPanel ให้เป็น BoxLayout (จัดเรียงแนวตั้ง)
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        // สร้างปุ่ม "Start Game"
        JButton startGameButton = new JButton("Start Game");
        // จัดปุ่มให้อยู่กึ่งกลางในแนวนอน (สำหรับ BoxLayout)
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // เพิ่ม ActionListener (ตัวดักฟังเหตุการณ์) เมื่อปุ่มถูกกด
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // เมื่อถูกกด ให้สลับไปแสดง "Lobby" (หน้า Level)
                cardLayout.show(mainPanel, "Lobby");
            }
        });

        // จัดปุ่ม Start Game ให้อยู่กลางจอ 
        startPanel.add(Box.createVerticalGlue());
        startPanel.add(startGameButton);
        startPanel.add(Box.createVerticalGlue());

        // สร้าง lobbyPanel และตั้งค่าการวาดพื้นหลังเหมือน startPanel
        lobbyPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(lobbyBackgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        // ตั้งค่า Layout Manager ของ lobbyPanel ให้เป็น BoxLayout (จัดเรียงแนวตั้ง)
        lobbyPanel.setLayout(new BoxLayout(lobbyPanel, BoxLayout.Y_AXIS));

        // สร้างปุ่ม "Level 1"
        JButton Level1Button = new JButton("Level 1");
        Level1Button.setAlignmentX(Component.CENTER_ALIGNMENT); // จัดปุ่มกึ่งกลางแนวนอน
        // เพิ่มตัวดักฟังเหตุการณ์เมื่อปุ่ม Level 1 ถูกกด
        Level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(1); // เรียกเมธอด startGame โดยส่งค่า difficulty เป็น 1
            }
        });

        // สร้างปุ่ม "Level 2"
        JButton Level2Button = new JButton("Level 2");
        Level2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(2); // เรียกเมธอด startGame โดยส่งค่า difficulty เป็น 2
            }
        });

        // สร้างปุ่ม "Level 3"
        JButton Level3Button = new JButton("Level 3");
        Level3Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(3); // เรียกเมธอด startGame โดยส่งค่า difficulty เป็น 3
            }
        });

        // สร้างปุ่ม "Level 4"
        JButton Level4Button = new JButton("Level 4");
        Level4Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(4); // เรียกเมธอด startGame โดยส่งค่า difficulty เป็น 4
            }
        });

        // สร้างปุ่ม "Level 5"
        JButton Level5Button = new JButton("Level 5");
        Level5Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Level5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(5); // เรียกเมธอด startGame โดยส่งค่า difficulty เป็น 5
            }
        });

        // เพิ่มปุ่มและช่องว่างไปที่ lobby (หน้าเลือก Level)
        lobbyPanel.add(Box.createVerticalGlue()); // เพิ่มช่องว่างยืดหยุ่นด้านบนสุด
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

        // สร้าง object ของคลาส Game (ตัวเกมจริง)
        gamePanel = new Game();
        // สร้าง gameOverPanel และตั้งค่าการวาดพื้นหลัง "gameover.png"
        gameOverPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(gameOverBackgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };
        // ตั้งค่า Layout Manager ของ gameOverPanel ให้เป็น BoxLayout (จัดเรียงแนวตั้ง)
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));

        // สร้างปุ่ม "Yes (Play Again)"
        JButton yesButton = new JButton("Yes (Play Again)");
        yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // สร้างปุ่ม "No (Back to Lobby)"
        JButton noButton = new JButton("No (Back to Lobby)");
        noButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // เพิ่มตัวดักฟังเหตุการณ์เมื่อปุ่ม "Yes" ถูกกด
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(gamePanel.difficultyLevel);
            }
        });

        // เพิ่มตัวดักฟังเหตุการณ์เมื่อปุ่ม "No" ถูกกด
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLobby();
            }
        });

        // สร้าง Label สำหรับแสดงคะแนน เริ่มต้นด้วย "Score: 0"
        gameOverScoreLabel = new JLabel("Score: 0");
        gameOverScoreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gameOverScoreLabel.setForeground(Color.WHITE);
        gameOverScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // เพิ่มส่วนประกอบต่างๆ ลงใน gameOverPanel
        gameOverPanel.add(Box.createVerticalGlue()); // เพิ่มช่องว่างยืดหยุ่นด้านบน
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        gameOverPanel.add(gameOverScoreLabel); // เพิ่ม Label แสดงคะแนน
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameOverPanel.add(yesButton); // เพิ่มปุ่ม "Yes"
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameOverPanel.add(noButton); // เพิ่มปุ่ม "No"
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

    // เมธอดสำหรับเริ่มเกม (หรือเริ่มใหม่) ตาม level ที่เลือก
    private void startGame(int difficulty) {
        gamePanel.difficultyLevel = difficulty;
        gamePanel.setDifficulty(difficulty);
        gamePanel.restartGame();
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
    }

    // เมธอดสำหรับกลับไปหน้าจอเริ่มต้น
    public void showLobby() {
        cardLayout.show(mainPanel, "Start"); // สลับหน้าจอไปที่ "Start"
    }

    // เมธอดสำหรับแสดงหน้า Game Over (คาดว่าจะถูกเรียกจาก gamePanel เมื่อเกมจบ)
    public void showGameOver() {
        int finalScore = gamePanel.getScore();
        gameOverScoreLabel.setText("Score: " + finalScore);
        cardLayout.show(mainPanel, "GameOver");
    }

    // เมธอด main จุดเริ่มต้นของการรันโปรแกรม
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lobby();
            }
        });
    }
}
