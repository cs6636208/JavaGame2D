
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// คลาสหลักของโปรแกรมชื่อ Lobby สืบทอดคุณสมบัติมาจาก JFrame (หน้าต่างโปรแกรม)
public class Lobby extends JFrame {

    private CardLayout cardLayout; // ตัวจัดการการสลับหน้าจอ (JPanel)
    private JPanel mainPanel; // Panel หลักที่จะบรรจุ Panel ย่อยอื่นๆ (ที่เปรียบเหมือนการ์ด)
    private Game gamePanel;  // Panel ของตัวเกม (สมมติว่ามีคลาส Game ที่ไม่ได้แสดงในไฟล์นี้)
    private JPanel lobbyPanel;  // Panel สำหรับหน้าล็อบบี้ (หน้าเลือก Level)
    private JPanel gameOverPanel; // Panel สำหรับหน้า Game Over
    private JPanel startPanel;    // Panel สำหรับหน้า Start เริ่มต้น
    private Image lobbyBackgroundImage; // รูปภาพพื้นหลังสำหรับหน้า Start และ Lobby
    private Image gameOverBackgroundImage; // รูปภาพพื้นหลังสำหรับหน้า Game Over
    private JLabel gameOverScoreLabel; // Label (ป้ายข้อความ) สำหรับแสดงคะแนนในหน้า Game Over

