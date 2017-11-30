
import java.rmi.*;
import java.util.List;
import java.util.Map;

public interface DA_SK_RMI_Interface extends Remote{
	
	/*
	 * Fetch serverDetails
	 */
	
	public Map<String, DA_SK_RMI_Interface> getServerDetails() throws RemoteException;
	
	/*
	 * Fetch serverIndex
	 */
	
	public int getServerIndex() throws RemoteException;
	
	/*
	 * Fetch all urls
	 */
	
	public String[] getUrls() throws RemoteException;
	
	/*
	 * Fetch total number of servers
	 */
	
	public int getTotalServers() throws RemoteException;
	
	/*
	 * Fetch clock
	 */
	
	public List<Integer> getClock() throws RemoteException;
	
	/*
	 * Fetch critical section flag
	 */
	
	public boolean getCriticalSectionFlag() throws RemoteException;
	
	/*
	 * Fetch process execution flag
	 */
	
	public boolean getProcessExecutionFlag() throws RemoteException;
	
	/*
	 * Reset
	 */
	
	public void reset() throws RemoteException;
	

}
