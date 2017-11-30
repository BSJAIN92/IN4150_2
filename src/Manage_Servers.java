
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.logging.Logger;
import java.net.*;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class Manage_Servers implements Manage_Servers_Interface{
	
	private static Logger logger = Logger.getLogger(Manage_Servers.class.getName());
	
	private String[] urls;
	private List<DA_SK_RMI_Interface> servers;
	
	
	public void startServers() {
		
		Configuration config = null;
		
		/*
		 * Registering RMI
		 * Remember to kill PID on 1099
		 */
			
		try {
			LocateRegistry.createRegistry(1099);
		}	catch (RemoteException e) {
			e.printStackTrace();
		}
		
		/*
		 * Starting Security Manager
		 */
		
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		
		/*
		 * Configuring servers
		 */
		
		try {
			config = new PropertiesConfiguration("network.cfg");
		}	catch(ConfigurationException e) {
			e.printStackTrace();
		}
		
		urls = config.getStringArray("node.url");
		servers = new ArrayList<DA_SK_RMI_Interface>();
		
		int serverIndex = 0;
		
		for (String url : urls) {
			try {
				DA_SK_RMI_Interface server = new DA_SK_RMI(urls, serverIndex);
				
				/*
				 * Starting individual servers and binding url to server
				 */
				
				new Thread((DA_SK_RMI) server).start();
				Naming.bind(url, server);
				servers.add(server);
				serverIndex++;
				System.out.println(server.getServerIndex());
				
			}	catch (RemoteException e1) {
				e1.printStackTrace();
			}	catch (MalformedURLException e2) {
				e2.printStackTrace();
			}	catch (AlreadyBoundException e3) {
				e3.printStackTrace();
			}
		}
		
		
		for (String url : urls) {
			
			try {
				DA_SK_RMI_Interface server = (DA_SK_RMI_Interface) Naming.lookup(url);
				server.reset();
				servers.add(server);
				
			}	catch (RemoteException e1) {
				e1.printStackTrace();
			}	catch (MalformedURLException e2) {
				e2.printStackTrace();
			}	catch (NotBoundException e3) {
				e3.printStackTrace();
			}
		}
		
		return;
	}
	
	/*
	 * Fetch the list of servers
	 */
	
	
	public List<DA_SK_RMI_Interface> getServers(){
		return servers;
	}
	
	/*
	 * Fetch list of URLs
	 */
	
	public String[] getUrls() {
		return urls;
	}

}
