package validation;

import naivebayes.Attribute;
import naivebayes.NBC;

public class LeaveOneOut {
    
    public static double validate(Attribute[][] data, Attribute[] labels, double t)
    {
        double accuracy = 0;
        
        for (int leaveOut = 0; leaveOut < data.length; leaveOut++)
        {
            //Fill subarray
            Attribute[][] workData = new Attribute[data.length - 1][data[0].length];
            
            boolean passed = false;
            for (int i = 0; i < data.length; i++)
            {
                if (i == leaveOut)
                {
                    passed = true;
                    continue;
                }
                
                for (int j = 0; j < data[0].length; j++)
                {
                    if (!passed)
                    {
                        workData[i][j] = data[i][j];
                    }
                    else
                    {
                        workData[i - 1][j] = data[i][j];
                    }
                    
                }
            }
            
            //Fill labels
            Attribute[] workLabels = new Attribute[labels.length - 1];
            passed = false;
            for (int i = 0; i < labels.length; i++)
            {
                if (i == leaveOut)
                {
                    passed = true;
                    continue;
                }
                if (!passed)
                {
                    workLabels[i] = labels[i];
                }
                else
                {
                    workLabels[i - 1] = labels[i];
                }
            }
            
            
            //Train the NBC
            NBC classifier = new NBC(workData, workLabels);
            classifier.train();
            
            
            Attribute result = classifier.predict(data[leaveOut], t);
            
            //System.out.println(result.getValue() + " - " + labels[leaveOut].getValue());
            
            if (result.getValue().equals(labels[leaveOut].getValue()))
            {
                accuracy++;
            }
        }
        
        accuracy = accuracy / data.length;
        
        return accuracy;
    }
    
}
