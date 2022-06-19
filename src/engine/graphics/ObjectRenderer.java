package engine.graphics;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import engine.maths.Transformations;
import engine.objects.GameObject;

public class ObjectRenderer {
  private Shader shader;
  
  public ObjectRenderer(Shader shader) {
    this.shader = shader;
  }
  
  public void render(Map<Mesh, List<GameObject>> objects) {
    for (Mesh mesh : objects.keySet()) {
      prepareMesh(mesh);
      List<GameObject> batch = objects.get(mesh);
      for (GameObject object : batch) {
        prepareObject(object);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
      }
      unbindMesh();
    }
  }
  
  private void prepareMesh(Mesh mesh) {
    if (mesh.getMaterial().isHasTransparency()) {
      MasterRenderer.disableCulling();
    }
    GL30.glBindVertexArray(mesh.getVAO());
    GL30.glEnableVertexAttribArray(0);
    GL30.glEnableVertexAttribArray(1);
    GL30.glEnableVertexAttribArray(2);
    mesh.getMaterial().bindTextures();
    shader.setUniform("shineDamper", mesh.getMaterial().getShineDamper());
    shader.setUniform("reflectivity", mesh.getMaterial().getReflectivity());
    shader.setUniform("useFakeLighting", mesh.getMaterial().isUseFakeLighting());
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
  }
  
  private void unbindMesh() {
    MasterRenderer.enableCulling();
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL30.glDisableVertexAttribArray(0);
    GL30.glDisableVertexAttribArray(1);
    GL30.glDisableVertexAttribArray(2);
    GL30.glBindVertexArray(0);
  }
  
  private void prepareObject(GameObject object) {
    shader.setUniform("model", Transformations.transform(object.getPosition(), object.getRotation(), object.getScale()));
  }
}