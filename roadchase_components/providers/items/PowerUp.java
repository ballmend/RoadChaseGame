package de.hhn.it.pp.components.roadchase.providers.items;

/**
 * This class is used to add and configure power-ups in the game.
 */
public class PowerUp {

  public static int shield = 0;
  public static int slowMotion = 0;
  private static boolean shieldInUse = false;
  private static boolean slowMotionInUse = false;

  public static boolean igotaslowmotionPowerup() {
    return slowMotionInUse;
  }

  public static void setUgotSlowMotionLeft(boolean slowMotionInUse) {
    PowerUp.slowMotionInUse = slowMotionInUse;
  }

  public static boolean ugotShieldsLeft() {
    return shieldInUse;
  }

  public static void setUgotShieldsLeft(boolean shieldInUse) {
    PowerUp.shieldInUse = shieldInUse;
  }

  public static int getShield() {
    return shield;
  }

  public static void setShield(int shield) {
    PowerUp.shield = shield;
  }

  public static int getSlowMotion() {
    return slowMotion;
  }

  public static void setSlowMotion(int slowMotion) {
    PowerUp.slowMotion = slowMotion;
  }

}
