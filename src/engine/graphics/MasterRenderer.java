package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import engine.io.Window;
import engine.maths.Transformations;
import engine.objects.Camera;
import engine.objects.GameObject;
import engine.objects.Light;
import terrains.Terrain;

public class MasterRenderer {
  private Window window;

  private Shader objectShader = new Shader("/shaders/mainVertex.glsl",
      "/shaders/mainFragment.glsl");
  private Shader terrainShader = new Shader("/shaders/terrainVertex.glsl",
      "/shaders/terrainFragment.glsl");

  private ObjectRenderer objectRenderer;
  private TerrainRenderer terrainRenderer;

  private Map<Mesh, List<GameObject>> objects = new HashMap<Mesh, List<GameObject>>();
  private List<Terrain> terrains = new ArrayList<Terrain>();

  public MasterRenderer(Window window) {
    enableCulling();
    this.window = window;
    objectRenderer = new ObjectRenderer(objectShader);
    terrainRenderer = new TerrainRenderer(terrainShader);
  }

  public void render(Light light, Camera camera) {
    objectShader.bind();
    objectShader.setUniform("view", Transformations.view(camera.getPosition(), camera.getRotation()));
    objectShader.setUniform("projection", window.getProjectionMatrix());
    objectShader.setUniform("skyColor", window.getBackgroundColor());
    objectShader.setUniform("lightPosition", light.getPosition());
    objectShader.setUniform("lightColor", light.getColor());
    objectRenderer.render(objects);
    objectShader.unbind();

    terrainShader.bind();
    terrainShader.setUniform("view", Transformations.view(camera.getPosition(), camera.getRotation()));
    terrainShader.setUniform("projection", window.getProjectionMatrix());
    terrainShader.setUniform("skyColor", window.getBackgroundColor());
    terrainShader.setUniform("lightPosition", light.getPosition());
    terrainShader.setUniform("lightColor", light.getColor());
    terrainRenderer.render(terrains);
    terrainShader.unbind();

    terrains.clear();
    objects.clear();
  }

  public void proceesTerrain(Terrain terrain) {
    terrains.add(terrain);
  }

  public void processObject(GameObject object) {
    Mesh mesh = object.getMesh();
    List<GameObject> batch = objects.get(mesh);
    if (batch != null) {
      batch.add(object);
    }
    else {
      List<GameObject> newBatch = new ArrayList<GameObject>();
      newBatch.add(object);
      objects.put(mesh, newBatch);
    }
  }

  public static void enableCulling() {
    GL11.glCullFace(GL11.GL_BACK);
    GL11.glEnable(GL11.GL_CULL_FACE);
  }

  public static void disableCulling() {
    GL11.glDisable(GL11.GL_CULL_FACE);
  }

  public void create() {
    objectShader.create();
    terrainShader.create();
  }

  public void destroy() {
    objectShader.destroy();
    terrainShader.destroy();
  }
}
