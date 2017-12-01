
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.*;



public class Unit_Test {
	
	private final static Logger logger = Logger.getLogger(Unit_Test.class.getName());
	
	private List<DA_SK_RMI_Interface> servers;
	private String[] urls;
	
	@Before
	public void init() {
		servers = new ArrayList<DA_SK_RMI_Interface>();
		Configuration config = null;
		
		try {
			config = new PropertiesConfiguration("network.cfg");
			
		}	catch (ConfigurationException e) {
			e.printStackTrace();
		}
		
		
		urls = config.getStringArray("node.url");
		servers = new ArrayList<DA_SK_RMI_Interface>();
		
		for (String url : urls) {
			
			try {
				DA_SK_RMI_Interface server = (DA_SK_RMI_Interface) Naming.lookup(url);
				server.reset();
				servers.add(server);
			}	catch (RemoteException e1) {
				e1.printStackTrace();
			}	catch (NotBoundException e2) {
				e2.printStackTrace();
			}	catch (MalformedURLException e3) {
				e3.printStackTrace();
			}
		}
		
		
	}
	
	

	@Test
	public void test() {
		
		DA_SK_RMI_Interface server1 = servers.get(0);
		DA_SK_RMI_Interface server2 = servers.get(1);
		DA_SK_RMI_Interface server3 = servers.get(2);
		DA_SK_RMI_Interface server4 = servers.get(3);
		
		try {
			
			Token token = Token.activateToken(4);
			Token_Message tm = new Token_Message("", 0, token);
			server1.receiveToken(tm);
			
			server1.startProcessing();
			server2.startProcessing();
			server3.startProcessing();
			server4.startProcessing();
			
			Thread.sleep(10000);
			
			Assert.assertTrue(server1.getProcessExecutionFlag());
			Assert.assertTrue(server2.getProcessExecutionFlag());
			Assert.assertTrue(server3.getProcessExecutionFlag());
			Assert.assertTrue(server4.getProcessExecutionFlag());
			
		}	catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}

}
