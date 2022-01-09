
package toolbox;


public class DoubleContainer {
    
    private double value; 
    
    public DoubleContainer(double d)
    {
        value = d;
    }
    
    public void setValue(double v)
    {
        value = v;
    }
    
    public double getValue()
    {
        return value;
    }
    
    public void increase()
    {
        value += 1;
    }
    
    public void divideBy(double n)
    {
        value /= n;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
}
