import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
// import org.apache.commons.io.IOUtils;



public class LZW
{
  private ArrayList<Integer> compressedAscii;
  private HashMap<String, Integer> dictionary;
  // private HashMap<Integer, String> Invertedictionary;
  private String fileInAString;
  private ArrayList<String> inputFileInAnArrayList;
  private ArrayList<String> preDictionary;
  private ArrayList<String> update;
  private BufferedWriter decodedFile;
  private ArrayList<Integer> decodedFileUnicodeValues;
  private ObjectInputStream is;
  private BufferedReader input;
  private ObjectOutputStream os;
  private FileInputStream fis;

  public LZW() throws Exception
  {
    decodedFileUnicodeValues = new ArrayList<Integer>();
    dictionary = new HashMap<String, Integer>();
    // Invertedictionary = new HashMap<Integer, String>();
    String fileName = "encoded3.bin";
    FileOutputStream fileOs = new FileOutputStream(fileName);
    os = new ObjectOutputStream(fileOs);
    compressedAscii = new ArrayList<Integer>();
    inputFileInAnArrayList = new ArrayList<String>();
    preDictionary = new ArrayList<String>();
    update = new ArrayList<String>();
    fis = new FileInputStream(fileName);
    is = new ObjectInputStream(fis);
    input = new BufferedReader(new FileReader("lzw-file3.txt"));
    // decodedFile = new BufferedWriter(new FileWriter("decoded2.txt"));
    fileInAString = "";
  }

public void readFile()throws Exception
{
String line = input.readLine();
  while(line != null)
  {
    fileInAString += line;
    line = input.readLine();
  }

  for(int i = 0; i < fileInAString.length(); i++)
  {
    inputFileInAnArrayList.add(fileInAString.substring(i, i+1));
  }

}

public void convertFileStringToArrayDictionary()throws Exception
{
  readFile();
  String temp = inputFileInAnArrayList.get(0);
  String temp2 = inputFileInAnArrayList.get(1);
  for (int i = 0; i < inputFileInAnArrayList.size(); i++)
  {
    if(preDictionary.contains(temp+temp2) == false)
    {

      preDictionary.add(temp+temp2);
      update.add(temp);
      if(i+2 < inputFileInAnArrayList.size())
      {
      temp = inputFileInAnArrayList.get(i+1);
      temp2 = inputFileInAnArrayList.get(i+2);
    }
    }
    else if(preDictionary.contains(temp+temp2) == true)
    {
      temp = temp + temp2;
      if(i+2<inputFileInAnArrayList.size())
      {
        temp2 = inputFileInAnArrayList.get(i+2);
      }
    }
  }
}

public void dictionaryPopulators()
{
  int counter = 256;
  for(int i = 0; i < 256; i++)
  {
    char ascii = (char) i;
    dictionary.put(Character.toString(ascii), i);
  }
  for(int k = 0; k < preDictionary.size(); k++)
  {
    dictionary.put(preDictionary.get(k), counter+k);
  }
  //inverted dictionary is dictionary with values and keys swapped places

  
}




public void fileCompressor() throws Exception
{
      for(int i = 0; i < update.size(); i++)
      {
        if(update.get(i).length() == 1)
        {
          compressedAscii.add((int) (update.get(i).charAt(0)));
        }
        else
        {
          compressedAscii.add(dictionary.get(update.get(i)));
        }
      }
      convertIntValuesToBinary();
}


public void convertIntValuesToBinary()throws Exception
{

  for(int i = 0; i < compressedAscii.size(); i++)
  {
    os.writeInt(compressedAscii.get(i));
  }

  for(int i = 0; i < compressedAscii.size(); i++)
  {
    try {

    decodedFileUnicodeValues.add(is.readInt());
  }catch(IOException e){ }
  }

}

// public void decode() throws Exception
// {
//   // int byteRead;
//   // String decoded = "";
//   // while ((byteRead = fis.read()) != -1);
//   // {
//   //   decoded += byteRead;
//   // }
//   // System.out.println(decoded);
//   String body = IOUtils.toString(fis, StandardCharsets.UTF_8.name()); 

// }

// public void decode() throws Exception
// {
//   int counter = 256;
//   for(int i = 0; i < 256; i++)
//   {
//     char ascii = (char) i;
//   Invertedictionary.put(i, Character.toString(ascii));
//   }
//   for(int k = 0; k < preDictionary.size(); k++)
//   {
//     Invertedictionary.put(counter+k, preDictionary.get(k));
//   }
  
//   for(int i = 0; i < decodedFileUnicodeValues.size(); i++)
//   {
//     int temp = decodedFileUnicodeValues.get(i);
//     decodedFile.write(Invertedictionary.get(temp));
//   }
//   decodedFile.close();
// }

public void fileEncoder()throws Exception
{
  convertFileStringToArrayDictionary();
  dictionaryPopulators();
  fileCompressor();
}

public static void main(String[] args)throws Exception
{
  LZW thing = new LZW();
  thing.fileEncoder();
}
}
