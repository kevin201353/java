import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


//java main
public class test {

	private static final int SEND_NUMBER = 5;
	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionFactory  connectionFactory;
		
		Connection connection = null;
		
		Session session;
		
		Destination  destination;
		
		MessageProducer   producer;
		
		
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//destination = session.createQueue("FirstQueue");
			destination = session.createTopic("zhaoTopic");
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session, producer);
			session.commit();
		}catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			try {
				if (null != connection) {
					connection.close();
				}
			}catch(Throwable ignore){
				
			}
		}
		System.out.println("Hello World.");
	}
	
	
	public static void sendMessage(Session session, MessageProducer producer) throws Exception {
		for (int i=1; i<=SEND_NUMBER;i++){
			TextMessage message = session.createTextMessage("ActiveMq send Message " + i);
			//send message destion
			System.out.println("send Message:"  + "ActiveMq sender Message" + i);
			producer.send(message);
		}
	}
}
