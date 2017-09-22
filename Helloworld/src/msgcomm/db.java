package msgcomm;
import  com.mongodb.MongoClient;
import  com.mongodb.client.MongoDatabase;
public class db {
	 private MongoClient  mongoClient;
	 private MongoDatabase mongoDatabase;
	 public void db() {
		 try{
			 //connect mongodb server
			 mongoClient = new MongoClient("localhost", 27017);
			 //connect mongodb database
			 mongoDatabase = mongoClient.getDatabase("test");
		 }catch(Exception e){
			 System.err.println(e.getClass().getName() + ":" + e.getMessage());
		 }
	 }
}
