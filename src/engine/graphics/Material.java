package engine.graphics;

public abstract class Material {
  
  private float shineDamper = 1;
  private float reflectivity = 0;

  private boolean hasTransparency = false;
  private boolean useFakeLighting = false;
  
  abstract public void create();
  
  abstract public void destroy();
  
  abstract public void bindTextures();
  

  public float getShineDamper() {
    return shineDamper;
  }

  public void setShineDamper(float shineDamper) {
    this.shineDamper = shineDamper;
  }

  public float getReflectivity() {
    return reflectivity;
  }

  public void setReflectivity(float reflectivity) {
    this.reflectivity = reflectivity;
  }

  public boolean isHasTransparency() {
    return hasTransparency;
  }

  public void setHasTransparency(boolean hasTransparency) {
    this.hasTransparency = hasTransparency;
  }

  public boolean isUseFakeLighting() {
    return useFakeLighting;
  }

  public void setUseFakeLighting(boolean useFakeLighting) {
    this.useFakeLighting = useFakeLighting;
  }
}
