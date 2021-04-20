package source;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import source.Event.What;
import source.Sim.Sex;

public class Main {
	static int m;
	static Arbre vivants;
	static ArrayList<Integer> Ids;
	static Event E=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Simulate(5000,20000);
	}
	public static void addMating(Sim sim,PriorityQueue<Event> eventQ) {
		Event mating = new Event(sim, What.mating);

	    if (sim.getSex()==Sex.F) while( mating.time < sim.getDeathTime()) {
	    	  
	   	 	mating.time = sim.nextMatingTime();
	   	 	if(sim.isMatingAge(mating.time)){
	   		  eventQ.add(mating);
	   		  break;
	   	 	}
	    }

	}
	
	
	public static void addEvents(Sim sim, PriorityQueue<Event> eventQ) {
		  eventQ.add(new Event(sim, What.birth)); // insertion dans la file de priorité
		  Ids.add(sim.getId());

	      eventQ.add(new Event(sim, What.death));;
	      addMating(sim,eventQ);
	     
	      vivants.add(sim);
	     
	}
	
	//////////// main end ///////////////////
	
	public static void Simulate(int n, double Tmax)
	{	
	   Random RND = new Random();	
	   Ids = new ArrayList<>();
	   AgeModel M = new AgeModel();
	   vivants = new Arbre();
	   double fidelite = 0.9;
	   m=3*n/2;
	   
	   PriorityQueue <Event>eventQ = new PriorityQueue<>(); // file de priorité
	   for (int i=0; i<n; i++)
	   {
	      Sim fondateur = new Sim(); // sexe au hasard, naissance à 0.0
	      addEvents(fondateur,eventQ);
	   }
	   //System.out.println("   ");
	   int t=0;
	   while (!eventQ.isEmpty())
	   {
	      E = eventQ.poll(); // prochain événement
	      Sim x = E.subject;
	      if (E.time > Tmax) break; // arrêter à Tmax
	      
	      
	      if(((int) E.time)== 100*t) {
	    	  if(t>1) m=Ids.size();
	    	  t++;
	    	  System.out.println("temps = "+(int)E.time+" population = "+Ids.size());
	      }
	      
	      //// accouplement ///////////////
	     

	      if (x.getDeathTime() > E.time)
	      {

	    	if(x.isMatingAge(E.time))
	    	{
	    	  
				Sim y = null;
				// choisir pere y
				if (!x.isInARelationship(E.time) || RND.nextDouble()>fidelite)
				{ // partenaire au hasard
	
				   do
				   {
					   
					  int RNDi = RND.nextInt(Ids.size());	  
					  int Id = Ids.get(RNDi);
					  //System.out.println(Id);
				      Sim z = vivants.get(Id);

				      if (z.getSex()!=x.getSex() && z.isMatingAge(E.time)) // isMatingAge() vérifie si z est de l'age acceptable
				      {
				         if (x.isInARelationship(E.time) // z accepte si x est infidèle
				             || !z.isInARelationship(E.time)
				             || RND.nextDouble()>fidelite)
				         {  y = z;}
				      }
				   } while (y==null);
				} 
				
				else y = x.getMate();//partenaire précédent de x
				  
				x.setMate(y);
				y.setMate(x);
		      
				
				///////// birth ///////////// 
				Sim enfant = new Sim(x,y,E.time);
				addEvents(enfant,eventQ);
				addMating(x,eventQ);

		      } 	
	      }
	    	
	      else {

	    	  vivants.remove(x);
	    	  Ids.remove(Ids.indexOf(x.getId()));

	      }
	      
	      // else rien à faire avec E car son sujet est mort
	   }
	}	
}
	
