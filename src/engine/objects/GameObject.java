package engine.objects;

import engine.maths.Vector3f;

import engine.graphics.Mesh;
import engine.maths.Transformations;

public class GameObject {
  private Vector3f position, rotation;
  private float scale;
  private Mesh mesh;
  
  public GameObject(Vector3f position, Vector3f rotation, float scale, Mesh mesh) {
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;
    this.mesh = mesh;
  }
  
  public void update() {
    position.z -= 0.05f;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }
  
  public void addPosition(Vector3f position) {
    this.position = Transformations.addVector3f(this.position, position);
  }

  public Vector3f getRotation() {
    return rotation;
  }

  public void setRotation(Vector3f rotation) {
    this.rotation = rotation;
  }
  
  public void addRotation(Vector3f rotation) {
    this.rotation = Transformations.addVector3f(this.rotation, rotation);
  }

  public float getScale() {
    return scale;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public Mesh getMesh() {
    return mesh;
  }
}
