package rabin_karp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class RabinKarpExtended {

  private String text;
  private TreeMap<Integer, Integer> number2position = new TreeMap<>();
  private HashMap<Character, Integer> alphabet = new HashMap<>();

  public RabinKarpExtended(String text) {
    this.text = text;
    createIndex();
  }

  public List<Integer> search(String query) {
    ArrayList<Integer> indicesPattern = createIndexPattern(query);
    ArrayList<Integer> indices = new ArrayList<>();
    for (Integer position = 0; position < number2position.size(); position++) {
      int searchPosition = 0;
      while (searchPosition < indicesPattern.size() && position + searchPosition < number2position
          .size()) {
        int currentPosition = position + searchPosition;
        if (indicesPattern.get(searchPosition).equals(number2position.get(currentPosition))) {
          indices.add(currentPosition);
          searchPosition++;
          continue;
        } else {
          indices = new ArrayList<>();
          break;
        }
      }
      if (searchPosition == indicesPattern.size()) {
        return indices;
      }
    }
    //TODO: implement search alogorithm
    return new ArrayList<Integer>();
  }

  private void createIndex() {
    char[] array = text.toCharArray();
    List<Character> uniqueSymbols = getUniqueSymbols(array);
    Integer position = 0;
    createAlphabet(uniqueSymbols);
    if (checkSize(alphabet)) {
      for (char elem : array) {
        number2position.put(position, alphabet.get(elem));
        position++;
      }
    }
  }
  private List<Character> getUniqueSymbols(char[] array) {
    Character[] newArray = IntStream.range(0, array.length)
        .mapToObj(i -> array[i])
        .toArray(Character[]::new);
    Set<Character> uniqueValue = new LinkedHashSet<>(Arrays.asList(newArray));
    return new ArrayList<>(uniqueValue);
  }

  private HashMap<Character, Integer> createAlphabet(List<Character> symbols) {
    Integer number = 1;
    for (Character symbol : symbols) {
      alphabet.put(symbol, number);
      number++;
    }
    return alphabet;
  }

  private boolean checkSize(HashMap<Character, Integer> numberSymb) {
    if (numberSymb.size() >= 10) {
      throw new RuntimeException();
    }
    return true;
  }

  private ArrayList<Integer> createIndexPattern(String query) {
    ArrayList<Integer> indexPattern = new ArrayList<>();
    char[] symbols = query.toCharArray();
    for (char symbol : symbols) {
      Integer codeSymbol = alphabet.get(symbol);
      if (codeSymbol == null) {
        return new ArrayList<>();
      }
      indexPattern.add(alphabet.get(symbol));
    }
    return indexPattern;
  }

}