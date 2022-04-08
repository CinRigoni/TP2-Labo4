import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

public class SqlConexion {
    private static Connection conn=null;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/practico_2_lab";


    public SqlConexion(){
        try{
            //Obtener el driver
            Class.forName(driver);
            //Obtener la conexion
            conn= DriverManager.getConnection(url,user,password);
            if(conn!=null){
                System.out.println("Conexion exitosa a la BD");
            }
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public Connection getConnection(){
        return conn;
    }

    public static String queryBuilder(String responseBody){
        String subJson = "";
        String query = "";
        if(responseBody.charAt(0) == '['){
            subJson = responseBody.substring(1,responseBody.length()-1);
        }else{
            subJson = responseBody;
        }
        try{
            JSONObject json = new JSONObject(subJson);
            if(!json.has("status")){
                int codigoPais = json.getJSONArray("callingCodes").getInt(0);
                String nombrePais = json.getString("name").replace("'","");
                String capitalPais = json.getString("capital").replace("'","");
                String region = json.getString("region").replace("'","");
                long poblacion = json.getInt("population");
                float latitud = json.getJSONArray("latlng").getFloat(0);
                float longitud = json.getJSONArray("latlng").getFloat(1);

                if(existePais(json.getJSONArray("callingCodes").getInt(0))){
                    query = "UPDATE pais SET " +
                            "nombrePais = '" +nombrePais+"', " +
                            "capitalPais = '" +capitalPais+"', " +
                            "region = '" +region+"', " +
                            "poblacion = '" +poblacion+"', " +
                            "latitud = '" +latitud+"', " +
                            "longitud = '" +longitud+"' " +
                            "WHERE codigoPais = " +codigoPais +";";
                }else{
                    query = "INSERT into pais VALUES (" +
                            codigoPais + ", '" +
                            nombrePais + "', '" +
                            capitalPais + "', '" +
                            region + "', " +
                            poblacion + ", " +
                            latitud + ", " +
                            longitud + ");";
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
            query = "";
        }catch (Exception e){
            query = "";
        }
        return query;
    }
    public void insertarQuery(String query) throws SQLException {
        Statement state = conn.createStatement();
        if(!query.isEmpty()){
            state.execute(query);
            System.out.println("Insertado con exito");
        }else{
            System.out.println("Invalido");
        }
    }
    public static boolean existePais(int codigoPais) throws SQLException{
        String query = "SELECT * FROM pais WHERE codigoPais = "+codigoPais+";";
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(query);
        return rs.next();
    }
}
