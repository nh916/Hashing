import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver{
    public ArrayList<Key> keysToBeHashed;
    public ArrayList<String> words;
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
        String wordFromTxtFile;

        try {
            scan = new Scanner(new FileInputStream(file));
            while (scan.hasNext()){
                wordFromTxtFile = scan.next();
                //wordsRead++;
                System.out.println(wordFromTxtFile);

                if (wordFromTxtFile.contains(",") || wordFromTxtFile.contains(".")){

                }

                //after each line
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
                    System.out.println(keys.hashEntry[i]  + ": " + keys.hashEntry[i].counter);
                    sum += keys.hashEntry[i].counter;
              }
              System.out.println("Total Keys:" + sum);



    }

    public static void main(String[] args){
        Driver test = new Driver();
        test.readKeys("keys-1.txt");
        HashTable hash = new HashTable(test.keysToBeHashed);

        test.statistics(hash,test.words);




    }

}
