package Project_Final.MySqlService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlProvider {
    static private Connection con=null;
    public static Connection MysqlService(){
        if(con==null){
            try {
                String password = "j2$#%9fjaa$#%Jfj";
                String username = "root";
                con= DriverManager.getConnection("jdbc:mysql://localhost:3306/busmangerment", username, password);
                System.out.println("Success");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}
