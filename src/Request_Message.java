
import java.io.Serializable;

public class Request_Message implements Serializable {
	
	/*
	 * source url
	 */
	
	private String sourceUrl;
	
	/*
	 * index of source server
	 */
	
	private int sourceServerIndex;
	
	/*
	 * clock of source server
	 */
	
	private int sourceClock;
	
	/*
	 * Default Constructor
	 */
	
	public Request_Message(String sourceUrl, int sourceServerIndex, int sourceClock) {
		this.sourceUrl = sourceUrl;
		this.sourceServerIndex = sourceServerIndex;
		this.sourceClock = sourceClock;
		
	}
	
	public String getSourceUrl() {
		return this.sourceUrl;
	}
	
	public int getSourceServerIndex() {
		return this.sourceServerIndex;
	}
	
	public int getSourceClock() {
		return this.sourceClock;
	}

}
