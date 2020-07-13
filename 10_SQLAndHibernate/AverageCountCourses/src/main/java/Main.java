import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox?&serverTimezone=UTC";
        String user = "root";
        String password = "testtest";
        System.setProperty("console.encoding","utf8mb4");
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String sql = "Select course_id\n" +
                    "from Subscriptions s \n" +
                    "where YEAR(s.subscription_date) = 2018\n" +
                    "group by course_id\n" +
                    "order by course_id";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())    {
                String course_id = resultSet.getString("course_id");
                sql = "Select max(month(subscription_date)) as month\n" +
                        "from Subscriptions\n" +
                        "where year(subscription_date) = 2018 \n" +
                        "and\n" +
                        "course_id = " + course_id;
                String collumn = "month";
                Integer maxMonth = Integer.parseInt(querySQL(connection, sql, collumn));
                sql = "Select min(month(subscription_date)) as month\n" +
                        "from Subscriptions\n" +
                        "where year(subscription_date) = 2018 \n" +
                        "and\n" +
                        "course_id = " + course_id;
                Integer minMonth = Integer.parseInt(querySQL(connection, sql, collumn));
                Integer countMonth = maxMonth - minMonth + 1;
                sql = "Select count(month(subscription_date)) as count\n" +
                        "from Subscriptions\n" +
                        "where year(subscription_date) = 2018 \n" +
                        "and\n" +
                        "course_id = " + course_id;
                collumn = "count";
                Integer sumSubscr = Integer.parseInt(querySQL(connection, sql, collumn));
                sql = "Select name from Courses where id = " + course_id;
                collumn = "name";
                String nameCourse = querySQL(connection, sql, collumn);
                Double aveCount = (double)sumSubscr/countMonth;
                System.out.println(nameCourse + " " + aveCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String querySQL (Connection connection, String sql, String collumn) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        String resultString = resultSet.getString(collumn);
        return resultString;
    }
}
