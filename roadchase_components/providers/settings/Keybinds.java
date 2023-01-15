package de.hhn.it.pp.components.roadchase.providers.settings;

/**
 * Settings submenu to change the possible controls.
 */
public class Keybinds {

  private static String moveLeftKey = "A";
  private static String moveRightKey = "D";
  private static String activateSloMoKey = "Q";
  private static String activateShieldKey = "E";

  public static void setMoveLeft(String moveLeftKey) {
    Keybinds.moveLeftKey = moveLeftKey;
  }

  public static String getMoveLeft() {
    return moveLeftKey;
  }

  public static void setMoveRight(String moveRightKey) {
    Keybinds.moveRightKey = moveRightKey;
  }

  public static String getMoveRight() {
    return moveRightKey;
  }

  public static void setActivateSloMoKey(String activateSloMoKey) {
    Keybinds.activateSloMoKey = activateSloMoKey;
  }

  public static String getActivateSloMoKey() {
    return activateSloMoKey;
  }


  public static void setActivateShieldKey(String activateShieldKey) {
    Keybinds.activateShieldKey = activateShieldKey;
  }

  public static String getActivateShieldKey() {
    return activateShieldKey;
  }
}
