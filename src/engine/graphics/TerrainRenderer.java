package engine.graphics;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import engine.maths.Vector3f;

import engine.maths.Transformations;
import terrains.Terrain;

public class TerrainRenderer {
  private Shader shader;
  
  public TerrainRenderer(Shader shader) {
    this.shader = shader;
  }
  
  public void render(List<Terrain> terrains) {
    for (Terrain terrain : terrains) {
      prepareMesh(terrain.getMesh());
      prepareTerrain(terrain);
      GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
      unbindMesh();
    }
  }
  
  private void prepareMesh(Mesh mesh) {
    GL30.glBindVertexArray(mesh.getVAO());
    GL30.glEnableVertexAttribArray(0);
    GL30.glEnableVertexAttribArray(1);
    GL30.glEnableVertexAttribArray(2);
    mesh.getMaterial().bindTextures();
    shader.setUniform("shineDamper", mesh.getMaterial().getShineDamper());
    shader.setUniform("reflectivity", mesh.getMaterial().getReflectivity());
    shader.setUniform("backGroundTexture", 0);
    shader.setUniform("rTexture", 1);
    shader.setUniform("gTexture", 2);
    shader.setUniform("bTexture", 3);
    shader.setUniform("blendMap", 4);
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
  }
  
  private void unbindMesh() {
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL30.glDisableVertexAttribArray(0);
    GL30.glDisableVertexAttribArray(1);
    GL30.glDisableVertexAttribArray(2);
    GL30.glBindVertexArray(0);
  }
  
  private void prepareTerrain(Terrain terrain) {
    shader.setUniform("model", Transformations.transform(terrain.getPosition(), new Vector3f(0, 0, 0), 1));
  }
}
