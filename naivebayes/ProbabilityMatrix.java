
package naivebayes;

import toolbox.DoubleContainer;
import java.util.ArrayList;

//Stores probabilities for a single attribute
//Values are P(x = value | label) in the Laplace estimate
public class ProbabilityMatrix {
    
    private Attribute[] labels; 
    private ArrayList<Attribute> values; 
    private ArrayList<ArrayList<DoubleContainer>> probabilities; 
    private int numUniqueLabels;
    
    public ProbabilityMatrix(Attribute[] labels, int unique)
    {
        this.labels = labels;
        values = new ArrayList<>();
        numUniqueLabels = unique;
        probabilities = new ArrayList<>();
        
        for (int i = 0; i < numUniqueLabels; i++)
        {
            probabilities.add(new ArrayList<>());
        }
    }
    
    //Add an occurence of value with label
    public void add(Attribute label, Attribute value)
    {
        //Find where the label is in the list
        int labelIndex = -1; 
        for (int i = 0; i < numUniqueLabels; i++)
        {
            if (label.equals(labels[i]))
            {
                labelIndex = i;
                break;
            }
        }
        
        //Find where the value is in the list
        int attrIndex = -1;
        for (int i = 0; i < values.size(); i++)
        {
            if (value.equals(values.get(i)))
            {
                attrIndex = i;
                break;
            }
        }
        
        //Add occurence in the matrix
        if (attrIndex == -1)
        {
            attrIndex = values.size();
            values.add(value);
        }
        
        while(probabilities.get(labelIndex).size() <= attrIndex)
        {
            probabilities.get(labelIndex).add(new DoubleContainer(0.0));
        }
        
        probabilities.get(labelIndex).get(attrIndex).increase();
    }
    
    //Convert to probabilities
    public void toProbability()
    {
        //First, add 1 to every element in matrix
        
        for (int i = 0; i < probabilities.size(); i++)
        {
            for (int j = 0; j < values.size(); j++)
            {
                if (j < probabilities.get(i).size())
                {
                    probabilities.get(i).get(j).increase();
                }
                else
                {
                    probabilities.get(i).add(new DoubleContainer(1.0));
                }
            }
        }
        
        //Calculate probabilities
        for (int i = 0; i < numUniqueLabels; i++)
        {
            int sum = 0;
            for (int j = 0; j < probabilities.get(i).size(); j++)
            {
                sum += probabilities.get(i).get(j).getValue() - 1;
            }
            
            for (int j = 0; j < probabilities.get(i).size(); j++)
            {
                probabilities.get(i).get(j).divideBy(sum + values.size());
            }
        }
    }
    
    public double getProbability(Attribute value, Attribute label)
    {
        //Find where the label is in the list
        int labelIndex = -1; 
        for (int i = 0; i < numUniqueLabels; i++)
        {
            if (label.equals(labels[i]))
            {
                labelIndex = i;
                break;
            }
        }
        
        //Find where the value is in the list
        int attrIndex = -1;
        for (int i = 0; i < values.size(); i++)
        {
            if (value.equals(values.get(i)))
            {
                attrIndex = i;
                break;
            }
        }
        
        if (labelIndex == -1 || attrIndex == -1)
        {
            return 1; //Skip this value if it is not in the matrix
        }
        
        //System.out.println("Value: " + probabilities.get(labelIndex).get(attrIndex).getValue());
        return probabilities.get(labelIndex).get(attrIndex).getValue();
    }
    
    @Override
    public String toString()
    {
        //Pretty printer for printing the matrices
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------");
        sb.append("\n");
        sb.append("    ");
        for (int i = 0; i < values.size(); i++)
        {
            sb.append(values.get(i).getValue());
            sb.append("   ");
        }
        sb.append("\n");
        for (int i = 0; i < numUniqueLabels; i++)
        {
            sb.append(labels[i].getValue());
            sb.append(": ");
            for (int j = 0; j < probabilities.get(i).size(); j++)
            {
                sb.append(probabilities.get(i).get(j).toString().substring(0, 3));
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("-------------------------------");
        return sb.toString();
    }
}
