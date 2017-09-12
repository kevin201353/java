package msgcomm;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.IOException;

public class Multicast {
	private final static String BC_IP="224.5.6.88";
	private final static int BC_PORT = 11008;
	private final static int PACK_SIZE = 4096;
	private static InetAddress ip = null;
	private static MulticastSocket socket = null;
	public void initServer() throws IOException {
		ip = InetAddress.getByName(BC_IP);
	    socket = new MulticastSocket(BC_PORT);
		socket.joinGroup(ip);
		socket.setLoopbackMode(false);
	}
	public void listen(){
		byte[] data = new byte[4096];
		try{
			  DatagramPacket packet = new DatagramPacket(data, data.length);
			  socket.receive(packet);
			  String message = new String(packet.getData(), 0, packet.getLength());
			  System.out.println(message);
			  socket.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void send(String data){
		try{
			ip = InetAddress.getByName(BC_IP);
			DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), ip, BC_PORT);
			MulticastSocket ms = new MulticastSocket();
			ms.send(packet);
			ms.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
