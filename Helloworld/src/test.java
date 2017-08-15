import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
//import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import javax.jms.BytesMessage;

import msgcomm.*;

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
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD, "failover://(tcp://localhost:61616)");
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("edu_ecd68a022b8f");
			//destination = session.createTopic("zhaoTopic");
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//sendMessage(session, producer);
			//ThreadTest2 thread_test = new ThreadTest2(session, producer);
			//thread_test.run();
			ThreadTest thread_test = new ThreadTest(session, producer);
			thread_test.start();
			recvMsg recvmsg = new recvMsg();
			recvmsg.recMsgRunning();
			thread_test.wait();
			recvmsg.wait_recv();
		}catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			try {
//				if (null != connection) {
//					connection.close();
//				}
//			}catch(Throwable ignore){
//				
//			}
//		}
		System.out.println("Hello World.");
	}
	
	
//	public static void sendMessage(Session session, MessageProducer producer) throws Exception {
//		for (int i=1; i<=SEND_NUMBER;i++){
//			TextMessage message = session.createTextMessage("ActiveMq send Message " + i);
//			//send message destion
//			System.out.println("send Message:"  + "ActiveMq sender Message" + i);
//			producer.send(message);
//		}
//	}
}



class ThreadTest extends Thread {
	private int tickets = 60000;
	public Session session_t;
	public MessageProducer  messageProducer_t;
	public ThreadTest(Session session, MessageProducer messageProducer) {
		session_t = session;
		messageProducer_t = messageProducer;
	}
	private int sendCount = 0;
	public void run(){
		while(true){
			if (tickets > 0){
				//TextMessage message = null;
				BytesMessage message = null;
				JSONObject jsonObject = null;
				Map<String, Object> map = new HashMap<String, Object>();
				Long milllis = System.currentTimeMillis();
				//Long milllis = System.nanoTime();
				String str_time = milllis.toString();
				System.out.println(str_time);
				System.out.println(Thread.currentThread().getName() + " sendcount :" +  sendCount);
				if (sendCount == 0) { //显示动画
					try {
						System.out.println("0000000");
						map.put("datetime", str_time);
						map.put("action", "classbegin");
						jsonObject = JSONObject.fromObject(map);
						System.out.println(jsonObject);
						
						//message = session_t.createTextMessage(jsonObject.toString());
						message = session_t.createBytesMessage();
						message.writeBytes(jsonObject.toString().getBytes());
					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						messageProducer_t.send(message);
						session_t.commit();
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}//sendCount==0
				
				if (sendCount == 20) {
					try {
						System.out.println("0000000");
						map.put("datetime", str_time);
						map.put("action", "unReconnect");
						jsonObject = JSONObject.fromObject(map);
						System.out.println(jsonObject);
						
						//message = session_t.createTextMessage(jsonObject.toString());
						message = session_t.createBytesMessage();
						message.writeBytes(jsonObject.toString().getBytes());
					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						messageProducer_t.send(message);
						session_t.commit();
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}//sendCount==0

				if (sendCount == 5) { //上课
					try {
						   System.out.println("44444444");
						   map.put("module", "class");
						   map.put("datetime", str_time);
						   map.put("action", "display");
						   jsonObject = JSONObject.fromObject(map);
						   System.out.println(jsonObject);
						   display dds = new display("192.168.110.231", "5900", "5900");
						   jsonObject = jsonObject.fromObject(dds);
						   System.out.println(jsonObject);
						   map.put("data", dds);
						   jsonObject = JSONObject.fromObject(map);
						   System.out.println(jsonObject);
						   //message = session_t.createTextMessage(jsonObject.toString());
						   //BytesMessage message = session_t.createBytesMessage();
						   message = session_t.createBytesMessage();
						   message.writeBytes(jsonObject.toString().getBytes());
					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						messageProducer_t.send(message);
						session_t.commit();
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}//sendCount==4
				
				
//				if (sendCount == 5) { //上课
//					try {
//						   System.out.println("44444444");
//						   map.put("module", "class");
//						   map.put("datetime", str_time);
//						   map.put("action", "display");
//						   jsonObject = JSONObject.fromObject(map);
//						   System.out.println(jsonObject);
//						   display dds = new display("192.168.0.220", "5901", "123456");
//						   jsonObject = jsonObject.fromObject(dds);
//						   System.out.println(jsonObject);
//						   map.put("data", dds);
//						   jsonObject = JSONObject.fromObject(map);
//						   System.out.println(jsonObject);
//						   message = session_t.createTextMessage(jsonObject.toString());
//					} catch (JMSException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					try {
//						messageProducer_t.send(message);
//						session_t.commit();
//					} catch (JMSException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}//sendCount==2
//			  if (sendCount == 10) //下课
//			  {
//				 try {
//					   System.out.println("10101010");
//					   map.put("module", "class");
//					   map.put("datetime", str_time);
//					   map.put("action", "classover");
//					   jsonObject = JSONObject.fromObject(map);
//					   System.out.println(jsonObject);
//					   //message = session_t.createTextMessage(jsonObject.toString().get);
//					   //BytesMessage message = session_t.createBytesMessage();
//					   message = session_t.createBytesMessage();
//					   message.writeBytes(jsonObject.toString().getBytes());
//				} catch (JMSException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				try {
//					messageProducer_t.send(message);
//					session_t.commit();
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			  }//sendCount==10
			  
//				if (sendCount > 5)
//				{
//							try {
//							System.out.println("0000000");
//							map.put("datetime", str_time);
//							map.put("action", "heartbeat");
//							jsonObject = JSONObject.fromObject(map);
//							System.out.println(jsonObject);
//							
//							//message = session_t.createTextMessage(jsonObject.toString());
//							message = session_t.createBytesMessage();
//							message.writeBytes(jsonObject.toString().getBytes());
//						} catch (JMSException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						try {
//							messageProducer_t.send(message);
//							session_t.commit();
//						} catch (JMSException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				}//if heartbeat
			  if (sendCount == 15) //自习
			  {
				 try {
					   System.out.println("10101010");
					   map.put("module", "class");
					   map.put("datetime", str_time);
					   map.put("action", "freeStudy");
					   jsonObject = JSONObject.fromObject(map);
					   System.out.println(jsonObject);
					   //message = session_t.createTextMessage(jsonObject.toString());
					   //BytesMessage message = session_t.createBytesMessage();
					   message = session_t.createBytesMessage();
					   message.writeBytes(jsonObject.toString().getBytes());
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					messageProducer_t.send(message);
					session_t.commit();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }//sendCount==20
//			  if (sendCount == 2) //演示
//			  {
//				 try {
//					   System.out.println("20202020");
//					   Map<String, Object> map_demo = new HashMap<String, Object>();
//					   map_demo.put("command", "eclass_client -h 192.168.8.182 -p 22222 -s 100000 -m 3 -debug");
//					   map.put("module", "class");
//					   map.put("datetime", str_time);
//					   map.put("action", "start_demonstrate");
//					   map.put("data", map_demo);
//					   jsonObject = JSONObject.fromObject(map);
//					   System.out.println(jsonObject);
//					   //message = session_t.createTextMessage(jsonObject.toString());
//					   message = session_t.createBytesMessage();
//					   message.writeBytes(jsonObject.toString().getBytes());
//				} catch (JMSException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				try {
//					messageProducer_t.send(message);
//					session_t.commit();
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			  }//sendCount==20
//			  if (sendCount == 10) //结束演示
//			  {
//				 try {
//					   System.out.println("30303030");
//					   map.put("module", "class");
//					   map.put("datetime", str_time);
//					   map.put("action", "stop_demonstrate");
//					   jsonObject = JSONObject.fromObject(map);
//					   System.out.println(jsonObject);
//					   //message = session_t.createTextMessage(jsonObject.toString());
//					   message = session_t.createBytesMessage();
//					   message.writeBytes(jsonObject.toString().getBytes());
//				} catch (JMSException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				try {
//					messageProducer_t.send(message);
//					session_t.commit();
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			  }//sendCount==30
//			  
//			  if (sendCount == 35) //结束演示后， 点击下课
//			  {
//				 try {
//					   System.out.println("35353535");
//					   map.put("datetime", str_time);
//					   map.put("action", "classover");
//					   jsonObject = JSONObject.fromObject(map);
//					   System.out.println(jsonObject);
//					   message = session_t.createTextMessage(jsonObject.toString());
//				} catch (JMSException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				try {
//					messageProducer_t.send(message);
//					session_t.commit();
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				sendCount = 0;
//				{
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					continue;
//				}
//			  }//sendCount==35
			  tickets--;
			  sendCount++;
			  if (sendCount == 30)
			  {
				  sendCount = 0;
			  }
			}//if(tickets > 0)
			try {
				Thread.sleep(2000); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//while
	}
}

class ThreadTest2 implements Runnable {
	private int tickets = 60000;
	public Session session_t;
	public MessageProducer  messageProducer_t;
	public ThreadTest2(Session session, MessageProducer messageProducer) {
		session_t = session;
		messageProducer_t = messageProducer;
	}
	private int sendCount = 0;
	public void run(){
		Long milllis = System.currentTimeMillis();
		//Long milllis = System.nanoTime();
		String str_time = milllis.toString();
		System.out.println(str_time);
		while(true){
			if (tickets > 0){
//				System.out.println(Thread.currentThread().getName() + " is saling ticket" + tickets--);
				TextMessage message = null;
				JSONObject jsonObject = null;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("datetime", str_time);
				map.put("action", "classbegin");
				jsonObject = JSONObject.fromObject(map);
				System.out.println(jsonObject);
//				   else if (sendCount == 4){
//				   map.put("datetime", str_time);
//				   map.put("action", "display");
//				   jsonObject = JSONObject.fromObject(map);
//				   System.out.println(jsonObject);
//				   display dds = new display("192.168.0.220", "5901", "123456");
//				   jsonObject = jsonObject.fromObject(dds);
//				   System.out.println(jsonObject);
//				   map.put("data", dds);
//				   jsonObject = JSONObject.fromObject(map);
//				   System.out.println(jsonObject);
//				}
				try {
					message = session_t.createTextMessage(jsonObject.toString());
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//send message destion
				//System.out.println("send Message:"  + "ActiveMq sender Message" + tickets);
				try {
					messageProducer_t.send(message);
					session_t.commit();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tickets--;
				sendCount++;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}