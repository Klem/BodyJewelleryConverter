package klem.android.bodyart.tests;

/**
 * Various test method, may morph to utility class
 * @author Big
 *
 */
public class Line {
  
  
  
 public static void main(String[] args) {
   Line line = new Line();
   float angle = 0;
   Dot startingPoint =  line.new Dot(10,10);
   float distance = 5;
   
   Dot destinationPoint = line.computeDot(angle, distance, startingPoint);
   System.out.println("Passed distance "+distance);
   System.out.println("Starting point "+startingPoint.toString());
   System.out.println("Destination point "+destinationPoint.toString());
   System.out.println("Computed distance "+line.computeDistance(startingPoint, destinationPoint));

  }
  
  public Dot computeDot(float angle, float distance, Dot startingPoint) {

    
    double cos = Math.cos(angle) * distance;
    double sin = Math.sin(angle) * distance;
    
    float x = (float) (startingPoint.x + cos);
    float y = (float) (startingPoint.y + sin);
    
    return new Dot(x, y);
    
  }
  
  public float computeDistance(Dot start, Dot end) {
    double dx   = start.x - end.x;         //horizontal difference 
    double dy   = start.y - end.y;         //vertical difference 
    double dist = Math.sqrt( dx*dx + dy*dy ); //distance using Pythagoras theorem
    return (float) dist;
  }
  
 

  public class Dot {
    public float x = 0f;
    public float y = 0f;
    
    public Dot(float x ,float y)  {
      this.x = x;
      this.y = y;
    }
    
    @Override
    public String toString() {
      return "x:"+x+", y"+y;
    }
  }
}
