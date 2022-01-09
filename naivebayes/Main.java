
package naivebayes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import toolbox.EasyArrays;
import toolbox.EasyColumns;
import toolbox.FileConverter;
import validation.LeaveOneOut;

public class Main {

    
    public static void main(String[] args) {
        
        int[] ignoreCols = EasyColumns.brownFrog; 
        int[] ignoreRows = EasyArrays.getRange(0, 2);
        
        Attribute[][] data = FileConverter.convertCSV("./amphibians.csv", ignoreCols, ignoreRows);
        
        //Get the labels out 
        Attribute[] labels = new Attribute[data.length];
        for (int i = 0; i < labels.length; i++)
        {
            labels[i] = data[i][data[0].length - 1];
        }
        
        Attribute[][] trainData = new Attribute[data.length][data[0].length - 1]; //Data without labels
        for (int i = 0; i < trainData.length; i++)
        {
            for (int j = 0; j < trainData[0].length; j++)
            {
                trainData[i][j] = data[i][j];
            }
        }
        
        //Preprocess the data: the first column contains water surface sizes 
        //In order to produce better results, transform this into: 
        // 0 if size <= 1000 
        // 1 if 1000 < size <= 10000
        // 2 is size > 10000
        for (int i = 0; i < trainData.length; i++)
        {
            if (Integer.parseInt(trainData[i][0].getValue()) <= 1000)
            {
                trainData[i][0].setValue("0");
            }
            else if (Integer.parseInt(trainData[i][0].getValue()) <= 10000)
            {
                trainData[i][0].setValue("1");
            }
            else
            {
                trainData[i][0].setValue("2");
            }
        }
    }
    
}
