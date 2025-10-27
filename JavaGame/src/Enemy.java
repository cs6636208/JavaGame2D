
import java.awt.Rectangle; // import คลาส Rectangle จาก library awt เพื่อใช้ในการกำหนดขอบเขต (hitbox) และตำแหน่ง

// คลาสนี้จะเก็บข้อมูลของศัตรู (แมว) ทั้งแบบหยุดนิ่งและแบบเคลื่อนที่
public class Enemy {

    public int x, y, width, height; // ตำแหน่ง (x, y) และ ขนาด (width, height) ของศัตรู
    public boolean isMoving; // ตัวแปร boolean เพื่อกำหนดว่าศัตรูตัวนี้เคลื่อนที่ (true) หรือหยุดนิ่ง (false)
    private int speed; // ความเร็วในการเคลื่อนที่ (ใช้เฉพาะเมื่อ isMoving = true)
    private Rectangle targetCheese; // Cheese (เป้าหมาย) ที่แมวตัวนี้กำลังเล็งอยู่

    public Enemy(int x, int y, int w, int h, boolean isMoving, int speed) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.isMoving = isMoving;
        this.speed = speed;
        this.targetCheese = null;
    }

    // เมธอดสำหรับดึงขอบเขต (Hitbox) 
    // เมธอดสำหรับให้ Game.java รู้ขอบเขตของแมวตัวนี้เพื่อตรวจสอบการชน
    public Rectangle getBounds() {
        // คืนค่า object Rectangle ใหม่ โดยใช้ตำแหน่งและขนาดปัจจุบันของศัตรู
        return new Rectangle(x, y, width, height);
    }

    // เมธอดสำหรับ "ล้าง" เป้าหมาย (เช่น เมื่อแมวขโมยสำเร็จ หรือเป้าหมายถูกเก็บไปแล้ว)
    public void clearTarget() {
        this.targetCheese = null;
    }

    // เมธอดนี้จะถูกเรียกใน Game Loop (เช่น ในคลาส Game) เพื่ออัปเดตสถานะของศัตรู
    public void update(Game game) {
        // ถ้าเป็นแมวหยุดนิ่ง ไม่ต้องทำอะไร
        if (!isMoving) {
            return;
        }

        // หา Cheese ที่ใกล้ที่สุด (ถ้ายังไม่มีเป้าหมาย หรือเป้าหมายเดิมถูกเก็บไปแล้ว)
        // ตรวจสอบว่า: ยังไม่มีเป้าหมาย (targetCheese == null)
        // หรือ เป้าหมายที่มีอยู่ถูกเก็บไปแล้ว (เรียก isTargetCollected)
        if (targetCheese == null || isTargetCollected(targetCheese, game.getCollectibles())) {
            // ถ้าเงื่อนไขเป็นจริง ให้ค้นหา Cheese ที่อยู่ใกล้ที่สุดอันใหม่
            targetCheese = findNearestAvailableCheese(game.getCollectibles());
        }

        // เคลื่อนที่เข้าหาเป้าหมาย (ถ้ามีเป้าหมาย)
        if (targetCheese != null) {
            // การเคลื่อนที่ในแกน X
            if (x < targetCheese.x) { // ถ้าแมวอยู่ทางซ้ายของเป้าหมาย
                x += speed; // เคลื่อนที่ไปทางขวา
            } else if (x > targetCheese.x) { // ถ้าแมวอยู่ทางขวาของเป้าหมาย
                x -= speed; // เคลื่อนที่ไปทางซ้าย
            }

            // การเคลื่อนที่ในแกน Y
            if (y < targetCheese.y) { // ถ้าแมวอยู่เหนือเป้าหมาย
                y += speed; // เคลื่อนที่ลง
            } else if (y > targetCheese.y) { // ถ้าแมวอยู่ใต้เป้าหมาย
                y -= speed; // เคลื่อนที่ขึ้น
            }
        }
    }

    // ตรวจสอบว่าเป้าหมายถูกเก็บไปหรือยัง
    // ตรวจสอบว่าเป้าหมายของเรา (target) ยังคงอยู่ในอาร์เรย์ collectibles (Cheese ทั้งหมด) หรือไม่
    private boolean isTargetCollected(Rectangle target, Rectangle[] collectibles) {
        // วนลูปดู Cheese ทั้งหมดในด่าน
        for (Rectangle c : collectibles) {
            // ถ้า Cheese ชิ้นนี้ (c) ยังอยู่ (ไม่ null) และตำแหน่งตรงกับเป้าหมาย (target)
            if (c != null && c.equals(target)) {
                return false; // แปลว่าเป้าหมายยังอยู่ (ยังไม่ถูกเก็บ)
            }
        }
        // ถ้าวนลูปจนจบแล้วไม่เจอเป้าหมาย แปลว่ามันถูกเก็บไปแล้ว
        return true;
    }

    // ค้นหา Cheese ที่ใกล้ที่สุด
    // วนลูปหา Cheese ที่ยังไม่ถูกเก็บ (ไม่ null) และอยู่ใกล้เราที่สุด
    private Rectangle findNearestAvailableCheese(Rectangle[] collectibles) {
        Rectangle nearest = null; // ตัวแปรเก็บ Cheese ที่ใกล้ที่สุดที่เจอ (เริ่มต้นเป็น null)
        double minDistance = Double.MAX_VALUE; // ตัวแปรเก็บระยะทางที่น้อยที่สุด (เริ่มต้นด้วยค่าสูงสุด)

        // วนลูปดู Cheese ทุกชิ้น
        for (Rectangle c : collectibles) {
            if (c != null) { // ถ้า Cheese ชิ้นนี้ยังอยู่ (ยังไม่ถูกเก็บ)
                // คำนวณระยะห่างระหว่างแมว (x, y) กับ Cheese (c.x, c.y)
                // โดยใช้ทฤษฎีพีทาโกรัส: sqrt((x2-x1)^2 + (y2-y1)^2)
                double distance = Math.sqrt(Math.pow(c.x - x, 2) + Math.pow(c.y - y, 2));

                // ถ้า
                if (distance < minDistance) {
                    minDistance = distance; // อัปเดตระยะทางที่น้อยที่สุด
                    nearest = c; // อัปเดต Cheese ที่ใกล้ที่สุด
                }
            }
        }
        return nearest; // คืนค่า Cheese ที่ใกล้ที่สุด (หรือ null ถ้าไม่มี Cheese เหลือ)
    }
}
