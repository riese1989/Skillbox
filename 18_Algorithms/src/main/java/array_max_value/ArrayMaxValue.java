package array_max_value;

public class ArrayMaxValue {

  public static int getMaxValue(int[] values) throws Exception {
    if (values.length == 0) {
      throw new Exception("Пустой массив");
    }
    int maxValue = values[0];

    for (int i = 1; i < values.length; i++) {
      if (values[i] > maxValue) {
        maxValue = values[i];
      }
    }
    return maxValue;
  }
}