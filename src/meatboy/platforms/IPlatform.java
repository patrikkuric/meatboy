package meatboy.platforms;

import meatboy.objects.IObject;

import java.awt.image.BufferedImage;

/**
 * Interface pre platformy
 */
public interface IPlatform {

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
     * Getter pre informáciu o tom, že hráč už interaktoval s platformou
     * @return vráti true v prípade, že hráč s platformou interaktoval, false ak nie
     */
    boolean getInteractedWith();

    /**
     * nastaví objektu boolean interactedWith na true
     */
    void setInteractedWith();

    /**
     * vykoná reakciu platformy na interakciu od hráča
     */
    void special();

    /**
     * Getter pre šírku platformy
     * @return vráti šírku platformy
     */
    int getWidth();

    /**
     * Getter pre obrázok
     * @return vráti obrázok objektu
     */
    BufferedImage getImage();

    /**
     * zastaví animáciu platformy
     */
    void stopAnimation();

    /**
     * pridá objekt na platformu
     * @param object - objekt typu IObject, ktorý sa priradí platforme
     */
    void addObject(IObject object);
}
