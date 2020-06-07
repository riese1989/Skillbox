import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String pathToCSVFile = "CSV/movementList.csv";
        List<String> lines = Files.readAllLines(Paths.get(pathToCSVFile));
        List<StringStatement> statements = constr(lines);
        Double sumComing = statements.stream().map(statement -> statement.getComing()).reduce(0.0,Double::sum);
        System.out.println("Сумма прихода " + sumComing);
        Double sumConsumption = statements.stream().map(statement -> statement.getConsumption()).reduce(0.0,Double::sum);
        System.out.println("Сумма расходов " + sumConsumption);
       Map<String, Double> mapStataments = statements.stream().filter(stat -> stat.getConsumption() > 0.0)
               .collect(Collectors.groupingBy(StringStatement::getShortDescription,
                Collectors.summingDouble(StringStatement::getConsumption)));
       mapStataments.forEach((d,s) -> System.out.println(d + " " + s ));
    }


    public static List<StringStatement> constr (List <String> lines) throws ParseException {
        List<StringStatement> statements = new ArrayList<>();
        int i = 0;
        for (String line : lines)   {
            if (i == 0) {
                i++;
                continue;
            }
            StringStatement stat;
            Double consumption;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
            String[] arr = line.split(",");
            String type = arr[0];
            String numberAccount = arr[1];
            String currency = arr[2];
            Date operationDate = formatter.parse(arr[3]);
            String reference = arr[4];
            String description = arr[5];
            Double coming = Double.parseDouble(arr[6].replace(",",".").replace("\"",""));
            if(arr.length == 8)  {
                consumption = Double.parseDouble(arr[7].replace(",",".").replace("\"",""));
            }
            else    {
                consumption = 0.0;
            }
            stat = new StringStatement(type,numberAccount,currency,operationDate,reference,description,coming,consumption);

            statements.add(stat);
        }
        return statements;
    }
}
