package template.try_demo;
import java.sql.*;

public class DatabaseConnection {
    public static void main(String args[]){
        DatabaseConnection app = new DatabaseConnection();
        //app.selectAll();

    }
    public static Connection connect(){
        //SQLite connection string
        String url = "jdbc:sqlite:ProductInfo.db";
        Connection c = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);

        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Opened database successfully");
        return c;
    }
    public void selectAll(){
        String sql = "SELECT * FROM shirtData";
        try{
            Connection c = this.connect();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString("itemId"));
            }
            rs.close();
            stmt.close();

            c.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

