package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;

import engine.utils.FileUtils;

public class ObjectMaterial extends Material {
  
  private Texture texture;
  private Texture normalMap;
  
  private int textureID, normalMapID; 
  
  // Constructor
  public ObjectMaterial(String path) {
    texture = FileUtils.loadTexture(path);
  }
  
  
  public void create() {
    textureID = texture.getTextureID();
    if (normalMap != null) {
      normalMapID = normalMap.getTextureID();
    }
  }
  
  public void bindTextures() {
    if (texture != null) {
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GL13.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }
    if (normalMap != null) {
      GL13.glActiveTexture(GL13.GL_TEXTURE1);
      GL13.glBindTexture(GL11.GL_TEXTURE_2D, normalMapID);
    }
  }
  
  public void destroy() {
    GL13.glDeleteTextures(textureID);
    if (normalMap != null) {
      GL13.glDeleteTextures(normalMapID);
    }
  }

  public int getTextureID() {
    return textureID;
  }
  
  public boolean hasNormalMap() {
    return this.normalMap != null;
  }
  
  public Texture getNormalMap() {
    return normalMap;
  }
  
  public void setNormalMap(Texture normalMap) {
    this.normalMap = normalMap;
  }
}
