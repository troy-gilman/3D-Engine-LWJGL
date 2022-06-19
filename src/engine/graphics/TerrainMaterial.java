package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;

import engine.utils.FileUtils;

public class TerrainMaterial extends Material {
  
  private Texture backgroundTexture;
  private Texture rTexture;
  private Texture gTexture;
  private Texture bTexture;
  private Texture blendMap;
  
  private int backgroundTextureID, rTextureID, gTextureID, bTextureID, blendMapID;
  
  // Constructor
  public TerrainMaterial(String backgroundTexturePath, String rTexturePath, String gTexturePath, String bTexturePath, String blendMapPath) {
    backgroundTexture = FileUtils.loadTexture(backgroundTexturePath);
    rTexture = FileUtils.loadTexture(rTexturePath);
    gTexture = FileUtils.loadTexture(gTexturePath);
    bTexture = FileUtils.loadTexture(bTexturePath);
    blendMap = FileUtils.loadTexture(blendMapPath);
  }
  
  
  public void create() {
    backgroundTextureID = backgroundTexture.getTextureID();
    rTextureID = rTexture.getTextureID();
    gTextureID = gTexture.getTextureID();
    bTextureID = bTexture.getTextureID();
    blendMapID = blendMap.getTextureID();
  }
  
  public void bindTextures() {
    if (backgroundTexture != null) {
      GL13.glActiveTexture(GL13.GL_TEXTURE0);
      GL13.glBindTexture(GL11.GL_TEXTURE_2D, backgroundTextureID);
    }
    if (rTexture != null) {
      GL13.glActiveTexture(GL13.GL_TEXTURE1);
      GL13.glBindTexture(GL11.GL_TEXTURE_2D, rTextureID);
    }
    if (gTexture != null) {
      GL13.glActiveTexture(GL13.GL_TEXTURE2);
      GL13.glBindTexture(GL11.GL_TEXTURE_2D, gTextureID);
    }
    if (bTexture != null) {
      GL13.glActiveTexture(GL13.GL_TEXTURE3);
      GL13.glBindTexture(GL11.GL_TEXTURE_2D, bTextureID);
    }
    if (blendMap != null) {
      GL13.glActiveTexture(GL13.GL_TEXTURE4);
      GL13.glBindTexture(GL11.GL_TEXTURE_2D, blendMapID);
    }
  }
  
  public void destroy() {
    GL13.glDeleteTextures(backgroundTextureID);
    GL13.glDeleteTextures(rTextureID);
    GL13.glDeleteTextures(gTextureID);
    GL13.glDeleteTextures(bTextureID);
    GL13.glDeleteTextures(blendMapID);
  }
}
