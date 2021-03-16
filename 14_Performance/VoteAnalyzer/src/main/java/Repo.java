import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Repo {

  private List<Station> stations = new ArrayList<>();
  private Set<Voter> voters = new LinkedHashSet<>();

  public synchronized void addStation(int id, Date dateVisit) {
    Station station = getStation(id);
    station.addVisit(dateVisit);
  }

  public List<Station> getStations() {
    return stations;
  }

  public Set<Voter> getVoters() {
    return voters;
  }

  public void addVoter(Voter voter) {
    voters.add(voter);
  }

  private synchronized Station getStation(int id) {
    for (Station station : stations) {
      if (station.getId() == id) {
        return station;
      }
    }
    Station station = new Station(id);
    stations.add(station);
    return station;
  }

  public Voter getVoter(Voter voter)  {
    for (Voter voter1 : voters) {
      if (voter1.equals(voter))  {
        return voter1;
      }
    }
    return null;
  }
}
