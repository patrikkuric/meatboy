package meatboy.platforms;

import meatboy.Game;
import meatboy.objects.IObject;
import meatboy.LoadedImages;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda PlatformRed - červená platforma
 * @author Patrik Kuric
 */

public class PlatformRed implements IPlatform, ActionListener {
    private int x;
    private int y;
    private final int width;
    private BufferedImage platformImage;
    private boolean interactedWith;
    private final ArrayList<IObject> objects;
    private final Timer animationTimer;

    /**
     * Konštruktor triedy PlatformRed
     * @param x - nastaví súradnicu X pre polohu červenej platformy
     * @param y - nastaví súradnicu Y pre polohu červenej platformy
     * @param width - nastaví šírku červenej platformy
     */
    public PlatformRed(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.interactedWith = false;
        this.objects = new ArrayList<>();

        this.platformImage = LoadedImages.getRedLeft();
        for (int i = 0; i < this.width - 2; i++) {
            BufferedImage filler = LoadedImages.getRedMid();
            this.platformImage = LoadedImages.joinBufferedImage(this.platformImage, filler);
        }
        BufferedImage rightSide = LoadedImages.getRedRight();
        this.platformImage = LoadedImages.joinBufferedImage(this.platformImage, rightSide);

        this.animationTimer = new Timer(16, this);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getXRightSide() {
        return (this.x + 128 * (this.width));
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
    public int getWidth() {
        return this.width;
    }

    @Override
    public BufferedImage getImage() {
        return this.platformImage;
    }

    @Override
    public void stopAnimation() {
        this.animationTimer.stop();
    }

    @Override
    public void addObject(IObject object) {
        this.objects.add(object);
    }

    @Override
    public void special() {
        Game.getInstance().speedUp();
        Game.getInstance().forcePlayerToJump();
        this.y += 10;
        for (IObject object : this.objects) {
            object.setY(object.getY() + 10);
        }

        this.animationTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Game.getInstance().isTheGameRunning()) {
            return;
        }

        this.y += 3;
        for (IObject object : this.objects) {
            object.setY(object.getY() + 3);
        }
    }
}
