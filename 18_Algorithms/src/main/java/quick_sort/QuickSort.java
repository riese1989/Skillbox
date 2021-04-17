package quick_sort;

public class QuickSort {

  public static void sort(int[] array) {
    if (array.length <= 1) {
      return;
    }
    sort(array, 0, array.length - 1);
  }

  private static void sort(int[] array, int from, int to) {
    if (from < to) {
      int pivot = partition(array, from, to);
      sort(array, from, pivot - 1);
      sort(array, pivot + 1, to);
    }
  }

  private static int partition(int[] array, int from, int to) {
    int temp;
    int currentElem = from;
    for (int id = from + 1; id <= to; id++) {
        if (array[id] < array[currentElem])    {
            for (int id2 = id; id2 > currentElem; id2--) {
              temp = array[id2];
              array[id2] = array[id2-1];
              array[id2-1] = temp;
            }
            currentElem++;
        }
    }
    return currentElem;
  }

}

//1 5 0 4
