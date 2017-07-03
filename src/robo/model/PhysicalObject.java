package robo.model;

/**
 * The physical representation of an object in 3D space.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public abstract class PhysicalObject extends Entity {
  // 3D space position (relative to the center of the object)
  protected float x = 0;
  protected float y = 0;
  protected float z = 0;
  // GPS coordinates
  protected float latitude;
  protected float longitude;
  // vectorial velocity values over the 3D space
  protected float vx = 0;
  protected float vy = 0;
  protected float vz = 0;
  // acceleration values over the 3D space
  protected float ax = 0;
  protected float ay = 0;
  protected float az = 0;
  // sizes of the bounding box of this object
  protected float width = 0;
  protected float height = 0;
  protected float depth = 0;
}