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
 * Trieda Coin - minca v hodnote 1 skóre
 * @author Patrik Kuric
 */

public class Coin implements IObject, ActionListener {
    private final Timer animationTimer;
    private int animationCounter;
    private int x;
    private int y;
    private BufferedImage coinImage;
    private final int coinWidth;
    private final int coinHeight;
    private boolean interactedWith;

    /**
     * Konštruktor triedy Coin
     * @param x - nastaví súradnicu X pre polohu mince
     * @param y - nastaví súradnicu Y pre polohu mince
     */
    public Coin(int x, int y) {
        this.animationCounter = 1;
        this.x = x;
        this.y = y;
        this.interactedWith = false;

        this.coinImage = LoadedImages.getCoinImage();
        this.coinWidth = this.coinImage.getWidth();
        this.coinHeight = this.coinImage.getHeight();

        this.animationTimer = new Timer(50, this);
        this.animationTimer.start();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getXRightSide() {
        return this.x + this.coinWidth;
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
        return this.coinHeight;
    }

    @Override
    public int getWidth() {
        return this.coinWidth;
    }

    @Override
    public BufferedImage getImage() {
        return this.coinImage;
    }

    @Override
    public void stopAnimation() {
        this.animationTimer.stop();
    }

    @Override
    public void interaction() {
        Game.getInstance().addScore(1);
        this.animationTimer.stop();
        this.coinImage = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (this.coinImage == null) {
            return;
        }

        int widthBefore = this.coinImage.getWidth();


        this.coinImage = LoadedImages.getCoinImages().get(this.animationCounter);

        this.x = this.x + (widthBefore - this.coinImage.getWidth()) / 2 ;

        this.animationCounter++;

        if (this.animationCounter == 16) {
            this.animationCounter = 0;
        }
    }
}
