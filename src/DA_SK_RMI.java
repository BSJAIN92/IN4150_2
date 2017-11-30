
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Logger;



public class DA_SK_RMI extends UnicastRemoteObject implements DA_SK_RMI_Interface, Runnable{
	
	public static final long servialVersionUID = 4745507L;
	
	/*
	 * For logging purpose
	 */
	
	private static Logger logger = Logger.getLogger(DA_SK_RMI.class.getName());
	
	/*
	 * To store url of server
	 */
	
	private Map<String, DA_SK_RMI_Interface> serverDetails;
	
	/*
	 * Index Number of server
	 */
	
	private int serverIndex;
	
	/*
	 * Store urls of all servers
	 */
	
	private String[] urls;
	
	/*
	 * total number of servers
	 */
	
	private int totalServers;
	
	/*
	 * clock
	 */
	
	private List<Integer> clock;
	
	/*
	 * to check whether server is still in Critical Section
	 */
	
	private boolean criticalSectionFlag;
	
	/*
	 * processing status, 1 = still processing
	 */
	
	private boolean processExecutionFlag;
	
	/*
	 * Fetch serverDetails
	 */
		
	public Map<String, DA_SK_RMI_Interface> getServerDetails(){
		return this.serverDetails;
	}
	
	/*
	 * Fetch serverIndex
	 */
	
	public int getServerIndex() {
		return this.serverIndex;
	}
	
	/*
	 * Fetch all urls
	 */
	
	public String[] getUrls() {
		return this.urls;
	}
	
	/*
	 * Fetch total number of servers
	 */
	
	public int getTotalServers() {
		return this.totalServers;
	}
	
	/*
	 * Fetch clock
	 */
	
	public List<Integer> getClock(){
		return this.clock;
	}
	
	/*
	 * Fetch critical section flag
	 */
	
	public boolean getCriticalSectionFlag() {
		return this.criticalSectionFlag;
	}
	
	/*
	 * Fetch process execution flag
	 */
	
	public boolean getProcessExecutionFlag() {
		return this.processExecutionFlag;
	}
	
	/*
	 * Default Constructor
	 */
	
	protected DA_SK_RMI(String[] urls, int serverIndex) throws RemoteException{
		super();
		serverDetails = new HashMap<String, DA_SK_RMI_Interface>();
		
		this.serverIndex = serverIndex;
		this.urls = urls;
		this.totalServers = urls.length;
		
		reset();
	}
	
	/*
	 * Reset
	 */
	
	public void reset() {
		//NOT YET COMPLETE
		
		this.clock = new ArrayList<Integer>(totalServers);
		for (int i = 0; i < totalServers; i++) {
			clock.add(0);
		}
		
		this.criticalSectionFlag = false;
		this.processExecutionFlag = false;
		
	}
	
	public void run() {
		logger.info("Server successfully initialized and started");
		
	}

}
