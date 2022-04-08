import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;

public class main {
    public static void main(String[] args) {
        //regionAmericas();
        //regionAmericas(100000000);
        //distintoAfrica();
        //actEgipto();
        //eliminarPais("258");
        //mostrarAscendiente();
        crearIndice();


    }

    public static void migracionMongo(){
        MongoConexion conexion = new MongoConexion();

        for (int i = 1; i <= 300; i++) {
            String url = "https://restcountries.com/v2/callingcode/" + i;
            String response = ClienteHTTP.peticion(url);
            System.out.println("Pais numero: "+i);
            if(response.charAt(0) == '['){
                response = response.substring(1,response.length()-1);
            }
            Document documento = Document.parse(response);
            if(!documento.containsKey("status")){
                FindIterable findIterable = conexion.getColeccion().find(eq("callingCodes",String.valueOf(i)));
                if(findIterable.first() != null){
                    conexion.getColeccion().replaceOne(eq("callingCodes",String.valueOf(i)),documento);
                    System.out.println("Actualizado con exito");
                }else{
                    conexion.getColeccion().insertOne(documento);
                    System.out.println("Insertado con exito");
                }
            }else{
                System.out.println("Invalido");
            }
        }
        conexion.close();
    }

    //Punto 5.1)
    public static void regionAmericas(){
        MongoConexion conexion = new MongoConexion();
        FindIterable findIterable = conexion.getColeccion().find(eq("region","Americas"));
        for(Object doc : findIterable){
            System.out.println(doc);
        }
        conexion.close();
    }

    //Punto 5.2)
    public static void regionAmericas(long poblacion){
        MongoConexion conexion = new MongoConexion();
        MongoCursor cursor = null;
        try {
            cursor = conexion.getColeccion().find(and(gte("population", poblacion),eq("region","Americas")))
                    .sort(Sorts.descending("name")).iterator();
            while (cursor.hasNext()) System.out.println(cursor.next());
        }catch(MongoException | NullPointerException e){
            e.printStackTrace();
        } finally {
            cursor.close();
            conexion.close();
        }
    }

    //Punto 5.3)
    public static void distintoAfrica(){
        MongoConexion conexion = new MongoConexion();
        MongoCursor cursor = null;
        try {
            cursor = conexion.getColeccion().find(ne("region","Africa"))
                    .sort(Sorts.descending("name")).iterator();
            while (cursor.hasNext()) System.out.println(cursor.next());
        }catch(MongoException | NullPointerException e){
            e.printStackTrace();
        } finally {
            cursor.close();
            conexion.close();
        }
    }

    //Punto 5.4
    public static void actEgipto(){
        MongoConexion conexion = new MongoConexion();
        try {
            Document query = new Document().append("name",  "Egypt");
            Bson updates = Updates.combine(
                    Updates.set("name", "Egipto"),
                    Updates.set("population",95000000));
            UpdateOptions options = new UpdateOptions().upsert(true);
            conexion.getColeccion().updateOne(query, updates, options);
            System.out.println("Actualizado Egipto");
        }catch(MongoException | NullPointerException e){
            e.printStackTrace();
        } finally {
            conexion.close();
        }
    }

    //Punto 5.5
    public static void eliminarPais(String codigoPais){
        MongoConexion conexion = new MongoConexion();
        try{
            conexion.getColeccion().deleteOne(eq("callingCodes",codigoPais));
            System.out.println("Eliminado pais codigo "+codigoPais);
        }catch(MongoException | NullPointerException e){
            e.printStackTrace();
        } finally {
            conexion.close();
        }
    }

    //Punto 5.7
    public static void mostrarPoblacion(long minimo, long maximo){
        MongoConexion conexion = new MongoConexion();
        MongoCursor cursor = null;
        try {
            cursor = conexion.getColeccion().find(and(gte("population", minimo),lte("population",maximo)))
                    .sort(Sorts.descending("name")).iterator();
            while (cursor.hasNext()) System.out.println(cursor.next());
        }catch(MongoException | NullPointerException e){
            e.printStackTrace();
        } finally {
            cursor.close();
            conexion.close();
        }
    }

    //Punto 5.8
    public static void mostrarAscendiente(){
        MongoConexion conexion = new MongoConexion();
        MongoCursor cursor = null;
        try {
            cursor = conexion.getColeccion().find()
                    .sort(Sorts.ascending("name")).iterator();
            while (cursor.hasNext()) System.out.println(cursor.next());
        }catch(MongoException | NullPointerException e){
            e.printStackTrace();
        } finally {
            cursor.close();
            conexion.close();
        }
    }

    //Punto 5.11
    public static void crearIndice(){
        MongoConexion conexion = new MongoConexion();
        try{
            conexion.getColeccion().createIndex(Indexes.ascending("callingCodes"));
            System.out.println("Indices creados");
        }catch (MongoException e){
            e.fillInStackTrace();
        }finally {
            conexion.close();
        }
    }
}