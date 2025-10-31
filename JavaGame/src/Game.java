
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {

    private Timer timer;
    public int difficultyLevel;
    private int characterX = 100;
    private int characterY = 400;
    private int velocityY = 0;
    private int velocityX = 0;
    private final int gravity = 1;
    private boolean isJumping = false;
    private boolean isOnPlatform = false;
    private int lives = 0;
    private boolean gameOver = false;
    private boolean hasWon = false;
    private boolean isAKeyPressed = false;
    private boolean isDKeyPressed = false;

    private Image characterImage;
    private Image enemyImage;
    private Image specialEnemyImage;
    private Image collectibleImage;
    private Image finishLineImage;
    private Image backgroundImage;
    private Image heartImage;

    private ArrayList<Enemy> enemiesList; // ศัตรูปกติ (นิ่ง/เคลื่อนที่)
    private ArrayList<Rectangle> specialEnemiesList; // ศัตรูพิเศษ (แมวตก)

    private Rectangle[] platforms; // แพลตฟอร์ม
    private Rectangle finishLine; // เส้นชัย (หนูสีส้มแดง)
    private Rectangle[] collectibles; // วัตถุที่ต้องเก็บ (Cheese) - จะถูกตั้งค่าเป็น null เมื่อถูกเก็บ
    private Rectangle[] collectibleOriginalPositions; // ตำแหน่งดั้งเดิมของ Cheese (สำหรับ respawn)
    private int score = 0;
    private int collectiblesCount = 0;
    private final int GAME_DURATION_SECONDS = 60; // เวลาทั้งหมด 60 วินาที
    private int remainingTime; // เวลาที่เหลือ 
    private int tickCounter; // ตัวนับ tick (20ms) เพื่อใช้คำนวณวินาที
    private final int TICKS_PER_SECOND = 50; // 50 ticks (ครั้ง) ต่อ 1 วินาที (1000ms / 20ms = 50)

    public Game() {
        timer = new Timer(20, this);
        timer.start();
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);
        specialEnemiesList = new ArrayList<>();
        enemiesList = new ArrayList<>();
        characterImage = new ImageIcon(getClass().getResource("Rat.png")).getImage();
        enemyImage = new ImageIcon(getClass().getResource("cat.png")).getImage();
        collectibleImage = new ImageIcon(getClass().getResource("cheese.png")).getImage();
        finishLineImage = new ImageIcon(getClass().getResource("Rat1-1.png.png")).getImage();
        heartImage = new ImageIcon(getClass().getResource("heart.png")).getImage();
        specialEnemyImage = new ImageIcon(getClass().getResource("SpecialCat.png")).getImage();
        setDifficulty(1);
    }

    public void setDifficulty(int difficulty) {
        switch (difficulty) {
            case 1:
                setLevel1Difficulty();
                break;
            case 2:
                setLevel2Difficulty();
                break;
            case 3:
                setLevel3Difficulty();
                break;
            case 4:
                setLevel4Difficulty();
                break;
            case 5:
                setLevel5Difficulty();
                break;
        }
    }

    public void setLevel1Difficulty() {
        lives = 3;
        backgroundImage = new ImageIcon(getClass().getResource("level1.png")).getImage();
        platforms = new Rectangle[2];
        platforms[0] = new Rectangle(300, 350, 400, 20);
        platforms[1] = new Rectangle(100, 250, 300, 20);

        enemiesList = new ArrayList<>();
        enemiesList.add(new Enemy(300, 290, 80, 75, false, 0));
        enemiesList.add(new Enemy(200, 190, 80, 75, true, 1));
        enemiesList.add(new Enemy(450, 425, 80, 75, false, 0));

        collectibleOriginalPositions = new Rectangle[2];
        collectibleOriginalPositions[0] = new Rectangle(500, 300, 30, 50);
        collectibleOriginalPositions[1] = new Rectangle(120, 180, 30, 50);

        collectibles = new Rectangle[2];
        collectibles[0] = new Rectangle(collectibleOriginalPositions[0]);
        collectibles[1] = new Rectangle(collectibleOriginalPositions[1]);
        finishLine = new Rectangle(700, 400, 50, 100);
        repaint();
    }

    public void setLevel2Difficulty() {
        lives = 4;
        backgroundImage = new ImageIcon(getClass().getResource("level2.png")).getImage();
        platforms = new Rectangle[3];
        platforms[0] = new Rectangle(400, 350, 300, 20);
        platforms[1] = new Rectangle(50, 250, 400, 20);
        platforms[2] = new Rectangle(200, 125, 200, 20);

        enemiesList = new ArrayList<>();
        enemiesList.add(new Enemy(500, 290, 80, 75, false, 0));
        enemiesList.add(new Enemy(250, 190, 80, 75, true, 1));
        enemiesList.add(new Enemy(225, 420, 80, 75, false, 0));
        enemiesList.add(new Enemy(240, 60, 80, 75, true, 1));

        collectibleOriginalPositions = new Rectangle[3];
        collectibleOriginalPositions[0] = new Rectangle(600, 300, 70, 50);
        collectibleOriginalPositions[1] = new Rectangle(200, 200, 70, 50);
        collectibleOriginalPositions[2] = new Rectangle(500, 400, 70, 50);

        collectibles = new Rectangle[3];
        collectibles[0] = new Rectangle(collectibleOriginalPositions[0]);
        collectibles[1] = new Rectangle(collectibleOriginalPositions[1]);
        collectibles[2] = new Rectangle(collectibleOriginalPositions[2]);
        finishLine = new Rectangle(700, 400, 50, 100);
        repaint();
    }

    public void setLevel3Difficulty() {
        lives = 5;
        backgroundImage = new ImageIcon(getClass().getResource("level3.png")).getImage();
        platforms = new Rectangle[4];
        platforms[0] = new Rectangle(100, 350, 300, 20);
        platforms[1] = new Rectangle(100, 250, 400, 20);
        platforms[2] = new Rectangle(500, 150, 300, 20);
        platforms[3] = new Rectangle(0, 150, 200, 20);

        enemiesList = new ArrayList<>();
        enemiesList.add(new Enemy(400, 190, 80, 75, true, 1));
        enemiesList.add(new Enemy(125, 190, 80, 75, false, 0));
        enemiesList.add(new Enemy(200, 290, 80, 75, true, 1));
        enemiesList.add(new Enemy(625, 425, 80, 75, false, 0));

        collectibleOriginalPositions = new Rectangle[4];
        collectibleOriginalPositions[0] = new Rectangle(150, 300, 70, 50);
        collectibleOriginalPositions[1] = new Rectangle(575, 420, 70, 50);
        collectibleOriginalPositions[2] = new Rectangle(405, 200, 70, 50);
        collectibleOriginalPositions[3] = new Rectangle(50, 100, 70, 50);

        collectibles = new Rectangle[4];
        collectibles[0] = new Rectangle(collectibleOriginalPositions[0]);
        collectibles[1] = new Rectangle(collectibleOriginalPositions[1]);
        collectibles[2] = new Rectangle(collectibleOriginalPositions[2]);
        collectibles[3] = new Rectangle(collectibleOriginalPositions[3]);
        finishLine = new Rectangle(700, 60, 50, 100);
        repaint();
    }

    public void setLevel4Difficulty() {
        lives = 5;
        backgroundImage = new ImageIcon(getClass().getResource("level4.png")).getImage();
        platforms = new Rectangle[5];
        platforms[0] = new Rectangle(50, 400, 200, 20);
        platforms[1] = new Rectangle(300, 320, 200, 20);
        platforms[2] = new Rectangle(50, 240, 200, 20);
        platforms[3] = new Rectangle(400, 160, 300, 20);
        platforms[4] = new Rectangle(650, 300, 100, 20);

        enemiesList = new ArrayList<>();
        enemiesList.add(new Enemy(350, 260, 80, 75, true, 1));
        enemiesList.add(new Enemy(100, 180, 80, 75, false, 0));
        enemiesList.add(new Enemy(450, 100, 80, 75, true, 2));
        enemiesList.add(new Enemy(200, 425, 80, 75, false, 0));
        enemiesList.add(new Enemy(550, 425, 80, 75, true, 1));

        collectibleOriginalPositions = new Rectangle[5];
        collectibleOriginalPositions[0] = new Rectangle(100, 350, 70, 50);
        collectibleOriginalPositions[1] = new Rectangle(450, 270, 70, 50);
        collectibleOriginalPositions[2] = new Rectangle(70, 190, 70, 50);
        collectibleOriginalPositions[3] = new Rectangle(650, 110, 70, 50);
        collectibleOriginalPositions[4] = new Rectangle(700, 420, 70, 50);

        collectibles = new Rectangle[5];
        collectibles[0] = new Rectangle(collectibleOriginalPositions[0]);
        collectibles[1] = new Rectangle(collectibleOriginalPositions[1]);
        collectibles[2] = new Rectangle(collectibleOriginalPositions[2]);
        collectibles[3] = new Rectangle(collectibleOriginalPositions[3]);
        collectibles[4] = new Rectangle(collectibleOriginalPositions[4]);

        finishLine = new Rectangle(700, 200, 50, 100);
        repaint();
    }

    public void setLevel5Difficulty() {
        lives = 5;
        backgroundImage = new ImageIcon(getClass().getResource("level5.png")).getImage();
        platforms = new Rectangle[6];
        platforms[0] = new Rectangle(100, 380, 150, 20);
        platforms[1] = new Rectangle(300, 300, 150, 20);
        platforms[2] = new Rectangle(500, 220, 150, 20);
        platforms[3] = new Rectangle(300, 140, 150, 20);
        platforms[4] = new Rectangle(50, 100, 100, 20);
        platforms[5] = new Rectangle(600, 380, 100, 20);

        enemiesList = new ArrayList<>();
        enemiesList.add(new Enemy(320, 240, 80, 75, true, 2));
        enemiesList.add(new Enemy(520, 160, 80, 75, true, 2));
        enemiesList.add(new Enemy(320, 80, 80, 75, true, 2));
        enemiesList.add(new Enemy(70, 40, 80, 75, false, 0));
        enemiesList.add(new Enemy(400, 425, 80, 75, true, 1));
        enemiesList.add(new Enemy(700, 425, 80, 75, true, 1));

        collectibleOriginalPositions = new Rectangle[6];
        collectibleOriginalPositions[0] = new Rectangle(200, 330, 70, 50);
        collectibleOriginalPositions[1] = new Rectangle(400, 250, 70, 50);
        collectibleOriginalPositions[2] = new Rectangle(600, 170, 70, 50);
        collectibleOriginalPositions[3] = new Rectangle(350, 90, 70, 50);
        collectibleOriginalPositions[4] = new Rectangle(650, 330, 70, 50);
        collectibleOriginalPositions[5] = new Rectangle(50, 420, 70, 50);

        collectibles = new Rectangle[6];
        collectibles[0] = new Rectangle(collectibleOriginalPositions[0]);
        collectibles[1] = new Rectangle(collectibleOriginalPositions[1]);
        collectibles[2] = new Rectangle(collectibleOriginalPositions[2]);
        collectibles[3] = new Rectangle(collectibleOriginalPositions[3]);
        collectibles[4] = new Rectangle(collectibleOriginalPositions[4]);
        collectibles[5] = new Rectangle(collectibleOriginalPositions[5]);

        finishLine = new Rectangle(70, 0, 50, 100);
        repaint();
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = -15;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !hasWon) {

            // --- Logic การจับเวลา ---
            tickCounter++;
            if (tickCounter >= TICKS_PER_SECOND) {
                remainingTime--;
                tickCounter = 0;
            }

            // ตรวจสอบว่าเวลาหมดหรือไม่
            if (remainingTime <= 0) {
                gameOver = true;
                Lobby mainFrame = (Lobby) SwingUtilities.getWindowAncestor(this);
                if (mainFrame != null) {
                    mainFrame.showGameOver(); // สั่งให้หน้าต่างหลักแสดงหน้า Game Over
                }
                return; // หยุดการทำงานของ actionPerformed ในรอบนี้
            }

            // ถ้ากำลังกระโดด หรือ ไม่ได้อยู่บนพื้น (กำลังตก)
            if (isJumping || !isOnPlatform) {
                characterY += velocityY;
                velocityY += gravity;
            }

            // ตรวจสอบการเหยียบแพลตฟอร์ม
            isOnPlatform = false;
            for (Rectangle platform : platforms) {
                // สร้าง Hitbox (สี่เหลี่ยมเล็กๆ) ใต้เท้าตัวละครเพื่อตรวจสอบการชน
                if (new Rectangle(characterX, characterY + 80, 50, 1).intersects(platform)) {
                    characterY = platform.y - 80;
                    isJumping = false;
                    isOnPlatform = true;
                    velocityY = 0;
                }
            }

            // ตรวจสอบการตกถึงพื้น (Y = 400)
            if (characterY >= 400) {
                characterY = 400;
                isJumping = false;
                isOnPlatform = true;
            }

            // การเคลื่อนที่ซ้าย-ขวา
            characterX += velocityX;

            // ตรวจสอบขอบหน้าจอ (ซ้าย-ขวา)
            if (characterX < 0) {
                characterX = 0;
            } else if (characterX > getWidth() - 50) {
                characterX = getWidth() - 50;
            }

            // อัปเดต AI ของแมวทุกตัวใน list
            for (Enemy cat : enemiesList) {
                cat.update(this);
            }

            // ตรวจสอบการชนกับศัตรูปกติ (แมว AI)
            // (วนลูปแบบถอยหลัง เพื่อป้องกันปัญหาขณะลบ item ออกจาก List)
            for (int i = enemiesList.size() - 1; i >= 0; i--) {
                Enemy cat = enemiesList.get(i);
                // ถ้า Hitbox ตัวละคร ชนกับ Hitbox ของแมว
                if (new Rectangle(characterX, characterY, 50, 50).intersects(cat.getBounds())) {
                    enemiesList.remove(i);
                    lives--;
                    respawnAllCollectibles();

                    if (lives <= 0) {
                        gameOver = true;
                        Lobby mainFrame = (Lobby) SwingUtilities.getWindowAncestor(this);
                        if (mainFrame != null) {
                            mainFrame.showGameOver();
                        }
                        return;
                    }
                }
            }

            // ตรวจสอบการชนกับศัตรูพิเศษ (แมวตก)
            int fallSpeed = 2 + difficultyLevel;

            for (int i = specialEnemiesList.size() - 1; i >= 0; i--) {
                Rectangle cat = specialEnemiesList.get(i);
                cat.y += fallSpeed;

                // 1. ตรวจสอบว่าชนผู้เล่นหรือไม่
                if (new Rectangle(characterX, characterY, 50, 50).intersects(cat)) {
                    specialEnemiesList.remove(i);
                    lives -= 2;
                    respawnAllCollectibles();

                    if (score < 0) {
                        score = 0;
                    }
                    if (lives <= 0) {
                        gameOver = true;
                        Lobby mainFrame = (Lobby) SwingUtilities.getWindowAncestor(this);
                        if (mainFrame != null) {
                            mainFrame.showGameOver();
                        }
                        return;
                    }
                } // 2. ตรวจสอบว่าตกพ้นจอหรือไม่
                else if (cat.y > getHeight()) {
                    specialEnemiesList.remove(i);
                }
            }

            // ตรรกะการสุ่มเกิดศัตรูพิเศษ (แมวตก)
            // โอกาสเกิดจะเพิ่มขึ้นตาม level
            double spawnChance = 0.01 + (difficultyLevel * 0.002);
            if (Math.random() < spawnChance) {
                int randomX = (int) (Math.random() * (getWidth() - 80));
                specialEnemiesList.add(new Rectangle(randomX, -60, 80, 60));
            }

            // ตรวจสอบการชน (Cat to Cheese) - แมวขโมย Cheese
            for (int i = 0; i < collectibles.length; i++) {
                if (collectibles[i] == null) {
                    continue;
                }

                // วนลูปแมว AI
                for (Enemy cat : enemiesList) {
                    if (cat.isMoving && cat.getBounds().intersects(collectibles[i])) {
                        collectibles[i] = new Rectangle(collectibleOriginalPositions[i]);
                        cat.clearTarget();
                    }
                }
            }

            // ตรวจสอบการชน (Player to Cheese) - ผู้เล่นเก็บ Cheese
            for (int i = 0; i < collectibles.length; i++) {
                if (collectibles[i] != null && new Rectangle(characterX, characterY, 50, 50).intersects(collectibles[i])) {
                    collectibles[i] = null;
                    collectiblesCount++;
                    score += 10;
                }
            }

            // ตรวจสอบเงื่อนไขการชนะ
            // ถ้าตัวละครชนเส้นชัย และ (&&) เก็บ Cheese ได้ครบทุกอัน
            if (new Rectangle(characterX, characterY, 50, 50).intersects(finishLine) && collectiblesCount == collectibles.length) {
                hasWon = true;
            }
        }
        repaint();
    }

    // เมธอดนี้จะถูกเรียกโดย repaint() หรือเมื่อระบบต้องการวาดหน้าจอใหม่
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.DARK_GRAY);
        for (Rectangle platform : platforms) {
            g.fillRect(platform.x, platform.y, platform.width, platform.height);
        }
        g.drawImage(characterImage, characterX, characterY, 50, 100, this);
        for (Enemy cat : enemiesList) {
            g.drawImage(enemyImage, cat.x, cat.y, cat.width, cat.height, this);
        }
        for (Rectangle special : specialEnemiesList) {
            g.drawImage(specialEnemyImage, special.x, special.y, special.width, special.height, this);
        }
        for (Rectangle collectible : collectibles) {
            if (collectible != null) {
                g.drawImage(collectibleImage, collectible.x, collectible.y, collectible.width, collectible.height, this);
            }
        }
        g.drawImage(finishLineImage, finishLine.x, finishLine.y, finishLine.width, finishLine.height, this);
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, 20 + (i * 50), 20, 50, 50, this);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String timeString = "Time: " + remainingTime;
        int stringWidth = g.getFontMetrics().stringWidth(timeString);
        g.drawString(timeString, getWidth() - stringWidth - 20, 50);
        if (hasWon) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("You Win!", getWidth() / 2 - 150, getHeight() / 2);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 20, 75);

        if (hasWon) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("You Win!", getWidth() / 2 - 150, getHeight() / 2);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String finalScoreMsg = "Final Score: " + score;
            int msgWidth = g.getFontMetrics().stringWidth(finalScoreMsg);
            g.drawString(finalScoreMsg, (getWidth() - msgWidth) / 2, getHeight() / 2 + 50);
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("Press R to Restart", getWidth() / 2 - 150, getHeight() / 2 + 100);
            g.drawString("Press Enter to go back to Lobby", getWidth() / 2 - 250, getHeight() / 2 + 150);
        }
    }

    // เมธอดสำหรับให้คลาสอื่น (เช่น Enemy) ดึงข้อมูล Cheese
    public Rectangle[] getCollectibles() {
        return collectibles;
    }

    // เมธอดสำหรับทำให้ Cheese กลับมาเกิดใหม่ทั้งหมด (เมื่อโดนแมวชน)
    public void respawnAllCollectibles() {
        score = 0;
        collectiblesCount = 0;
        if (collectibleOriginalPositions == null) {
            return;
        }
        for (int i = 0; i < collectibles.length; i++) {
            collectibles[i] = new Rectangle(collectibleOriginalPositions[i]);
        }
    }

    public int getScore() {
        return score;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // ไม่ใช้งาน
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // เงื่อนไขสำหรับการกดปุ่มเมื่อเกมยังไม่จบ
        if (!gameOver && !hasWon) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                jump();
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                isAKeyPressed = true;
                velocityX = isDKeyPressed ? 0 : -5;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                isDKeyPressed = true;
                velocityX = isAKeyPressed ? 0 : 5;
            }
        }

        // เงื่อนไขสำหรับการกดปุ่มเมื่อเกมจบ (เฉพาะตอนชนะ)
        if (hasWon) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                restartGame();
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Lobby mainFrame = (Lobby) SwingUtilities.getWindowAncestor(this);
                mainFrame.showLobby();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !hasWon) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                isAKeyPressed = false;
                velocityX = isDKeyPressed ? 5 : 0;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                isDKeyPressed = false;
                velocityX = isAKeyPressed ? -5 : 0;
            }
        }
    }

    // เมธอดสำหรับรีเซ็ตค่าทั้งหมดเพื่อเริ่มเล่นด่านเดิมใหม่
    public void restartGame() {
        setDifficulty(difficultyLevel);
        characterX = 100;
        characterY = 400;
        velocityY = 0;
        velocityX = 0;
        gameOver = false;
        hasWon = false;
        collectiblesCount = 0;
        score = 0;
        specialEnemiesList.clear();
        remainingTime = GAME_DURATION_SECONDS;
        tickCounter = 0;
        repaint();
    }
}
