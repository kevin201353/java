package msgcomm;

public class display {
	private String host;
	private String port;
	private String vmId;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
	    return port;
	}
	public void setPort(String port) {
	    this.port = port;
	}
	public String getvmId() {
	    return vmId;
	}
	public void setvmId(String vmId) {
	    this.vmId = vmId;
	}
	public display() {
	    super();
	         // TODO Auto-generated constructor stub
	}
	public display(String host, String port, String vmId) {
	         super();
	         this.host = host;
	         this.port = port;
	         this.vmId = vmId;
	}
	@Override
	public String toString() {
	    return "display [host=" + host + ", port=" + port + ", vmId=" + vmId + "]";
	}
}
