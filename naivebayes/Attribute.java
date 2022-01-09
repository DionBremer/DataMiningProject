
package naivebayes;


public class Attribute {
    
    private String name;
    protected String value;
    
    public Attribute(String name, String value)
    {
        this.name = name;
        this.value = value;
    }
    
    public void setValue(String v)
    {
        value = v;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public String getName()
    {
        return name; 
    }
    
    @Override 
    public boolean equals(Object o)
    {
        if (o.getClass() != this.getClass())
        {
            return false;
        }
        
        Attribute obj = (Attribute) o;
        return this.name.equals(obj.getName()) && this.value.equals(obj.getValue());
    }
}
