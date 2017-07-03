package robo.model.event;



public interface EventListener {
  /**
   * Notify a listener about an occurred event for which type was registered
   * before.
   * 
   * @param event
   *          the occurred event
   */
  public void on( Event< ?> event);
}
