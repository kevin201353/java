package msgcomm;
import  com.mongodb.MongoClient;
import  com.mongodb.client.MongoDatabase;
import  com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import  org.bson.Document;
import net.sf.json.JSONObject;
public class db {
	 private MongoClient  mongoClient;
	 private MongoDatabase mongoDatabase;
	 private MongoCollection<Document> collection;
	 public void db() {
		 try{
			 //connect mongodb server
			 mongoClient = new MongoClient("localhost", 27017);
			 //connect mongodb database
			 mongoDatabase = mongoClient.getDatabase("test");
			collection = mongoDatabase.getCollection("shenclass");
		 }catch(Exception e){
			 System.err.println(e.getClass().getName() + ":" + e.getMessage());
		 }
	 }
	 
	 public void getFileds(String key){
			//检索所有文档 
         /**
         * 1. 获取迭代器FindIterable<Document>
         * 2. 获取游标MongoCursor<Document>
         * 3. 通过游标遍历检索出的文档集合
         * */ 
         FindIterable<Document> findIterable = collection.find();
         MongoCursor<Document> mongoCursor = findIterable.iterator();
         while(mongoCursor.hasNext()){
            //System.out.println(mongoCursor.next().toJson());
            JSONObject obj = JSONObject.fromObject(mongoCursor.next().toJson());
            System.out.println("name is : " + obj.get(key));
         }  
	 }
}
