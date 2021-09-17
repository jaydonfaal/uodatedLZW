import java.util.*;
import java.io.*;

// Note: there is something wrong with the encoder that I can't quite figure out.
// every file, when encoded then decoded, begins with the binary sequence 10101100111011010000000000000101
// -Peter Shen

public class Decoder {

	// name of method is self-explanatory
    public static void decode(String inputFile, String outputFile) throws IOException
	{
		StringBuilder binaryString = new StringBuilder();
		FileInputStream in = new FileInputStream(inputFile);
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
		byte[] byteArray = in.readAllBytes();
		
		// converts byte array into a giant binary String
		for(int i = 0; i < byteArray.length; i++)
		{
			binaryString.append(toBinary(byteArray[i]));
		}
		
		// sanity check: binary String for accuracy
		System.out.println(binaryString);
		
		ArrayList<Integer> binaryValues = new ArrayList<Integer>();
		
		// converts the String of binary to an ArrayList of ints, each 9 digits long
		for(int i = 0 ;i <= binaryString.length() - 9; i += 9)
		{
			binaryValues.add(toBinaryValue(binaryString.substring(i, i+9)));
		}

		// sanity check: ArrayList for accuracy
		System.out.println(binaryValues);
		
		// initial dictionary values
		HashMap<Integer, String> map = new HashMap<Integer,String>();
		for(int i = 0; i < 256; i++)
			map.put(i, "" + (char)i);
		
		// decoding part, involving dictionary and writing to outputFile
		int nextKey = 256;
		int current = binaryValues.get(0);
		String str = map.get(current);
		String ch = "" + str.charAt(0);
		out.print(str);
		for(int i = 1; i < binaryValues.size(); i++)
		{
			int next = binaryValues.get(i);

			// adding keys to dictionary, reverse-engineering compression dictionary following LZW rules
			if(!map.containsKey(next))
			{
				str = map.get(current);
				str = str + ch;
			}
			else
			{
				str = map.get(next);
			}
			out.print(str);
			ch = "" + str.charAt(0);
			map.put(nextKey, map.get(current) + ch);
			nextKey++;
			current = next;
		}
		
		out.println();
		
		out.close();
	}

	// converts number to a String of binary
    public static String toBinary(int num)
	{
		String current = Integer.toBinaryString(num);
		StringBuilder str = new StringBuilder();
		while(current.length()+str.length() < 8)
		{
			str.append("0");
		}
		if(current.length() > 8)
		{
			current = current.substring(current.length() - 8);
		}
		str.append(current);
		return str.toString();
	}

	// converts a binary String into an integer
    public static int toBinaryValue(String str)
	{
		int end = 0;
		for(int i = 0; i < 9; i++)
		{
			if(str.charAt(i) == '1')
			{
				end += (1 << (8 - i));
			}
		}
		return end;
	}

	// tester
    public static void main (String [] args) throws IOException
    {
        decode("encoded3.bin", "decoded.txt");
    }
}
