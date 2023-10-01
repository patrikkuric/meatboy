package meatboy.player;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda PlayerHitbox - hitbox hráča
 * @author Patrik Kuric
 */

public class PlayerHitbox {
    private int x;
    private int y;

    private static final int X_OFFSET = 25;
    private static final int Y_OFFSET = 20;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 50;

    /**
     * Konštruktor triedy PlayerHitbox
     * @param x - nastaví hodnotu X-ovej súradnice X hráča
     * @param y - nastaví hodnotu X-ovej súradnice Y hráča
     */
    public PlayerHitbox(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter pre ľavú súradnicu X hitboxu hráča
     * @return vráti ľavú súradnicu X pre hitbox hráča
     */
    public int getLeftX() {
        return this.x + X_OFFSET;
    }

    /**
     * Getter pre pravú súradnicu X hitboxu hráča
     * @return vráti pravú súradnicu X pre hitbox hráča
     */
    public int getRightX() {
        return this.x + X_OFFSET + WIDTH;
    }

    /**
     * Getter pre hornú súradnicu Y hitboxu hráča
     * @return vráti hornú súradnicu Y pre hitbox hráča
     */
    public int getUpperY() {
        return this.y + Y_OFFSET;
    }

    /**
     * Getter pre dolnú súradnicu Y hitboxu hráča
     * @return vráti dolnú súradnicu Y pre hitbox hráča
     */
    public int getBottomY() {
        return this.y + Y_OFFSET + HEIGHT ;
    }

    /**
     * Setter pre hodnotu X-ovej súradnice hráča
     * @param x - nastaví hodnotu X-ovej súradnice hráča
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter pre hodnotu Y-ovej súradnice hráča
     * @param y - nastaví hodnotu Y-ovej súradnice hráča
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * vráti šírku hitboxu hráča
     * @return šírka hitboxu hráča
     */
    public int getWidth() {
        return WIDTH;
    }
}