    public Lobby() {
        // สร้าง object CardLayout เพื่อใช้จัดการสลับหน้า
        cardLayout = new CardLayout();
        // สร้าง mainPanel โดยใช้ CardLayout ที่เพิ่งสร้าง
        mainPanel = new JPanel(cardLayout);
        // โหลดรูปภาพพื้นหลัง "startgamelobby.png" จาก resource ของโปรแกรม
        lobbyBackgroundImage = new ImageIcon(getClass().getResource("startgamelobby.png")).getImage();
        // โหลดรูปภาพพื้นหลัง "gameover.png" จาก resource ของโปรแกรม
        gameOverBackgroundImage = new ImageIcon(getClass().getResource("gameover.png")).getImage();

        // สร้าง startPanel (หน้าแรก) โดยใช้ anonymous inner class
        startPanel = new JPanel() {
            @Override
            // เพื่อ override เมธอด paintComponent (เมธอดที่ใช้วาด component)
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // เรียกเมธอดวาดของคลาสแม่ (JPanel)
                // วาดรูป lobbyBackgroundImage ให้เต็มขนาดของ Panel
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
                // เมื่อถูกกด ให้สลับไปแสดง "Lobby" (หน้าเลือก Level)
                cardLayout.show(mainPanel, "Lobby");
            }
        });

        // จัดปุ่ม Start Game ให้อยู่กลางจอ (โดยใช้ "กาว" ยืดหยุ่น)
        startPanel.add(Box.createVerticalGlue()); // เพิ่มช่องว่างยืดหยุ่นได้ (Glue) ด้านบนปุ่ม
        startPanel.add(startGameButton); // เพิ่มปุ่ม "Start Game" ลงใน startPanel
        startPanel.add(Box.createVerticalGlue()); // เพิ่มช่องว่างยืดหยุ่นได้ (Glue) ด้านล่างปุ่ม

        // --- สร้างหน้า lobby (หน้าเลือก Level) ---
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

        // ... (โค้ดสร้างปุ่ม Level 1-5) ...
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
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 100))); // เพิ่มช่องว่างคงที่ขนาด 100 pixel ในแนวตั้ง
        lobbyPanel.add(Level1Button); // เพิ่มปุ่ม Level 1
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10))); // เพิ่มช่องว่างคงที่ 10 pixel (เว้นระยะระหว่างปุ่ม)
        lobbyPanel.add(Level2Button);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(Level3Button);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(Level4Button);
        lobbyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        lobbyPanel.add(Level5Button);
        lobbyPanel.add(Box.createVerticalGlue()); // เพิ่มช่องว่างยืดหยุ่นด้านล่างสุด

        // สร้าง object ของคลาส Game (ตัวเกมจริง)
        gamePanel = new Game(); // (สมมติว่าคลาส Game kế thừaมาจาก JPanel)
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
        yesButton.setAlignmentX(Component.CENTER_ALIGNMENT); // จัดปุ่มกึ่งกลางแนวนอน
        // สร้างปุ่ม "No (Back to Lobby)"
        JButton noButton = new JButton("No (Back to Lobby)");
        noButton.setAlignmentX(Component.CENTER_ALIGNMENT); // จัดปุ่มกึ่งกลางแนวนอน

        // เพิ่มตัวดักฟังเหตุการณ์เมื่อปุ่ม "Yes" ถูกกด
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // เมื่อกด "Yes" ให้เริ่มเกมใหม่ โดยใช้ level ความยากเดิม (ที่เก็บใน gamePanel.difficultyLevel)
                startGame(gamePanel.difficultyLevel);
            }
        });

        // เพิ่มตัวดักฟังเหตุการณ์เมื่อปุ่ม "No" ถูกกด
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // เมื่อกด "No" ให้เรียกเมธอด showLobby() (ซึ่งจะกลับไปหน้า Start)
                showLobby();
            }
        });

        // สร้าง Label สำหรับแสดงคะแนน เริ่มต้นด้วย "Score: 0"
        gameOverScoreLabel = new JLabel("Score: 0");
        gameOverScoreLabel.setFont(new Font("Arial", Font.BOLD, 40)); // ตั้งค่า font เป็น Arial, ตัวหนา, ขนาด 40
        gameOverScoreLabel.setForeground(Color.WHITE); // ตั้งค่าสีตัวอักษรเป็นสีขาว
        gameOverScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // จัด Label ให้อยู่กึ่งกลางแนวนอน

        // เพิ่มส่วนประกอบต่างๆ ลงใน gameOverPanel
        gameOverPanel.add(Box.createVerticalGlue()); // เพิ่มช่องว่างยืดหยุ่นด้านบน
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 300))); // เพิ่มช่องว่างคงที่ 300 pixel (เพื่อดัน UI ลงมาด้านล่าง)
        gameOverPanel.add(gameOverScoreLabel); // เพิ่ม Label แสดงคะแนน
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 10))); // เพิ่มช่องว่าง 10 pixel
        gameOverPanel.add(yesButton); // เพิ่มปุ่ม "Yes"
        gameOverPanel.add(Box.createRigidArea(new Dimension(0, 10))); // เพิ่มช่องว่าง 10 pixel
        gameOverPanel.add(noButton); // เพิ่มปุ่ม "No"
        gameOverPanel.add(Box.createVerticalGlue()); // เพิ่มช่องว่างยืดหยุ่นด้านล่าง

        mainPanel.add(startPanel, "Start"); // เพิ่ม startPanel โดยตั้งชื่อว่า "Start"
        mainPanel.add(lobbyPanel, "Lobby"); // เพิ่ม lobbyPanel โดยตั้งชื่อว่า "Lobby"
        mainPanel.add(gamePanel, "Game"); // เพิ่ม gamePanel โดยตั้งชื่อว่า "Game"
        mainPanel.add(gameOverPanel, "GameOver"); // เพิ่ม gameOverPanel โดยตั้งชื่อว่า "GameOver"

        add(mainPanel); // เพิ่ม mainPanel (ที่บรรจุทุกหน้า) ลงใน JFrame
        cardLayout.show(mainPanel, "Start"); // สั่งให้ CardLayout แสดงหน้า "Start" เป็นหน้าแรก
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ตั้งค่าให้โปรแกรมปิดการทำงานเมื่อกดปุ่ม X
        setSize(800, 600); // ตั้งค่าขนาดหน้าต่างเป็น 800x600 pixel
        setLocationRelativeTo(null); // ตั้งค่าให้หน้าต่างแสดงผลกลางจอ
        setVisible(true); // ทำให้หน้าต่างแสดงผลขึ้นมา
    }

    // เมธอดสำหรับเริ่มเกม (หรือเริ่มใหม่) ตาม level ที่เลือก
    private void startGame(int difficulty) {
        gamePanel.difficultyLevel = difficulty; // เก็บค่า difficulty ที่เลือกไว้ในตัวแปรของ gamePanel
        gamePanel.setDifficulty(difficulty); // เรียกเมธอด setDifficulty ใน gamePanel (เพื่อตั้งค่าเกมตาม level)
        gamePanel.restartGame(); // เรียกเมธอด restartGame ใน gamePanel (เพื่อเริ่มเกมใหม่/รีเซ็ตค่า)
        cardLayout.show(mainPanel, "Game"); // สลับหน้าจอไปที่ "Game"
        gamePanel.requestFocusInWindow(); // สั่งให้ gamePanel ได้รับ focus (เพื่อให้รับ input จาก keyboard ได้ทันที)
    }

    // เมธอดสำหรับกลับไปหน้าจอเริ่มต้น
    public void showLobby() {
        cardLayout.show(mainPanel, "Start"); // สลับหน้าจอไปที่ "Start"
    }

    // เมธอดสำหรับแสดงหน้า Game Over (คาดว่าจะถูกเรียกจาก gamePanel เมื่อเกมจบ)
    public void showGameOver() {
        int finalScore = gamePanel.getScore(); // ดึงคะแนนสุดท้ายจาก gamePanel
        gameOverScoreLabel.setText("Score: " + finalScore); // อัปเดตข้อความใน gameOverScoreLabel ให้แสดงคะแนนที่ได้
        cardLayout.show(mainPanel, "GameOver"); // สลับหน้าจอไปที่ "GameOver"
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
