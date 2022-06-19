package engine.maths;

public class Transformations {
  
  public static final int MATRIX_SIZE = 4;

  public static Matrix4f translate(Vector3f translation) {
    Matrix4f result = new Matrix4f();
    result.setIdentity();
    result.set(3, 0, translation.x);
    result.set(3, 1, translation.y);
    result.set(3, 2, translation.z);
    return result;
  }

  public static Matrix4f rotate(float angle, Vector3f axis) {
    Matrix4f result = new Matrix4f();
    result.setIdentity();
    float cos = (float) Math.cos(Math.toRadians(angle));
    float sin = (float) Math.sin(Math.toRadians(angle));
    float C = 1 - cos;
    result.set(0, 0, cos + axis.x * axis.x * C);
    result.set(0, 1, axis.x * axis.y * C - axis.z * sin);
    result.set(0, 2, axis.x * axis.z * C + axis.y * sin);
		result.set(1, 0, axis.y * axis.x * C + axis.z * sin);
		result.set(1, 1, cos + axis.y * axis.y * C);
		result.set(1, 2, axis.y * axis.z * C - axis.x * sin);
		result.set(2, 0, axis.z * axis.x * C - axis.y * sin);
		result.set(2, 1, axis.z * axis.y * C + axis.x * sin);
		result.set(2, 2, cos + axis.z * axis.z * C);
    return result;
  }

  public static Matrix4f scale(Vector3f scalar) {
    Matrix4f result = new Matrix4f();
    result.setIdentity();
    result.set(0, 0, scalar.x);
    result.set(1, 1, scalar.y);
    result.set(2, 2, scalar.z);
    return result;
  }
  
  public static Matrix4f transform(Vector3f position, Vector3f rotation, float scale) {
    Matrix4f translationMatrix = translate(position);
    Matrix4f rotXMatrix = rotate(rotation.x, new Vector3f(1, 0, 0));
    Matrix4f rotYMatrix = rotate(rotation.y, new Vector3f(0, 1, 0));
    Matrix4f rotZMatrix = rotate(rotation.z, new Vector3f(0, 0, 1));
    Matrix4f scaleMatrix = scale(new Vector3f(scale, scale, scale));
    Matrix4f rotationMatrix = Matrix4f.multiply(rotXMatrix, Matrix4f.multiply(rotYMatrix, rotZMatrix));
    Matrix4f result = Matrix4f.multiply(translationMatrix, Matrix4f.multiply(rotationMatrix, scaleMatrix));
    return result;
  }

  public static Matrix4f projection(float fov, float aspect, float near, float far) {
    Matrix4f result = new Matrix4f();
    result.setIdentity();
    float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
    float range = far - near;
    result.set(0, 0, 1.0f / (aspect * tanFOV));
    result.set(1, 1, 1.0f / tanFOV);
    result.set(2, 2, -((far + near) / range));
    result.set(2, 3, -1.0f);
    result.set(3, 2, -((2 * far * near) / range));
    result.set(3, 3, 0.0f);
    return result;
  }

  public static Matrix4f view(Vector3f position, Vector3f rotation) {
    Vector3f negativePos = new Vector3f(-position.x,-position.y,-position.z);
    Matrix4f translationMatrix = translate(negativePos);
    Matrix4f rotXMatrix = rotate(rotation.x, new Vector3f(1, 0, 0));
    Matrix4f rotYMatrix = rotate(rotation.y, new Vector3f(0, 1, 0));
    Matrix4f rotZMatrix = rotate(rotation.z, new Vector3f(0, 0, 1));
    Matrix4f rotationMatrix = Matrix4f.multiply(rotXMatrix, Matrix4f.multiply(rotYMatrix, rotZMatrix));
    Matrix4f result = Matrix4f.multiply(translationMatrix, rotationMatrix);
    return result;
  }
  
  public static Vector3f addVector3f(Vector3f vec1, Vector3f vec2) {
    return new Vector3f(vec1.x + vec2.x, vec1.y + vec2.y, vec1.z + vec2.z);
  }
}
