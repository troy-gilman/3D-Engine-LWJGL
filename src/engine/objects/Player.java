package engine.objects;

import org.lwjgl.glfw.GLFW;
import engine.maths.Vector3f;

import engine.graphics.Mesh;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Transformations;

public class Player extends GameObject {

  private static final float RUN_SPEED = 5;
  private static final float GRAVITY = -50;
  private static final float JUMP_POWER = 15;

  private static final float TERRAIN_HEIGHT = 0;

  private float upwardsSpeed = 0;
  private boolean isInAir = false;

  public Player(Vector3f position, Vector3f rotation, float scale, Mesh mesh) {
    super(position, rotation, scale, mesh);
  }

  public void move() {
    float frameTime = Window.getFrameTimeSeconds();
    
    upwardsSpeed += GRAVITY * frameTime;
    float distance = RUN_SPEED * frameTime;
    
    float x = (float) Math.sin(Math.toRadians(super.getRotation().y)) * distance;
    float z = (float) Math.cos(Math.toRadians(super.getRotation().y)) * distance;
    
    Vector3f newMove = new Vector3f();
    
    if (Input.isKeyDown(GLFW.GLFW_KEY_W))
      newMove = Transformations.addVector3f(newMove, new Vector3f(x, 0, z));
    else if (Input.isKeyDown(GLFW.GLFW_KEY_S))
      newMove = Transformations.addVector3f(newMove, new Vector3f(-x, 0, -z));
    else  newMove = Transformations.addVector3f(newMove, new Vector3f(0, 0, 0));
    
    if (Input.isKeyDown(GLFW.GLFW_KEY_A))
      newMove = Transformations.addVector3f(newMove, new Vector3f(z, 0, -x));
    else if (Input.isKeyDown(GLFW.GLFW_KEY_D))
      newMove = Transformations.addVector3f(newMove, new Vector3f(-z, 0, x));
    else  newMove = Transformations.addVector3f(newMove, new Vector3f(0, 0, 0));
    
    if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
      jump();
    
    

      
    super.addPosition(newMove);
    super.addPosition(new Vector3f(0, upwardsSpeed * frameTime, 0));
    
    if (super.getPosition().y < TERRAIN_HEIGHT) {
      upwardsSpeed = 0;
      isInAir = false;
      super.getPosition().y = TERRAIN_HEIGHT;
    }
  }

  private void jump() {
    if (!isInAir) {
      this.upwardsSpeed = JUMP_POWER;
      isInAir = true;
    }
  }

}
