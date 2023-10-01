package meatboy.objects;

import meatboy.Game;
import meatboy.LoadedImages;
import meatboy.platforms.IPlatform;
import meatboy.other.Direction;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda Enemy - nepriateľ, ktorý pri dotyku eliminuje hráča
 * @author Patrik Kuric
 */

public class Enemy implements IObject, ActionListener {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private boolean interactedWith;
    private final BufferedImage enemyImage;
    private final Timer animationTimer;
    private final IPlatform platform;
    private Direction direction;
    private final int speed;

    /**
     * Konštruktor triedy Enemy
     * @param x - nastaví súradnicu X pre polohu nepriateľa
     * @param y - nastaví súradnicu Y pre polohu nepriateľa
     * @param platform - platforma, na ktorej sa nepriateľ nachádza
     * @param speed - rýchlosť, ktorou sa nepriateľ pohybuje
     */
    public Enemy(int x, int y, IPlatform platform, int speed) {
        this.x = x;
        this.y = y;
        this.interactedWith = false;
        this.platform = platform;
        this.speed = speed;

        int dir = ThreadLocalRandom.current().nextInt(0, 2);
        if (dir == 0) {
            this.direction = Direction.RIGHT;
        } else {
            this.direction = Direction.LEFT;
        }
        this.enemyImage = LoadedImages.getEnemyImage();
        this.width = this.enemyImage.getWidth();
        this.height = this.enemyImage.getHeight();

        this.animationTimer = new Timer(5, this);
        this.animationTimer.start();
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
        return this.enemyImage;
    }

    @Override
    public void interaction() {
        this.interactedWith = true;
        Game.getInstance().playerDies();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (this.x >= this.platform.getXRightSide() - 64) {
            this.direction = Direction.LEFT;
        } else if (this.x <= this.platform.getX()) {
            this.direction = Direction.RIGHT;
        }

        if (this.direction == Direction.LEFT) {
            this.x -= this.speed / 2;
        } else {
            this.x += this.speed;
        }

    }

    /**
     * preruší animáciu nepriateľa
     */
    public void stopAnimation() {
        this.animationTimer.stop();
    }
}
