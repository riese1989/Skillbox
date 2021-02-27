import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repo {

  private List<Station> stations = new ArrayList<>();
  private ArrayList<Voter> voters = new ArrayList<>();

  public void addStation(int id, Date dateVisit) {
    Station station = getStation(id);
    station.addVisit(dateVisit);
  }

  public List<Station> getStations() {
    return stations;
  }

  public ArrayList<Voter> getVoters() {
    return voters;
  }

  public void addVoter(Voter voter) {
    voters.add(voter);
  }

  private Station getStation(int id) {
    for (Station station : stations) {
      if (station.getId() == id) {
        return station;
      }
    }
    Station station = new Station(id);
    stations.add(station);
    return station;
  }
}
