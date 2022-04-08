
import java.sql.SQLException;

public class main {
    public static void main(String[] args) {
        SqlConexion conexion = new SqlConexion();
        for (int i = 1; i <= 300; i++) {
            String url = "https://restcountries.com/v2/callingcode/" + i;
            String response = ClienteHTTP.peticion(url);

            if (!response.isEmpty()) {
                String query = SqlConexion.queryBuilder(response);
                System.out.println("Numero pais: " + i);

                try {
                    conexion.insertarQuery(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                    ClienteHTTP.responseBody = "";
                }
            }
        }
        try{
            conexion.getConnection().close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
