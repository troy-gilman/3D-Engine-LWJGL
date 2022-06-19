package engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {
  private float[] vertices, normals, textureCoords;
  private int[] indices;
  private Material material;
  private int vao, pbo, ibo, cbo, tbo, nbo;
  
  public Mesh(float[] vertices, float[] normals, float[] textureCoords, int[] indices) {
    this(vertices, normals, textureCoords, indices, new ObjectMaterial("nothing"));
  }
  
  public Mesh(float[] vertices, float[] normals, float[] texturesCoords, int[] indices, Material material) {
    this.vertices = vertices;
    this.normals = normals;
    this.textureCoords = texturesCoords;
    this.indices = indices;
    this.material = material;
  }
  
  public void create() {
    material.create();
    
    vao = GL30.glGenVertexArrays();
    GL30.glBindVertexArray(vao);

    // Position Buffer
    FloatBuffer positionBuffer = storeDataInFloatBuffer(vertices);
    pbo = storeData(positionBuffer, 0, 3);
    
    // Texture Buffer
    FloatBuffer textureBuffer = storeDataInFloatBuffer(textureCoords);
    tbo = storeData(textureBuffer, 1, 2);
    
    // Normal Buffer
    FloatBuffer normalBuffer = storeDataInFloatBuffer(normals);
    nbo = storeData(normalBuffer, 2, 3);
    
    // Indices Buffer
    IntBuffer indicesBuffer = storeDataInIntBuffer(indices);
    ibo = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
  }
  
  private int storeData(FloatBuffer buffer, int index, int size) {
    int bufferID = GL15.glGenBuffers();
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    return bufferID;
  }
  
  private FloatBuffer storeDataInFloatBuffer(float[] data){
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
  
  private IntBuffer storeDataInIntBuffer(int[] data){
    IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
  
  public void destroy() {
    GL15.glDeleteBuffers(pbo);
    GL15.glDeleteBuffers(cbo);
    GL15.glDeleteBuffers(ibo);
    GL15.glDeleteBuffers(tbo);
    
    GL30.glDeleteVertexArrays(vao);
    
    material.destroy();
  }

  public float[] getVertices() {
    return vertices;
  }

  public float[] getNormals() {
    return normals;
  }

  public float[] getTextureCoords() {
    return textureCoords;
  }

  public int[] getIndices() {
    return indices;
  }

  public int getVAO() {
    return vao;
  }

  public int getPBO() {
    return pbo;
  }
  
  public int getCBO() {
    return cbo;
  }
  
  public int getTBO() {
    return tbo;
  }
  
  public int getNBO() {
    return nbo;
  }

  public int getIBO() {
    return ibo;
  }
  
  public Material getMaterial() {
    return material;
  }
  
  public void setMaterial(Material material) {
    this.material = material;
  }
}