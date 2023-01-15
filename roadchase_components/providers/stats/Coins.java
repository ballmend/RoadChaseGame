package de.hhn.it.pp.components.roadchase.providers.stats;

/**
 * This class is used to configure the in-game currency.
 */
public class Coins {

  private static int coins = 0;

  public Coins(int coins) {
    Coins.coins = coins;
  }

  public static void setCoins(int coins) {
    Coins.coins = coins;
  }

  public static int getCoins() {
    return coins;
  }
}
