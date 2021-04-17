package bubble_sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import binary_search.BinarySearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BubbleSortTest {

  @Test
  @DisplayName("Сортировка отсортированного массива")
  public void sortSortedArray() {
    int[] array = {1, 2, 3};
    int[] sortedArray = BubbleSort.sort(array);
    assertTrue(compareArrays(array, sortedArray));

  }

  @Test
  @DisplayName("Сортировка неотсортированного массива")
  public void sortNoneSortedArray() {
    int[] array = {2, 1, 3};
    int[] sortedArray = BubbleSort.sort(array);
    assertTrue(compareArrays(array, sortedArray));

  }

  @Test
  @DisplayName("Массива из одного элемента")
  public void sortSimpleArray() {
    int[] array = {3};
    int[] sortedArray = BubbleSort.sort(array);
    assertTrue(compareArrays(array, sortedArray));
  }

  private boolean compareArrays(int[] array1, int[] array2) {
    if (array1.length != array2.length) {
      return false;
    }
    for (int i = 0; i < array1.length; i++) {
      if (array1[i] != array2[i]) {
        return false;
      }
    }
    return true;
  }

}
