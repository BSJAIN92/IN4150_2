import java.rmi.RemoteException;
import java.util.*;


public class Test_Code {
	
	public static void main(String[] args) {
		
		try {
			
			Manage_Servers ms = new Manage_Servers();
			ms.startServers();
			
		}	catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
