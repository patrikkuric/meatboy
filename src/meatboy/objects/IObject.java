package meatboy.objects;

import java.awt.image.BufferedImage;

/**
 * Interface pre objekty
 */
public interface IObject {

    /**
     * Getter pre súradnicu X
     * @return vráti hodnotu X-ovej súradnice
     */
    int getX();

    /**
     * Getter pre súradnicu X na pravej strane
     * @return vráti hodnotu X-ovej súradnice vpravo
     */
    int getXRightSide();

    /**
     * Setter pre súradnicu X
     * @param x - nastaví hodnotu X-ovej súradnice
     */
    void setX(int x);

    /**
     * Getter pre súradnicu Y
     * @return vráti hodnotu Y-ovej súradnice
     */
    int getY();

    /**
     * Setter pre súradnicu Y
     * @param y - nastaví hodnotu Y-ovej súradnice
     */
    void setY(int y);

    /**
     * Getter pre informáciu o tom, že hráč už interaktoval s objektom
     * @return vráti true v prípade, že hráč s objektom interaktoval, false ak nie
     */
    boolean getInteractedWith();

    /**
     * nastaví objektu boolean interactedWith na true
     */
    void setInteractedWith();

    /**
     *
     * @return vráti výšku obrázka objektu
     */
    int getHeight();

    /**
     * Getter pre výšku
     * @return vráti šírku obrázka objektu
     */
    int getWidth();

    /**
     * Getter pre obrázok
     * @return vráti obrázok objektu
     */
    BufferedImage getImage();

    /**
     * zastaví animáciu objektu
     */
    void stopAnimation();

    /**
     * sprostredkuje interakciu objektu s hráčom
     */
    void interaction();
}
