import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.util.Date;

public class SaxParse extends DefaultHandler {

  private String element;
  private Boolean isEntered = false;
  private Repo repo;

  public SaxParse(String element, Repo repo) {
    this.element = element;
    this.repo = repo;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    String name;
    int stationId = -1;
    Date birthday = null, visit = null;
    Voter voter;
    if (qName.equals(element)) {
      isEntered = true;
      name = attributes.getValue(0);
      try {
        birthday = General.BIRTH_DAY_FORMAT.parse(attributes.getValue(1));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      voter = new Voter(name, birthday);
      long sizeBeforeAdd = repo.getVoters().size();
      repo.addVoter(voter);
      long sizeAfterAdd = repo.getVoters().size();
      if (sizeAfterAdd == sizeBeforeAdd)  {
        repo.getVoters().stream().filter(v -> v.equals(voter)).findFirst().get().incVot();
      }
    }
    if (isEntered) {
      int length = attributes.getLength();
      for (int i = 0; i < length; i++) {
        if (attributes.getQName(i).equals("station")) {
          stationId = Integer.parseInt(attributes.getValue(i));
        }
        if (attributes.getQName(i).equals("time")) {
          try {
            visit = General.VISIT_DATE_FORMAT.parse(attributes.getValue(i));
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
        if (stationId != -1 && visit != null) {
          repo.addStation(stationId, visit);
        }
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
    if (qName.equals(element)) {
      isEntered = false;
    }
  }
}
