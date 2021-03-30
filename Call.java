package callcenter;

import java.time.*;

public class Call {
	private LocalDate date;
	private LocalTime time;
	private int agentID;
	private int length;
	
	public Call(LocalDate d, LocalTime t, int id, int l) {
		this.date = d;
		this.time = t;
		this.agentID = id;
		this.length = l;
	}
	@Override
	public String toString() {
		return date.toString() + " at " + time.toString() + ", Taken by Agent#" + agentID + ", Call Length: " + length + "sec";
	}
	
}
