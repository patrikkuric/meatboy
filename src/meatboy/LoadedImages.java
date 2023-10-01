package meatboy;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Date: 3/19/2022
 * Time: 10:14 PM
 *
 * Trieda LoadedImages - načítané obrázky, obsahuje gettery pre každý typ obrázka, ktorý sa v hre používa
 * @author Patrik Kuric
 */

public final class LoadedImages {

    private static BufferedImage greenLeft;
    private static BufferedImage greenMid;
    private static BufferedImage greenRight;
    private static BufferedImage blueLeft;
    private static BufferedImage blueMid;
    private static BufferedImage blueRight;
    private static BufferedImage redLeft;
    private static BufferedImage redMid;
    private static BufferedImage redRight;
    private static BufferedImage whiteLeft;
    private static BufferedImage whiteMid;
    private static BufferedImage whiteRight;

    private static BufferedImage woodenSign;
    private static BufferedImage spikeImage;
    private static BufferedImage enemyImage;
    private static BufferedImage coinImage;
    private static BufferedImage chestImage;
    private static BufferedImage backgroundImage;

    private static BufferedImage jump;
    private static BufferedImage fall;
    private static BufferedImage jumpInverted;
    private static BufferedImage fallInverted;

    private static ArrayList<BufferedImage> coinImages;
    private static ArrayList<BufferedImage> chestImages;
    private static ArrayList<BufferedImage> runningImages;
    private static ArrayList<BufferedImage> runningImagesInverted;
    private static ArrayList<BufferedImage> numbers;

    static {

        String dir = "." + File.separator + "images" + File.separator;

        try {
            greenLeft = ImageIO.read(new File(dir + "GreenLeft.png"));
            greenMid = ImageIO.read(new File(dir + "GreenMid.png"));
            greenRight = ImageIO.read(new File(dir + "GreenRight.png"));
            blueLeft = ImageIO.read(new File(dir + "BlueLeft.png"));
            blueMid = ImageIO.read(new File(dir + "BlueMid.png"));
            blueRight = ImageIO.read(new File(dir + "BlueRight.png"));
            redLeft = ImageIO.read(new File(dir + "RedLeft.png"));
            redMid = ImageIO.read(new File(dir + "RedMid.png"));
            redRight = ImageIO.read(new File(dir + "RedRight.png"));
            whiteLeft = ImageIO.read(new File(dir + "WhiteLeft.png"));
            whiteMid = ImageIO.read(new File(dir + "WhiteMid.png"));
            whiteRight = ImageIO.read(new File(dir + "WhiteRight.png"));

            woodenSign = ImageIO.read(new File(dir + "WoodenSign.png"));
            spikeImage = ImageIO.read(new File(dir + "Spike.png"));
            enemyImage = ImageIO.read(new File(dir + "Enemy.png"));
            coinImage = ImageIO.read(new File(dir + "Coin1.png"));
            chestImage = ImageIO.read(new File(dir + "Chest1.png"));
            backgroundImage = ImageIO.read(new File(dir + "Background.png"));

            jump = ImageIO.read(new File(dir + "Jump.png"));
            fall = ImageIO.read(new File(dir + "Fall.png"));
            jumpInverted = horizontalFlip(jump);
            fallInverted = horizontalFlip(fall);

            coinImages = new ArrayList<>();
            chestImages = new ArrayList<>();
            runningImages = new ArrayList<>();
            runningImagesInverted = new ArrayList<>();
            numbers = new ArrayList<>();

            for (int i = 1; i < 17; i++) {
                coinImages.add(ImageIO.read(new File(dir + "Coin" + i + ".png")));
            }

            for (int i = 1; i < 12; i++) {
                chestImages.add(ImageIO.read(new File(dir + "Chest" + i + ".png")));
            }

            for (int i = 1; i < 19; i++) {
                runningImages.add(ImageIO.read(new File(dir + "Run" + i + ".png")));
                runningImagesInverted.add(horizontalFlip(ImageIO.read(new File(dir + "Run" + i + ".png"))));
            }

            for (int i = 0; i < 10; i++) {
                numbers.add(ImageIO.read(new File(dir + i + ".png")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nenašiel sa nejaký obrázok.");
        }
    }

    //metóda z internetu

    /**
     * metóda, ktorá horizontálne otočí obrázok
     * @param img - obrázok, ktorý sa bude horizontálne preklápať
     * @return vráti horizontálne prevrátený obrázok
     */
    public static BufferedImage horizontalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getType());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return flippedImage;
    }

    //metóda z internetu

    /**
     * metóda, ktorá spojí 2 obrázky do 1
     * @param img1 - základný obrázok
     * @param img2 - obrázok, ktorý budeme k základnému obrázok pripájať
     * @return vráti spojený obrázok pozostávajúci z 2 vstupných obrázkov
     */
    public static BufferedImage joinBufferedImage(BufferedImage img1,
                                                  BufferedImage img2) {
        int offset = 0;
        int width = img1.getWidth() + img2.getWidth() + offset;
        int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        g2.setPaint(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth() + offset, 0);
        g2.dispose();
        return newImage;
    }

    public static BufferedImage getGreenLeft() {
        return greenLeft;
    }

    public static BufferedImage getGreenMid() {
        return greenMid;
    }

    public static BufferedImage getGreenRight() {
        return greenRight;
    }

    public static BufferedImage getBlueLeft() {
        return blueLeft;
    }

    public static BufferedImage getBlueMid() {
        return blueMid;
    }

    public static BufferedImage getBlueRight() {
        return blueRight;
    }

    public static BufferedImage getRedLeft() {
        return redLeft;
    }

    public static BufferedImage getRedMid() {
        return redMid;
    }

    public static BufferedImage getRedRight() {
        return redRight;
    }

    public static BufferedImage getWhiteLeft() {
        return whiteLeft;
    }

    public static BufferedImage getWhiteMid() {
        return whiteMid;
    }

    public static BufferedImage getWhiteRight() {
        return whiteRight;
    }

    public static BufferedImage getWoodenSign() {
        return woodenSign;
    }

    public static BufferedImage getSpikeImage() {
        return spikeImage;
    }

    public static BufferedImage getEnemyImage() {
        return enemyImage;
    }

    public static BufferedImage getCoinImage() {
        return coinImage;
    }

    public static BufferedImage getChestImage() {
        return chestImage;
    }

    public static BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public static ArrayList<BufferedImage> getCoinImages() {
        return coinImages;
    }

    public static ArrayList<BufferedImage> getChestImages() {
        return chestImages;
    }

    public static ArrayList<BufferedImage> getRunningImages() {
        return runningImages;
    }

    public static ArrayList<BufferedImage> getInvertedRunningImages() {
        return runningImagesInverted;
    }

    public static BufferedImage getJumpImage() {
        return jump;
    }

    public static BufferedImage getFallImage() {
        return fall;
    }

    public static BufferedImage getInvertedJumpImage() {
        return jumpInverted;
    }

    public static BufferedImage getInvertedFallImage() {
        return fallInverted;
    }

    public static ArrayList<BufferedImage> getNumbers() {
        return numbers;
    }
}
