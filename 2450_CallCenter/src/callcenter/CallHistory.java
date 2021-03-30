package callcenter;

import java.util.Deque;

public class CallHistory {
	private Deque<Call> calls;
	
	/**
	 * Returns a String array containing the last 10 calls. 
	 * Memory is always allocated for 10 calls; as a result, some calls may be null if customers do not have 10 calls. 
	 * @return
	 */
	public String[] lastTen() {
		String[] finish = new String[10];
		callConvert(finish, 10);
		return finish;
	}
	/**
	 * Returns a String array containing all calls. 
	 * @return
	 */
	public String[] allCalls() {
		String[] finish = new String[calls.size()];
		callConvert(finish, 1024);
		return finish;
	}
	/**
	 * Converts calls to strings and inserts into array. Used by both call functions in class.
	 * @param finish String array
	 * @param ten number used as conditional to stop the lastTen() function
	 */
	private void callConvert(String[] finish, int ten) {
		Deque<Call> temp = calls;
		for (int i = 0; i < calls.size() && i < ten; i++) {
			finish[i] = temp.peekFirst().toString();
			temp.removeFirst();
		}
	}
}
