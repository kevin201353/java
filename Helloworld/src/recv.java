import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Message;


public class recv {
	public recv() {
		
	}
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory (ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD, "failover://(tcp://localhost:61616)");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//destination = session.createQueue("FirstQueue");
			destination = session.createTopic("zhaoTopic");
			consumer = session.createConsumer(destination);
			while (true) {
				TextMessage message = (TextMessage)consumer.receive(1000*10);
				if (null != message) {
					System.out.println("recevie message: " + message.getText());
				}else {
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (null != connection) {
					connection.close();
				}
			}catch(Throwable ignore) {
				
			}
		}
		System.out.println("Hello World Receive.");
	}
}


