package classe;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connexion {

    public Connection getConnection() throws SQLException{
        Connection con = null;
        try {  
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/attestationfx?useUnicode=yes&characterEncoding=UTF-8","root","");  
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from etablissement");  
            while(rs.next())  
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

}
