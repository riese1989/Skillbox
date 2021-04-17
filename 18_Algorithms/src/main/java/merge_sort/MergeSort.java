package merge_sort;

public class MergeSort {

  public static void mergeSort(int[] array) {
    int n = array.length;
    if (n < 2) {
      return;
    }
    int middle = n / 2;
    int[] leftArray = new int[middle];
    int[] rightArray = new int[n - middle];

    for (int i = 0; i < middle; i++) {
      leftArray[i] = array[i];
    }
    for (int i = middle; i < n; i++) {
      rightArray[i - middle] = array[i];
    }
    mergeSort(leftArray);
    mergeSort(rightArray);

    merge(array, leftArray, rightArray);
  }

  private static void merge(int[] array, int[] left, int[] right) {
    int idLeft = 0, idRight = 0;
    for (int idArray = 0; idArray <= array.length - 1; idArray++) {
        if (idLeft > left.length - 1 || idRight > right.length - 1) {
            if (idLeft > left.length - 1)   {
                array[idArray] = right[idRight];
                idRight++;
                continue;
            }
            array[idArray] = left[idLeft];
            idLeft++;
            continue;
        }
      if (left[idLeft] <= right[idRight]) {
        array[idArray] = left[idLeft];
        if (idLeft <= left.length - 1) {
          idLeft++;
        }
        continue;
      }
      if (left[idLeft] > right[idRight]) {
        array[idArray] = right[idRight];
        if (idRight <= right.length - 1) {
          idRight++;
        }
      }
    }
  }
}
