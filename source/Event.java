package source;

import java.util.Random;

public class Event implements Comparable<Event>{

	public double time;
	public Sim subject;
	public enum What {birth,death,mating};
	
	
	public Event(Sim sim,What what) {
		Random R = new Random();
		AgeModel M= new AgeModel();
		
		this.subject=sim;
		if(what==What.birth) this.time=sim.getBirthTime();
		else if(what==What.death) this.time=sim.getDeathTime();
		else if(what==What.mating) this.time=sim.nextMatingTime();
	}
	
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
			return Double.compare(this.time, o.time);
		
	}
	public double getTime() {
		return this.time;
	}
	
	
	public Sim getSubject() {
		return this.subject;
	}
}
