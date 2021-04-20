package source;
import java.util.Random;
public class Sim implements Comparable<Sim>
{
    private static int NEXT_SIM_IDX=0;
    
    public static double MIN_MATING_AGE_F = 16.0;
    public static double MIN_MATING_AGE_M = 16.0;
    public static double MAX_MATING_AGE_F = 50.0; // Janet Jackson
    public static double MAX_MATING_AGE_M = 73.0; // Charlie Chaplin
    
    public static AgeModel M = new AgeModel();
    public static Random R = new Random();
    
    /** 
     * Ordering by death date.
     * 
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Sim o) 
    {
        return Double.compare(this.deathtime,o.deathtime);
    }
    
    public enum Sex {F, M};

    private int sim_ident;
    private double birthtime;
    private double deathtime;
    private double matingTime;
    private Sim mother;
    private Sim father;
    private Sim mate;
    
    private Sex sex;
    
    public Sim(Sim mother, Sim father, double birth)
    {
    	
        this.mother = mother;
        this.father = father;
        
        this.birthtime = birth;
        this.deathtime = birth+M.randomAge(R);
        this.matingTime = birth;
        this.mate=null;
        
        this.sim_ident = NEXT_SIM_IDX++;
        
        
    	if(R.nextBoolean()) this.sex=Sex.F;
    	else this.sex= Sex.M;
    	
    }
    
    /**
     * A founding Sim.
     * 
     */
    public Sim()
    {
    	this(null, null, 0.0);

    }
    
    
    public double nextMatingTime() {
    	this.matingTime += M.randomMatingWait(R);
    	return this.matingTime;
    }
    /**
     * If this sim is of mating age at the given time
     * 
     * @param time
     * @return true if alive, sexually mature and not too old
     */
    public boolean isMatingAge(double time)
    {
        if (time<getDeathTime())
        {
            double age = time-getBirthTime();
            return 
                    Sex.F.equals(getSex())
                    ? age>=MIN_MATING_AGE_F && age <= MAX_MATING_AGE_F
                    : age>=MIN_MATING_AGE_M && age <= MAX_MATING_AGE_M;
        } else
            return false; // no mating with dead people
    }
    
    /**
     * Test for having a (faithful and alive) partner. 
     * 
     * @param time
     * @return 
     */
    public boolean isInARelationship(double time)
    {
        return mate != null && mate.getDeathTime()>time 
                && mate.getMate()==this;
    }
    
    public void setDeath(double death)
    {
        this.deathtime = death;
    }
    public int getId() {
    	return this.sim_ident;
    }
    public Sex getSex()
    {
        return sex;
    }
    
    public double getBirthTime()
    {
        return birthtime;
    }
    
    public double getDeathTime()
    {
        return deathtime;
    }
    
    public void setDeathTime(double death_time)
    {
        this.deathtime = death_time;
    }
    
    /**
     * 
     * @return null for a founder
     */
    public Sim getMother()
    {
        return mother;
    }
    
    /**
     * 
     * @return null for a founder
     */
    public Sim getFather()
    {
        return father;
    }
    
    public Sim getMate()
    {
        return mate;
    }
    
    public void setMate(Sim mate){this.mate = mate;}
    
    public boolean isFounder()
    {
        return (mother==null && father==null);
    }
    
    private static String getIdentString(Sim sim)
    {
        return sim==null?"":"sim."+sim.sim_ident+"/"+sim.sex;
    }
    
    @Override
    public String toString()
    {
        return getIdentString(this)+" ["+birthtime+".."+deathtime+", mate "+getIdentString(mate)+"\tmom "+getIdentString(getMother())+"\tdad "+getIdentString(getFather())
        +"]";
    }
}
