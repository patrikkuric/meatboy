package meatboy.objects;

import meatboy.Game;
import meatboy.LoadedImages;

import java.awt.image.BufferedImage;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda Spike - pichliač, ktorý pri dotyku eliminuje hráča
 * @author Patrik Kuric
 */

public class Spike implements IObject {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private boolean interactedWith;
    private final BufferedImage spikeImage;

    /**
     * Konštruktor triedy Spike
     * @param x - nastaví súradnicu X pre polohu pichliača
     * @param y - nastaví súradnicu Y pre polohu pichliača
     */
    public Spike(int x, int y) {
        this.x = x;
        this.y = y;
        this.interactedWith = false;

        this.spikeImage = LoadedImages.getSpikeImage();
        this.width = this.spikeImage.getWidth();
        this.height = this.spikeImage.getHeight();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getXRightSide() {
        return this.x + this.width;
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
        return this.height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public BufferedImage getImage() {
        return this.spikeImage;
    }

    @Override
    public void stopAnimation() {
    }

    @Override
    public void interaction() {
        this.interactedWith = true;
        Game.getInstance().playerDies();
    }
}
