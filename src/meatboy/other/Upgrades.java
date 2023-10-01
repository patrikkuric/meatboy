package meatboy.other;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda Upgrades - vylepšenia pre pohyb hráča
 * @author Patrik Kuric
 */

public class Upgrades {

    //upgrady, true = kúpený
    private boolean jumpBoost1;
    private boolean jumpBoost2;
    private boolean featherfalling;
    private boolean agility1;
    private boolean agility2;

    /**
     * Konštruktor triedy Upgrades, nastaví všetky vylepšenia na false - nezakúpené
     */
    public Upgrades() {
        this.jumpBoost1 = false;
        this.jumpBoost2 = false;
        this.featherfalling = false;
        this.agility1 = false;
        this.agility2 = false;
    }

    /**
     * vráti všetky vylepšenia do pôvodného stavu
     */
    public void reset() {
        this.jumpBoost1 = false;
        this.jumpBoost2 = false;
        this.featherfalling = false;
        this.agility1 = false;
        this.agility2 = false;
    }

    /**
     * Getter pre vylepšenie JumpBoost úrovne 1
     * @return vráti true v prípade, že hráč má zakúpené vylepšenie JumpBoost1, false ak nie
     */
    public boolean getJumpBoost1() {
        return this.jumpBoost1;
    }

    /**
     * Getter pre vylepšenie Agility úrovne 1
     * @return vráti true v prípade, že hráč má zakúpené vylepšenie Agility1, false ak nie
     */
    public boolean getAgility1() {
        return this.agility1;
    }

    /**
     * Slúži na pokus o zakúpenie vylepšenia JumpBoost úrovne 1
     * @return vráti true v prípade, že kúpa vylepšenia prebehla úspešne
     */
    public boolean buyJumpBoost1() {
        if (!this.jumpBoost1) {
            this.jumpBoost1 = true;
            return true;
        }
        return false;
    }

    /**
     * Slúži na pokus o zakúpenie vylepšenia JumpBoost úrovne 2
     * @return vráti true v prípade, že kúpa vylepšenia prebehla úspešne
     */
    public boolean buyJumpBoost2() {
        if (!this.jumpBoost2) {
            this.jumpBoost2 = true;
            return true;
        }
        return false;
    }

    /**
     * Slúži na pokus o zakúpenie vylepšenia FeatherFalling
     * @return vráti true v prípade, že kúpa vylepšenia prebehla úspešne
     */
    public boolean buyFeatherFalling() {
        if (!this.featherfalling) {
            this.featherfalling = true;
            return true;
        }
        return false;
    }

    /**
     * Slúži na pokus o zakúpenie vylepšenia Agility úrovne 1
     * @return vráti true v prípade, že kúpa vylepšenia prebehla úspešne
     */
    public boolean buyAgility1() {
        if (!this.agility1) {
            this.agility1 = true;
            return true;
        }
        return false;
    }

    /**
     * Slúži na pokus o zakúpenie vylepšenia Agility úrovne 2
     * @return vráti true v prípade, že kúpa vylepšenia prebehla úspešne
     */
    public boolean buyAgility2() {
        if (!this.agility2) {
            this.agility2 = true;
            return true;
        }
        return false;
    }
}
