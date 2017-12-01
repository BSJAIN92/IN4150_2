
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Logger;



public class DA_SK_RMI extends UnicastRemoteObject implements DA_SK_RMI_Interface, Runnable{
	
	public static final long serialVersionUID = 4745507L;
	
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
	 * delay
	 */
	
	private long delay;
	
	private Token token = null;
	
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
	 * Fetch delay
	 */
	
	public long getDelay() {
		return this.delay;
	}
	
	/*
	 * set delay
	 */
	
	public void setDelay(long delay) {
		this.delay = delay;
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
		
		this.clock = new ArrayList<Integer>(totalServers);
		for (int i = 0; i < totalServers; i++) {
			clock.add(0);
		}
		
		token = null;
		this.criticalSectionFlag = false;
		this.processExecutionFlag = false;
		
	}
	
	public void run() {
		logger.info("Server successfully initialized and started");
		
	}
	

	/*
	 * Start processing, implement token exchange
	 */
	
	public void startProcessing () {
		
		try{
			Thread.sleep(delay);
			
		}	catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		requestToken();
		
		waitToken();
		
		criticalSection();
		
		leaveToken();
		
		try{
			Thread.sleep(delay);
			
		}	catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		processExecutionFlag = true;
		
	}
	
	/*
	 * Send request to other servers for token
	 */
	
	public void requestToken() {
		
		//Increment local clock
		
		clock.set(serverIndex, clock.get(serverIndex)+1);
		
		/*
		 * Send request to every server.
		 * Iterate over the list of servers
		 */
		
		for (String url : urls) {
			try{
				
				DA_SK_RMI_Interface destination = (DA_SK_RMI_Interface) Naming.lookup(url);
				Request_Message rm = new Request_Message(urls[serverIndex], serverIndex, clock.get(serverIndex));
				destination.receiveRequestMessage(rm);
				
			}	catch (RemoteException e1) {
				e1.printStackTrace();
			}	catch (MalformedURLException e2) {
				e2.printStackTrace();
			}	catch (NotBoundException e3) {
				e3.printStackTrace();
			}
			
			
			
		}
	}
	
	/*
	 * Receive Token Request Message
	 */
	
	public synchronized void receiveRequestMessage(Request_Message rm) {
		
		//Update clock for the source server
		
		clock.set(rm.getSourceServerIndex(), rm.getSourceClock());
		
		/*
		 * Check for following conditions, and if satisfied, send token
		 * - current server is not in critical section
		 * - current server does have token
		 * - current server is not the one requesting
		 * - clock of requesting server is greater than token clock
		 */
		
		if (!criticalSectionFlag && token != null && serverIndex != rm.getSourceServerIndex() && clock.get(rm.getSourceServerIndex()) > token.getTokenClock().get(rm.getSourceServerIndex())) {
			sendToken(rm.getSourceUrl());
		}
		
	}
	
	/*
	 * send token to other server
	 */
	
	private void sendToken(String url) {
		try{
			DA_SK_RMI_Interface destination = (DA_SK_RMI_Interface) Naming.lookup(url);
			Token_Message tm = new Token_Message(urls[serverIndex], serverIndex, token);
			destination.receiveToken(tm);
			token = null;
			
		}	catch (RemoteException e1) {
			e1.printStackTrace();
		}	catch (NotBoundException e2) {
			e2.printStackTrace();
		}	catch (MalformedURLException e3) {
			e3.printStackTrace();
		}
	}
	
	/*
	 * receive token from other server
	 */
	
	public void receiveToken(Token_Message tm) {
		token =  tm.getToken();
	}
	
	/*
	 * wait to receive token
	 */
	
	public void waitToken() {
		while (token == null) {
			try{
				Thread.sleep(100);;
			}	catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*
	 * Random sleep to emulate critical section
	 */
	
	public void criticalSection() {
		criticalSectionFlag = true;
		
		try {
			Thread.sleep(100);
		}	catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		criticalSectionFlag = false;
	}
	
	private void leaveToken() {
		//Update token clock
		token.getTokenClock().set(serverIndex, clock.get(serverIndex));
		
		//search for server requesting access to token
		
		for (int i = 0; i < totalServers; i++) {
			if(i == serverIndex) {
				continue;
			}
			
			if (clock.get(i) > token.getTokenClock().get(i)) {
				sendToken(urls[i]);
				break;
			}
		}
	}
	

}
