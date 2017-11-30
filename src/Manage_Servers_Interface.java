import java.util.*;

public interface Manage_Servers_Interface {
	
	/*
	 * Initialise multiple servers
	 */
	
	public void startServers();
	
	/*
	 * Fetch the list of servers
	 */
	
	public List<DA_SK_RMI_Interface> getServers();
	
	/*
	 * Fetch list of URLs
	 */
	
	public String[] getUrls();


}
