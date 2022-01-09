package naivebayes;

import java.util.ArrayList;


//The classifier
public class NBC {

    private final Attribute[][] trainData;
    private final Attribute[] labels;
    private final Attribute[] uniqueLabels;
    private int numUniqueLabels;
    private final ArrayList<ProbabilityMatrix> probabilities;
    
    public NBC(Attribute[][] attributes, Attribute[] labels)
    {
        trainData = attributes; 
        this.labels = labels;
        uniqueLabels = new Attribute[labels.length]; 
        numUniqueLabels = 0;
        
        probabilities = new ArrayList<>();
        
        //System.out.println("Dim traindata: " + trainData.length + "," + trainData[0].length);
        //System.out.println("Dim labels: " + labels.length);
    }
    
    public void train()
    {
        //First, select the unique labels 
        for (int i = 0; i < labels.length; i++)
        {
            boolean unique = true;
            for (int j = 0; j < numUniqueLabels; j++)
            {
                if (uniqueLabels[j].equals(labels[i]))
                {
                    unique = false;
                    break; 
                }
            }
            
            if (unique)
            {
                uniqueLabels[numUniqueLabels] = labels[i];
                numUniqueLabels++;
            }
        }
        
        //System.out.println("Unique labels: " + numUniqueLabels);
        
        //Calculate the probabilities
        for (int i = 0; i < trainData[0].length; i++)
        {
            probabilities.add(new ProbabilityMatrix(uniqueLabels, numUniqueLabels)); 
        }
        
        for (int i = 0; i < trainData[0].length; i++)
        {
            for (int j = 0; j < labels.length; j++)
            {
                probabilities.get(i).add(labels[j], trainData[j][i]);
            }
        }
        
        for (int i = 0; i < probabilities.size(); i++)
        {
            probabilities.get(i).toProbability();
        }
        
        //Print the first probmatrix as a test
        //System.out.println(probabilities.get(0).toString());
    }
    
    public Attribute predict(Attribute[] data, double t)
    {
        int highestLabel = 0;
        double highestProb = 0;
        
        for (int i = 0; i < numUniqueLabels; i++)
        {
            double p = 0;
            //First find P(label):
            for (int l = 0; l < labels.length; l++)
            {
                if (labels[l].getValue().equals(uniqueLabels[i].getValue()))
                {
                    p += 1;
                }
            }
            p /= labels.length;
           
            
            for (int j = 0; j < data.length; j++)
            {
                double p1 = probabilities.get(j).getProbability(data[j], uniqueLabels[i]);
                p *= (p1 * Math.pow(p1, t));
            }
            
            if (p > highestProb)
            {
                highestProb = p;
                highestLabel = i;
            }
        }
        
        return uniqueLabels[highestLabel];
    }
    
    public void printProbabilities()
    {
        for (int i = 0; i < probabilities.size(); i++)
        {
            System.out.println("Attribute: " + trainData[0][i].getName());
            System.out.println(probabilities.get(i));
        }
    }
}
