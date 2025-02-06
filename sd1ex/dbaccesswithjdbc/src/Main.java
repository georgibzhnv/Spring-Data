import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Homework homework = new Homework();
        homework.setConnection("root","root123");

        //homework.getCoachesNames();
        //homework.getPlayersNames();
        //homework.addStadium();
        homework.increaseAgeWithProcedure();
    }
}