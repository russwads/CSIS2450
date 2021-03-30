package callcenter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Agent {
	private int agentID;
	private String password;
	private String name;
	
	public Agent(int a, String p, String n) {
		this.agentID = a;
		this.name = n;
		this.password = passwordHash(p);
	}
	/**
	 * 
	 * @param agentID
	 * @param password
	 * @return
	 */
	public boolean callDirectory(int agentID, String password) {
		// TODO Finish function
		return false;
	}
	/**
	 * Specialized equals() function that matches user input in the password field to the password stored in the Agent DB.
	 * @param password string-value that will get tested in terms of matches
	 * @return true if passwords match; false if otherwise
	 */
	public boolean authenticate(String passwd) {
		String compare = passwordHash(passwd);
		return this.password.equals(compare);
	}
	/**
	 * A function that hashes a String to use as a hashed password. Creates security for Agent users so password is not easily obtained.
	 * @param passwordToHash String value that will pass through function to get hashed
	 * @return hashed password, in the form of a 128-bit string
	 */
	private String passwordHash (String passwordToHash) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(passwordToHash.getBytes());
	        byte[] bytes = md.digest();
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++)
	        {
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        return sb.toString();
	    } 
	    catch (NoSuchAlgorithmException e) 
	    {
	        e.printStackTrace();
	        return "ERROR: No Such Algorithm";
	    }
	}
	@Override
	public String toString() {
		return "Agent #" + agentID + ": " + name;
	}
	
}
