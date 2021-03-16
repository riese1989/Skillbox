import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Station {

  private int id;
  private List<Date> visits = new ArrayList<>();
  private WorkTime workTime = new WorkTime();

  public WorkTime getWorkTime() {
    return workTime;
  }

  public Station(int id) {
    this.id = id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void addVisit(Date visit) {
    visits.add(visit);
  }

  public List<Date> getVisits() {
    return visits;
  }
}
