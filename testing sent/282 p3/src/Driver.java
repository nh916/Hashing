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

    private void readKeys(String file){
        String keyFromFile;
        try (Scanner scan = new Scanner(new FileInputStream(file))) {

            while (scan.hasNext()) {
//                keyFromFile = (scan.nextLine().replaceAll("[\\p{Punct}\\p{Blank}]+", ""));
//                does away with all punctuation, blank spaces, digits
//                keyFromFile = (scan.nextLine().replaceAll("[\\p{Punct}\\p{Blank}\\p{Digit}]+", ""));

//                removes all punctuation and spaces
                keyFromFile = (scan.nextLine().replaceAll("[\\p{Punct}\\p{Blank}]+", ""));

//                inserts the words only if they are words not empty this is an extra check to prevent any empty characters from getting in there and makes the program
                if (!keyFromFile.isEmpty()){
                    keysToBeHashed.add(new Key(keyFromFile.toLowerCase()));
                }

//                System.out.println(keysToBeHashed);

            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (NullPointerException e) {
            System.out.println("file error");
        }
    }

    //    reads it word by word
    private void readTxtWordByWord(String file){
        String wordFromTxtFile = "";
        try (Scanner scan = new Scanner(new FileInputStream(file))) {

            while (scan.hasNext()) {
//                strips off all punctuations from the string
                wordFromTxtFile = scan.next().toLowerCase();

//                remove all punctuations only
//                wordFromTxtFile = wordFromTxtFile.replaceAll("[\\p{Punct}]", "");

//                remove all punctuation and blank spaces
                wordFromTxtFile = wordFromTxtFile.replaceAll("[\\p{Punct}]+\\p{Blank}*", "");


//                only puts in the words if that are not empty
                if (!wordFromTxtFile.isEmpty()){
                    wordsRead++;
                    words.add(wordFromTxtFile);
                    //System.out.println(wordFromTxtFile);
                }


                //linesRead++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (NullPointerException e) {
            System.out.println("file error");
        }
    }
    private void findNumberOfLine(String file){
//        int linesRead = 0;
        Scanner scan = null;
        String line = "";
        int blankLines =0;//handles the blank lines within the code
        //if a blank line is found, it is taken into account. Then the number of blank lines returned is subtracted from the total lines read
        try {
            scan = new Scanner(new FileInputStream(file));
            while(scan.hasNextLine()){
                line = scan.nextLine();

                if(line.equals("")){
                    blankLines++;
                }

                linesRead++;

            }
            linesRead = linesRead - blankLines;
            //System.out.println(linesRead);
        }
        catch (NullPointerException e){
            System.out.println("file error");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (scan != null){
                scan.close();
            }
        }


    }

    private void statistics(HashTable keys, ArrayList<String> words){

        System.out.println("**********************");
        System.out.println("***** Statistics *****");
        System.out.println("**********************");
        System.out.println("Total Lines Read: " + linesRead);
        System.out.println("Total Words Read: " + wordsRead);
        System.out.println("Break Down by Key Word");
        keys.checkForKeyWord(words);


        int sum = 0;
        for(int i = 0; i < keys.hashEntry.length;i++){
            System.out.println("\t" + keys.hashEntry[i].key  + ": " + keys.hashEntry[i].counter);
            sum += keys.hashEntry[i].counter;
        }
        System.out.println("Total Keys: " + sum);



    }
    //Returns the keys read by files
    private ArrayList<Key> getKeys(){
        return keysToBeHashed;
    }

    public static void main(String[] args){
        Driver driver = new Driver();
        driver.readKeys("keys-1.txt");
        driver.readTxtWordByWord("text-1.txt");
        driver.findNumberOfLine("text-1.txt");

//        just in case
        try {
            HashTable hash = new HashTable(driver.getKeys());
            driver.statistics(hash,driver.words);
        }

        catch (Exception e){
            System.out.println("Error in the hashing process");
        }

    }

}
