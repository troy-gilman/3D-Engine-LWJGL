package engine.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class FileUtils {
  private static final String RES_LOC = "resources/";
  
  public static String loadAsString(String path) {
    StringBuilder result = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(FileUtils.class.getResourceAsStream(path)))) {
      String line = "";
      while ((line = reader.readLine()) != null) {
        result.append(line).append("\n");
      }
    }
    catch (IOException e) {
      System.err.println("Couldn't find the file at " + path);
    }

    return result.toString();
  }
  
  public static Texture loadTexture(String filePath) {
    Texture texture = null;
    try {
        texture = TextureLoader.getTexture("PNG", new FileInputStream(RES_LOC + filePath));
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Tried to load texture " + filePath + ", didn't work");
        System.exit(-1);
    }
    return texture;
}
}