package msgcomm;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.BytesMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import net.sf.json.JSONObject;
import java.util.LinkedList;
import java.util.Queue;

class ThreadTest extends Thread {
	public Session session_t;
	public MessageConsumer  MessageConsumer_t;
	public ThreadTest(Session session, MessageConsumer messageconsumer) {
		session_t = session;
		MessageConsumer_t = messageconsumer;
	}
	public void run(){
		while(true){
			try{
				byte[] bytes = new byte[1024];
				BytesMessage bytesMessage = (BytesMessage)MessageConsumer_t.receive(1000*10);
				if (null != bytesMessage) {
					int read = bytesMessage.readBytes(bytes);
					String msg = new String(bytes, 0, read, "UTF-8");
//					for(String str : msg.split("###")){
//						System.out.println("recevie message: " + str);
//					}
					String[] args = msg.split("###", 3);
					RespData resp = new RespData(args[1], args[2]);
					System.out.println("recevie message: args[1]: " + args[1] + "-----" + "args[2]: " + args[2]);
					recvMsg.msgqueue.add(resp);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Hello World Receive   11111.");
		}
	}
}

public class recvMsg {
	private ThreadTest thread_test;
	public static Queue<RespData> msgqueue;
	public void recMsgRunning() {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory (ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("edu_Queue");
			//destination = session.createTopic("zhaoTopic");
			consumer = session.createConsumer(destination);
			thread_test = new ThreadTest(session, consumer);
			thread_test.start();
		}catch(Exception e){
			e.printStackTrace();
		}
//		finally{
//			try {
//				if (null != connection) {
//					connection.close();
//				}
//			}catch(Throwable ignore) {
//				
//			}
//		}
		System.out.println("Hello World Receive.");
	}
	public void wait_recv(){
		try {
			thread_test.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class RespData {
	private String msgflag;
	private String content;
	public RespData(String v_msgflag, String v_content){
		msgflag = v_msgflag;
		content = v_content;
	}
	public String GetMsgFlag(){
		return msgflag;
	}
	public String GetContent(){
		return content;
	}
}