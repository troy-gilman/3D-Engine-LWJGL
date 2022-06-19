package engine.objects;

import org.lwjgl.glfw.GLFW;
import engine.maths.Vector3f;

import engine.io.Input;
import engine.maths.Transformations;

public class Camera {
  private Vector3f position, rotation;
  private float moveSpeed = 0.1f, mouseSensitivity = 0.15f, verticalViewRange = 90;
  private float distance = 4.0f, horizantalAngle = 0, verticalAngle = 0;
  private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;
  private double oldScrollX = 0, oldScrollY = 0, newScrollX, newScrollY;

  public Camera(Vector3f position, Vector3f rotation) {
    this.position = position;
    this.rotation = rotation;
  }

  public void updateFirstPerson() {
    newMouseX = Input.getMouseX();
    newMouseY = Input.getMouseY();
    newScrollX = Input.getScrollX();
    newScrollY = Input.getScrollY();

    float x = (float) Math.sin(Math.toRadians(rotation.y)) * moveSpeed;
    float z = (float) Math.cos(Math.toRadians(rotation.y)) * moveSpeed;

    if (Input.isKeyDown(GLFW.GLFW_KEY_A))
      position = Transformations.addVector3f(position, new Vector3f(-z, 0, x));
    if (Input.isKeyDown(GLFW.GLFW_KEY_D))
      position = Transformations.addVector3f(position, new Vector3f(z, 0, -x));
    if (Input.isKeyDown(GLFW.GLFW_KEY_W))
      position = Transformations.addVector3f(position, new Vector3f(-x, 0, -z));
    if (Input.isKeyDown(GLFW.GLFW_KEY_S))
      position = Transformations.addVector3f(position, new Vector3f(x, 0, z));
    if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
      position = Transformations.addVector3f(position, new Vector3f(0, moveSpeed, 0));
    if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
      position = Transformations.addVector3f(position, new Vector3f(0, -moveSpeed, 0));

    float dx = (float) (newMouseX - oldMouseX);
    float dy = (float) (newMouseY - oldMouseY);

    float newRotationX = rotation.x - dy * mouseSensitivity;
    if (newRotationX > verticalViewRange || newRotationX < -verticalViewRange) {
      dy = 0;
    }

    rotation = Transformations.addVector3f(rotation,
        new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

    oldMouseX = newMouseX;
    oldMouseY = newMouseY;
    oldScrollX = newScrollX;
    oldScrollY = newScrollY;
  }

  public void updateThirdPerson(GameObject object) {
    newMouseX = Input.getMouseX();
    newMouseY = Input.getMouseY();
    newScrollX = Input.getScrollX();
    newScrollY = Input.getScrollY();

    float dxMouse = (float) (newMouseX - oldMouseX);
    float dyMouse = (float) (newMouseY - oldMouseY);
    float dxScroll = (float) (newScrollX - oldScrollX);
    float dyScroll = (float) (newScrollY - oldScrollY);
    
    
    if (verticalAngle - dyMouse * mouseSensitivity > verticalViewRange || 
        verticalAngle - dyMouse * mouseSensitivity < -verticalViewRange) {
      verticalAngle = rotation.x;
    }
    else {
      verticalAngle -= dyMouse * mouseSensitivity;
    }

    horizantalAngle += dxMouse * mouseSensitivity;

    if ((distance - dyScroll / 4) >= 1.0f) {
      distance -= dyScroll / 4;
    } else {
      distance = 1.0f;
    }


    float horizantalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle)));
    float verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle)));

    float xOffset = (float) (horizantalDistance * Math.sin((Math.toRadians(-horizantalAngle))));
    float zOffset = (float) (horizantalDistance * Math.cos((Math.toRadians(-horizantalAngle))));
    


    position.set(object.getPosition().x + xOffset,
        object.getPosition().y - verticalDistance + 1.0f, object.getPosition().z + zOffset);

    rotation.set(verticalAngle, -horizantalAngle, 0);
    
    object.setRotation(new Vector3f(0, -horizantalAngle - 180, 0));

    oldMouseX = newMouseX;
    oldMouseY = newMouseY;
    oldScrollX = newScrollX;
    oldScrollY = newScrollY;
  }

  public Vector3f getPosition() {
    return position;
  }

  public Vector3f getRotation() {
    return rotation;
  }
}