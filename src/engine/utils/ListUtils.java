package engine.utils;

import java.util.List;

import engine.graphics.Mesh;

public class ListUtils {
  
  public static List<Mesh> combineMeshLists(List<Mesh> list1, Mesh[] list2) {
    for (int i = 0; i < list2.length; i++) {
      list1.add(list2[i]);
    }
    return list1;
  }
  
  public static float[] listToArray(List<Float> list) {
    int size = list != null ? list.size() : 0;
    float[] floatArr = new float[size];
    for (int i = 0; i < size; i++) {
        floatArr[i] = list.get(i);
    }
    return floatArr;
  }
  
  public static int[] listIntToArray(List<Integer> list) {
    int[] result = list.stream().mapToInt((Integer v) -> v).toArray();
    return result;
  }
}
