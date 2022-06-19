package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.glfw.GLFW;

import engine.graphics.MasterRenderer;
import engine.graphics.TerrainMaterial;
import engine.graphics.Mesh;
import engine.io.Input;
import engine.io.Window;
import engine.io.loaders.OBJFileLoader;
import engine.objects.Camera;
import engine.objects.GameObject;
import engine.objects.Light;
import engine.objects.Player;
import engine.maths.Vector3f;
import terrains.Terrain;

public class Main implements Runnable {
  public Window window;
  public MasterRenderer renderer;
  public final int WIDTH = 1280, HEIGHT = 760;
  
  public List<GameObject> allObjects = new ArrayList<GameObject>();
  public List<Mesh> allMeshes = new ArrayList<Mesh>();
  
  public Player player;
  
  public Light light;
  public Camera camera;
  public Terrain terrain1;
  

  
  // Initialize variables
  public void init() {
    window = new Window(WIDTH, HEIGHT, "Game");
    window.setBackgroundColor(167f / 255f, 214f / 255f, 217f / 255f);
    window.create();
    
    loadObjects();
    
    renderer = new MasterRenderer(window);
    renderer.create();
  }
  
  // Game Loop
  public void run() {
    init();
    while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
      update();
      render();
      if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
      if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
    }
    close();
  }
  
  // Update variables
  private void update() {
    window.update();
    player.move();
    camera.updateThirdPerson(player);

  }
  
  // Render Objects and Terrain
  private void render() {
    for (GameObject object : allObjects) {
      renderer.processObject(object);
    }
    
    renderer.proceesTerrain(terrain1);
    
    renderer.render(light, camera);
    window.swapBuffers();
  }
  
  // Destroys variables
  private void close() {
    window.destroy();
    
    for (Mesh mesh : allMeshes) {
      mesh.destroy();
    }
    
    terrain1.destroy();
    renderer.destroy();
  }
  
  // Creates and loads all entites into world
  private void loadObjects() {
    light = new Light(new Vector3f(0, 25, 0), new Vector3f(1, 1, 1));
    
    camera = new Camera(new Vector3f(0, 1, 2), new Vector3f(0, 0, 0));
    
    terrain1 = new Terrain(-0.5f, -0.5f, new TerrainMaterial("textures/terrainTexture1.png", 
                                                             "textures/terrainTexture2.png", 
                                                             "textures/terrainTexture3.png", 
                                                             "textures/terrainTexture4.png", 
                                                             "textures/blendMap.png"));
    terrain1.create();
    
    Mesh tree = OBJFileLoader.loadOBJ("models/tree/tree.obj", "models/tree/tree.png");
    Mesh grass = OBJFileLoader.loadOBJ("models/grass/grassModel.obj",  "models/grass/grass.png");
    Mesh playerMesh = OBJFileLoader.loadOBJ("models/person/player.obj",  "models/person/grey.png");
    
    
    allMeshes.add(tree);
    allMeshes.add(grass);
    allMeshes.add(playerMesh);

    
    for (Mesh mesh : allMeshes) {
      mesh.create();
    }
    
    grass.getMaterial().setHasTransparency(true);
    grass.getMaterial().setUseFakeLighting(true);
    
    
    Random random = new Random();
    for (int i = 0; i < 1000; i++) {
      allObjects.add(new GameObject(new Vector3f(random.nextFloat() * 200 - 100, 0, random.nextFloat() * -200 + 100), 
          new Vector3f(0, 0, 0), 2.5f, tree));
      allObjects.add(new GameObject(new Vector3f(random.nextFloat() * 200 - 100, 0, random.nextFloat() * -200 + 100), 
          new Vector3f(0, 0, 0), 0.4f, grass));
    }
    
    player = new Player(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 0.1f, playerMesh);
    allObjects.add(player);
  }
  
  public static void main(String[] args) {
    new Main().run();
  }
}