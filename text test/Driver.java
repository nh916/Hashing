import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.*;

public class Driver{
    private ArrayList<Key> keysToBeHashed;
    private ArrayList<String> words;
    private int linesRead;
    private int wordsRead;

    public Driver() {
        keysToBeHashed = new ArrayList<Key>();
        words = new ArrayList<String>();//contains words from text
        linesRead =0;
        wordsRead = 0;
    }
    public void readKeys(String file){
        Scanner scan = null;
        String keyFromFile;
        try {
            scan = new Scanner(new FileInputStream(file));

            while (scan.hasNext()){
                keyFromFile = (scan.nextLine());
                keysToBeHashed.add(new Key(keyFromFile.toLowerCase()));

            }
        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        catch (NullPointerException e){
            System.out.println("file error");
        }
        finally {
            if (scan != null){
                scan.close();
            }
        }
    }
    //    reads it word by word
    public void readTxtWordByWord(String file){
        Scanner scan = null;

        String wordFromTxtFile = "";


        try {

            scan = new Scanner(new FileInputStream(file));
            while (scan.hasNext()){
//                strips off all punctuations from the string
                wordFromTxtFile = scan.next().toLowerCase();

                wordFromTxtFile = wordFromTxtFile.replaceAll("[^\\w\\s ]", "");

            wordsRead++;
                words.add(wordFromTxtFile);
                //System.out.println(wordFromTxtFile);

                //linesRead++;
            }

        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        catch (NullPointerException e){
            System.out.println("file error");
        }
        finally {
            if (scan != null){
                scan.close();
            }
        }
    }
    public void findNumberOfLine(String file){
//        int linesRead = 0;
        Scanner scan = null;
        String line = "";
        int blankLines =0;//handles the blank lines within the code
        //if a blank line is found, it is taken into account. Then the number of blank lines returned is subtracted from the total lines read
        try {
            scan = new Scanner(new FileInputStream(file));
            while(scan.hasNextLine()){
                line = scan.nextLine();
                if(line.equals("")) blankLines++;

                linesRead++;

            }
            linesRead-=blankLines;
            //System.out.println(linesRead);
        }
        catch (NullPointerException e){
            System.out.println("file error");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scan != null){
                scan.close();
            }
        }


    }

    public void statistics(HashTable keys, ArrayList<String> words){

            System.out.println("**********************");
            System.out.println("***** Statistics *****");
            System.out.println("**********************");
            System.out.println("Total Lines Read: " + linesRead);
            System.out.println("Total Words Read: " + wordsRead);
            System.out.println("Break Down by Key Word");
            keys.checkForKeyWord(words);
            int sum = 0;
              for(int i = 0; i < keys.hashEntry.length;i++){
                    System.out.println(keys.hashEntry[i].key  + ": " + keys.hashEntry[i].counter);
                    sum += keys.hashEntry[i].counter;
              }
              System.out.println("Total Keys: " + sum);



    }
    //Returns the keys read by files
    public ArrayList<Key> getKeys(){
        return keysToBeHashed;
    }
    public static void main(String[] args){
        Driver test = new Driver();
        test.readKeys("keys-1.txt");
        test.readTxtWordByWord("text-1.txt");
        test.findNumberOfLine("text-1.txt");
        HashTable hash = new HashTable(test.getKeys());

        test.statistics(hash,test.words);



    }

}
