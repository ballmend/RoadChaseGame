package de.hhn.it.pp.components.roadchase.providers.vehicles;

/**
 * This class is used to configure every feature/property of the Car in-game.
 */
public class Car {

  boolean state;
  int speed;
  String skin;

  /**
   * Standard constructor for Car.
   *
   * @param state true: moving, false: not moving
   * @param speed of the car
   * @param skin  of the car
   */
  public Car(boolean state, int speed, String skin) {
    this.state = state;
    this.speed = speed;
    this.skin = skin;
  }

  public Car() {
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public String getSkin() {
    return skin;
  }

  public void setSkin(String skin) {
    this.skin = skin;
  }

  public boolean isState() {
    return state;
  }
}
