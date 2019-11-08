package utilidade;

import java.net.InetAddress;

public class EnderecoVirtual {
	public InetAddress ip;
	public int porta;
	
	public EnderecoVirtual(String ip, int porta) {
		try {
			this.ip = InetAddress.getByName(ip);
			this.porta = porta;
		} catch (Exception e) {
			
		}
	}
}
