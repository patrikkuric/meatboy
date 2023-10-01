package meatboy.objects;

import meatboy.Game;
import meatboy.LoadedImages;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda Chest - truhlica s 10 skóre
 * @author Patrik Kuric
 */

public class Chest implements IObject, ActionListener {
    private int x;
    private int y;
    private BufferedImage chestImage;
    private boolean interactedWith;
    private final Timer animationTimer;
    private int imageCounter;

    /**
     * Konštruktor triedy Chest
     * @param x - nastaví súradnicu X pre polohu truhlice
     * @param y - nastaví súradnicu Y pre polohu truhlice
     */
    public Chest(int x, int y) {
        this.x = x;
        this.y = y;
        this.interactedWith = false;
        this.animationTimer = new Timer(80, this);

        this.imageCounter = 0;
        this.chestImage = LoadedImages.getChestImage();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getXRightSide() {
        return this.x + this.chestImage.getWidth();
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean getInteractedWith() {
        return this.interactedWith;
    }

    @Override
    public void setInteractedWith() {
        this.interactedWith = true;
    }

    @Override
    public int getHeight() {
        return this.chestImage.getHeight();
    }

    @Override
    public int getWidth() {
        return this.chestImage.getWidth();
    }

    @Override
    public BufferedImage getImage() {
        return this.chestImage;
    }

    /**
     * preruší animáciu truhlice
     */
    public void stopAnimation() {
        this.animationTimer.stop();
    }

    @Override
    public void interaction() {
        Game.getInstance().addScore(10);
        this.y -= 2;
        this.imageCounter++;
        this.chestImage = LoadedImages.getChestImages().get(this.imageCounter);

        this.animationTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        this.y -= 2;
        this.imageCounter++;
        this.chestImage = LoadedImages.getChestImages().get(this.imageCounter);
        if (this.imageCounter >= 10) {
            this.animationTimer.stop();
        }

    }

}
