import java.util.Comparator;

public class StationComparator implements Comparator<Station> {

  @Override
  public int compare(Station o1, Station o2) {
    return Integer.compare(o1.getId(), o2.getId());
  }
}
