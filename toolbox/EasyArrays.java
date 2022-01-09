
package toolbox;

import java.util.stream.IntStream;

public class EasyArrays {
    
    public static int[] toArray(int ... values)
    {
        return IntStream.of(values).toArray();
    }
    
    public static int[] concat(int[] ar1, int[] ar2)
    {
        int[] result = new int[ar1.length + ar2.length];
        for (int i = 0; i < result.length; i++)
        {
            if (i < ar1.length)
            {
                result[i] = ar1[i];
            }
            else
            {
                result[i] = ar2[i % ar2.length];
            }
        }
        
        return result;
    }
    
    public static int[] getRange(int from, int to)
    {
        int[] result = new int[to - from];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = from + i;
        }
        
        return result;
    }
}
