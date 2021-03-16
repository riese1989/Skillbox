import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;

public class Loader {

  private static StringBuilder stringWorkTimes = new StringBuilder();
  private static Repo repo = new Repo();

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    String fileName = "res/data-18M.xml";
    parseFile(fileName);
    System.out.println("Voting station work times: ");
    for (Station station : repo.getStations()) {
      stringWorkTimes.append(station.getId());
      stringWorkTimes.append(" ");
      WorkTime workTime = new WorkTime();
      for (Date visit : station.getVisits()) {
        workTime.addVisitTime(visit.getTime());
      }
      stringWorkTimes.append(workTime);
      stringWorkTimes.append("\n");
    }
    System.out.println(stringWorkTimes.toString());
    executeSQL();
    long countDoubl = DBConnection.countDuplicatedVoters();
    System.out.println("Duplicated voters: " + countDoubl);
    System.out.println(System.currentTimeMillis() - start);
  }

  private static void parseFile(String fileName) throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    SaxParse saxp = new SaxParse("voter", repo);
    parser.parse(new File(fileName), saxp);
  }

  private static void executeSQL()  {
    StringBuilder sql = new StringBuilder();
    sql.append("INSERT INTO voter_count (name, birthDate, `count`) VALUES ");
    Iterator<Voter> iterator = repo.getVoters().iterator();
    while (iterator.hasNext())  {
      Voter voter = iterator.next();
      sql.append("(");
      sql.append("'");
      sql.append(voter.getName());
      sql.append("'");
      sql.append(", ");
      sql.append("'");
      sql.append(General.BIRTH_DAY_FORMAT_DB.format(voter.getBirthDay()));
      sql.append("'");
      sql.append(", ");
      sql.append(voter.getCountVot());
      sql.append(")");
      if (sql.length() > 1000)  {
        sql.append(";");
        WriterDB writerDB = new WriterDB(sql.toString());
        writerDB.run();
        sql = new StringBuilder();
        sql.append("INSERT INTO voter_count (name, birthDate, `count`) VALUES ");
        continue;
      }
      if (iterator.hasNext()) {
        sql.append(", ");
      }
      else  {
        sql.append(";");
      }
    }
  }


}