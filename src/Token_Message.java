
import java.io.Serializable;

public class Token_Message implements Serializable{
	
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
	
	private Token token;
	
	/*
	 * Default Constructor
	 */
	
	public Token_Message(String sourceUrl, int sourceServerIndex, Token token) {
		this.sourceUrl = sourceUrl;
		this.sourceServerIndex = sourceServerIndex;
		this.token = token;
		
	}
	
	public String getSourceUrl() {
		return this.sourceUrl;
	}
	
	public int getSourceServerIndex() {
		return this.sourceServerIndex;
	}
	
	public Token getToken() {
		return this.token;
	}

	

}
