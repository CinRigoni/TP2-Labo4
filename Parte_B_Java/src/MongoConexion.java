
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {
    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection coleccion;

    public MongoConexion() {
        try {
            mongo = new MongoClient("localhost", 27017);
            database = mongo.getDatabase("paises_db");
            coleccion = database.getCollection("paises");
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public MongoClient getConexion(){
        return mongo;
    }
    public MongoDatabase getDatabase(){
        return database;
    }
    public MongoCollection getColeccion(){
        return coleccion;
    }
    public void close(){
        mongo.close();
    }
}