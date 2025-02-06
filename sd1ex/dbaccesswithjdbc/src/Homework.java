import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Homework {
    private static final String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static final String TABLE_NAME = "football";
    private Connection connection;
    private BufferedReader bufferedReader;

    public Homework(){
        this.bufferedReader = new BufferedReader(
                new InputStreamReader(System.in));
    }
    public void setConnection(String user, String password) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",password);

         connection = DriverManager
                .getConnection(CONNECTION_STRING + TABLE_NAME,properties);
    }

    public void getCoachesNames() throws SQLException {
        String query = "select p.first_name,count(pc.coach_id) as \"coaches\" \n" +
                "from players p \n" +
                "join players_coaches pc on p.id = pc.player_id\n" +
                "group by p.id\n" +
                "having \"coaches\" >1\n" +
                "order by \"coaches\" desc;";

        PreparedStatement statement = connection
                .prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s%n %d%n",resultSet.getString(1),
                    resultSet.getInt(2));
        }
    }

    public void getPlayersNames() throws IOException, SQLException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter team id: ");
        int teamId = Integer.parseInt(bufferedReader.readLine());

        String teamName = getEntityNameById(teamId,"teams");
        if(teamName ==null) {
            System.out.printf("No team with id %d",teamId);
            return;
        }
        System.out.printf("Team: %s%n",teamName);

        String query = "select p.first_name, p.age from players as p\n" +
                "join teams as te on p.team_id = te.id\n" +
                "where team_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,teamId);

        int counter= 1;
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.printf("%d. %s %d%n",counter++,resultSet.getString("first_name"),
                    resultSet.getInt("age"));
        }
    }

    private String getEntityNameById(int entityId, String tableName) throws SQLException {
        String query = String.format("Select name FROM %s WHERE id= ?",tableName);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,entityId);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name"): null;
    }

    public void addStadium() throws IOException, SQLException {
        System.out.printf("Enter stadium info: name,capacity,town name: ");
        String[] stadiumInfo= bufferedReader.readLine().split("\\s+");
        String stadiumName = stadiumInfo[0];
        int capacity = Integer.parseInt(stadiumInfo[1]);
        String townName = stadiumInfo[2];

        int townId= getEntityIdByName(townName,"towns");
        int stadiumId = getEntityIdByName(stadiumName,"stadiums");

        System.out.printf("Checking if town exists: %s%n", townName);
        if (townId <0){
            System.out.printf("Town '%s' not found. Adding to database.%n", townName);
            insertEntityInTowns(townName);
            townId = getEntityIdByName(townName,"towns");
        } else {
            System.out.printf("Town '%s' already exists with ID: %d%n", townName, townId);
        }

        System.out.printf("Checking if stadium exists: %s%n", stadiumName);
        if (stadiumId<0){
            System.out.printf("Stadium '%s' not found. Adding to database.%n", stadiumName);
            insertEntityInStadiums(stadiumName,capacity,townId);
        } else {
            System.out.printf("Stadium '%s' already exists with ID: %d%n", stadiumName, stadiumId);
        }
    }

    private void insertEntityInStadiums(String stadiumName,int capacity,int townId) throws SQLException {
        String query = "INSERT INTO stadiums(name,capacity,town_id) values(?,?,?)";

        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1,stadiumName);
        statement.setInt(2,capacity);
        statement.setInt(3,townId);
        statement.execute();
    }
    private void insertEntityInTowns(String townName) throws SQLException {
        String query = "INSERT INTO towns(name) values(?)";

        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1,townName);
        statement.execute();
    }

    private int getEntityIdByName(String entityName, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?",tableName);

        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1,entityName);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }


    public void increaseAgeWithProcedure() throws IOException, SQLException {
        System.out.println("Enter player ID: ");
        int playerId = Integer.parseInt(bufferedReader.readLine());

        String query = "CALL get_older(?)";
        CallableStatement callableStatement = connection
                .prepareCall(query);
        callableStatement.setInt(1,playerId);
        callableStatement.execute();
    }
}
