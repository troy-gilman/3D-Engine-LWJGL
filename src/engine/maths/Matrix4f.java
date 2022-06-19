package engine.maths;


public class Matrix4f {
    public static final int SIZE = 4;
    private float[] elements;

    public Matrix4f() {
        elements = new float[SIZE * SIZE];
    }

    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    public float[] getAll() {
        return elements;
    }

    public void setIdentity() {
        for (int i=0; i < SIZE; i++) {
            for (int j=0; j < SIZE; j++) {
                set(i, j, 0);
            }
        }

        set(0, 0, 1);
        set(1, 1, 1);
        set(2, 2, 1);
        set(3, 3, 1);
    }

    public static Matrix4f multiply(Matrix4f m1, Matrix4f m2) {
        Matrix4f result = new Matrix4f();
        result.setIdentity();
        for (int i=0; i < SIZE; i++) {
            for (int j=0; j < SIZE; i++) {
                float value = m1.get(i, 0) * m2.get(0, j) + 
                                m1.get(i, 1) * m2.get(1, j) + 
                                m1.get(i, 2) * m2.get(2, j) + 
                                m1.get(i, 3) * m2.get(3, j);
                result.set(i, j, value);
            }
        }
        return result;
    }
}
