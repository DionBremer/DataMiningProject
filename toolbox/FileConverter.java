
package toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import naivebayes.Attribute;



//Class that generates Attribute[][] from csv files 
public class FileConverter {

    
    //Path is the path of the file, ignoreColumns/ignoreRows are used to specify which to ignore (start counting from 0 as in arrays)
    public static Attribute[][] convertCSV(String path, int[] ignoreCols, int[] ignoreRows)
    {
        Attribute[][] result = null; 
        
        try
        {
            Scanner scanner = new Scanner(new File(path));
            //First, count the number of rows and columns
            int numRows = 0; 
            int numCols = 0;
            
            while(scanner.hasNext())
            {
                String line = scanner.nextLine();
                numRows++;
                if (numRows == 1)
                {
                    numCols = line.split(";").length;
                }
            }
            
            //No need to store rows/columns that we will ignore
            numRows -= ignoreRows.length;
            numCols -= ignoreCols.length;
            
            result = new Attribute[numRows][numCols];
            
            //Read the file
            scanner.close();
            scanner = new Scanner(new File(path));
            
            int currentLine = 0;
            int currentRow = 0;
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (!isIgnored(ignoreRows, currentLine))
                {
                    
                    String[] lines = line.split(";");
                    int currentCol = 0; 
                    for (int i = 0; i < lines.length; i++)
                    {
                        if (!isIgnored(ignoreCols, i))
                        {
                            result[currentRow][currentCol] = new Attribute("X" + currentCol, lines[i]);
                            currentCol++;
                        }
                    }
                    
                    currentRow++;
                }
                
                currentLine++;
            }
            
            scanner.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Can not find the file: " + path);
            System.exit(1);
        }
        
        /*
        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < result[0].length; j++)
            {
                System.out.print(result[i][j]);
                System.out.print(" ");
            }
            System.out.println("");
        }
        */
        
        
        return result;
    }
    
    public static Attribute[] oneRowCSV(String path, int row, int[] ignoreCols)
    {
        Attribute[] result = null;
        
        try
        {
            Scanner scanner = new Scanner(new File(path));
            int currentLine = 0;
            
            while(scanner.hasNextLine() && currentLine != row)
            {
                scanner.nextLine();
                currentLine++;
            }
            
            if (!scanner.hasNextLine())
            {
                System.err.println("Could not get to row: " + row);
                System.err.println("Got to row: " + currentLine);
                return result;
            }
            
            String[] lines = scanner.nextLine().split(";");
            int currentCol = 0; 
            result = new Attribute[lines.length - ignoreCols.length];
            
            for (int i = 0; i < lines.length; i++)
            {
                if (!isIgnored(ignoreCols, i))
                {
                    result[currentCol] = new Attribute("X" + currentCol, lines[i]);
                    currentCol++;
                }
            }
            
            scanner.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Can not find the file: " + path);
            System.exit(1);
        }
        
        return result;
    }
    
    private static boolean isIgnored(int[] ignored, int n)
    {
        for (int i = 0; i < ignored.length; i++)
        {
            if (ignored[i] == n)
            {
                return true;
            }
        }
        
        return false;
    }
}
