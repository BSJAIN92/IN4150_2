import java.io.Serializable;
import java.util.*;


public class Token implements Serializable{
	
	List<Integer> tokenClock;
	private static boolean isActive = false;
	
	private Token (int totalServers) {
		tokenClock = new ArrayList<Integer>();
		
		for (int i = 0; i < totalServers; i++) {
			tokenClock.add(0);
		}
	}
	
	public static Token activateToken (int totalServers) {
		if (!isActive) {
			isActive = true;
			return new Token(totalServers);
		}
		
		return null;
	}
	
	public List<Integer> getTokenClock(){
		return this.tokenClock;
	}

}
