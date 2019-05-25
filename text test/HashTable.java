
import java.util.*;

public class HashTable {

    private static final int DEFAULT_TABLE_SIZE = 101;

    public Key[] hashEntry;//holds the hashtable made after the keys have been placed in the right index by the chichelli algorithm
    private ArrayList<Key> keys;
    //The following instance variables are used to created the order in which the keys will be placed into the hash table
    private int[] letters = new int[26];
    private ArrayList<Character> uniqueLetters;
    private int[][] g;
    private Stack<Key> keyWordStack = new Stack<Key>();
    private int modValue;
    private int maxValue;
    public HashTable(ArrayList<Key> key) {
        hashEntry = new Key[key.size()];
        keys = key;
        uniqueLetters = new ArrayList<Character>();
        maxValue = keys.size()/2;
        modValue = keys.size();
        cichelli();

    }
    //used to calculate the hash value of words that are not a part of the keys
    public int hash(String word, int gFirst, int gLast, int modValue){
        int hashValue = (word.length() + gFirst + gLast) % modValue;
        return hashValue;
    }
    public int hash(Key word, int gFirst, int gLast, int modValue){
        int hash = 0;
        hash = (word.length() + gFirst + gLast) % modValue;
        return hash;
    }
    public void cichelli(){
        initializeLetters();
        setValueOfAllKeys();
        initializeUniqueLetters();
        // all previous methods work together to create the order in which the keys are inserted into the hashtable.
        g = new int[uniqueLetters.size()][3];
        initializeGArray();
        for (int i = (keys.size()-1); i >= 0; i -= 1)	{
            keyWordStack.push(keys.get(i));
        }
        cichelliRec(keyWordStack, hashEntry, g, modValue,maxValue);

    }
    private boolean cichelliRec(Stack<Key> keyWordStack, Key[] hashTable, int[][] g, int modValue, int maxValue){
        while(keyWordStack.empty() != true){
            Key word = keyWordStack.pop();

            //finding first and last g values by comparing 2d gArray to first and last letters of word.
            //setPositionInG refers to the row of the array where the letters was found.
            for (int i = 0; i < g.length; i += 1)	{
                if (g[i][1] == ((int) word.getFirstLetter()))	{
                    word.positionInGFirst = i;
                }
                if (g[i][1] == ((int) word.getLastLetter()))	{
                    word.positionInGLast = i;
                }
            }

            if(g[word.positionInGFirst][2]==1 && g[word.positionInGLast][2]==1) {
                //check if hash value for word is valid.
                if (hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] == null) {
                    //assign hash value to word
                    hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] = word;
                    //recursively call cichelli method
                    if (cichelliRec(keyWordStack, hashTable, g, modValue, maxValue) == true) {
                        return true;
                    }
                    //detach the hash value for word
                    else {
                        hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] = null;
                        //set values of g array column 2 back to zero.
                    }
                }
                keyWordStack.push(word);
                return false;
            }
            else if((g[word.positionInGFirst][2] == 0) && (g[word.positionInGLast][2] == 0) && (word.getFirstLetter() != word.getLastLetter())){
                for(int m = 0; m <(maxValue+1); m+= 1){
                    if(m>0){
                        g[word.positionInGFirst][0]+=1;
                    }
                    //check if hash value is valid if not, keep increasing g value
                    if (hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] == null) {
                        hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] = word;
                        g[word.positionInGFirst][2]=1;
                        g[word.positionInGLast][2] = 1;
                        if(cichelliRec(keyWordStack, hashTable, g, modValue, maxValue) == true){
                            return true;
                        }
                        else{
                            hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] = null;
                            //set values of g array column 2 back to zero.
                            g[word.positionInGFirst][2] = 0;
                            g[word.positionInGLast][2] = 0;

                        }
                    }

                }
                g[word.positionInGFirst][0]=0;
                keyWordStack.push(word);
                return false;
            }
            else{
                if (g[word.positionInGFirst][2] == 0)	{
                    word.unassignedLetterPosition=word.positionInGFirst;

                }
                else if (g[word.positionInGLast][2] == 0)	{
                    word.unassignedLetterPosition= word.positionInGLast;

                }
                else{
                    word.unassignedLetterPosition = -1;
                }
                for(int m = 0; m < (maxValue +1); m+=1){
                    if(m>0){
                        g[word.unassignedLetterPosition][0]+=1;
                    }
                    if(hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] == null){
                        hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] =word;
                        g[word.unassignedLetterPosition][2] = 1;
                        if(cichelliRec(keyWordStack, hashTable, g, modValue, maxValue) == true){
                            return true;
                        }
                        else{
                            hashTable[hash(word, g[word.positionInGFirst][0], g[word.positionInGLast][0], modValue)] = null;
                            g[word.unassignedLetterPosition][2] = 0;
                        }

                    }
                }
                g[word.unassignedLetterPosition][0]=0;
                keyWordStack.push(word);
                return false;
            }

        }
        return true;
    }
    // used to count the number of frequencies of each key in the text.
    //Since the key itself already holds a counter, this method just calculates the hash value of each word in the text.
    //If that hash value matches any index within the hashtable, the key at the index has its counter increased, meaning
    //the same word was found again.
    //used in the driver class
    public void checkForKeyWord(ArrayList<String> words)	{
        for(String word: words)	{
            char firstLetter = word.charAt(0),  lastLetter = word.charAt(word.length() - 1);
            boolean firstLetterBool = false, lastLetterBool = false;
            int firstGvalue = 0, lastGvalue = 0;


            for(int i = 0; i < g.length; i += 1)	{
                if(firstLetter == (char) g[i][1]) {
                    firstLetterBool = true;
                    firstGvalue = g[i][0];
                }
                if (lastLetter == (char) g[i][1])	{
                    lastLetterBool = true;
                    lastGvalue = g[i][0];
                }
            }
            if (firstLetterBool == true && lastLetterBool == true)	{
                int index = hash(word, firstGvalue, lastGvalue, hashEntry.length);
                if (word.equals(hashEntry[index].key))	{
                   /* System.out.println("Word: " + word + " index: " + index);
                    System.out.println(hashEntry[index].key);*/
                    hashEntry[index].counter += 1;
                }
            }
        }
    }
    //Finds all the reoccurence of letters within the keys. Finds the letters by comparing the ascii code of each character
    private  void initializeLetters(){
       //using an array of 26 to keep track of occurences of letters
       //initialize all array values to zero.
       //also sets an array list of unique letters, comprised of all the letters that actually appear within then the keys
       for (int i = 0; i < 26; i += 1)	{
           letters[i] = 0;
       }
       // count the frequency that each letter appears as a first or last letter
       for (Key e: keys)	{
           for (int i = 97; i < 123; i += 1)	{
               if (e.getFirstLetter() == (char) i)	{
                   letters[i-97] += 1;

               }
               if (e.getLastLetter() == (char) i)	{
                   letters[i - 97] += 1;

               }
           }//end of for loop
       }//end of foreach loop
   }
   //creates a smaller version of the array created in previous method
    //it takes all of the letters in which the value was not 0, meaning that letters in a space with any other value are unique letters
    //they are the letters that appear more than once
    private void initializeUniqueLetters(){
       for(int i = 0; i < letters.length; i += 1)	{
           if (letters[i] != 0)	{
               uniqueLetters.add((char) (i+97));
           }
       }
   }
   //sets the valuue of each key by adding the first and last letter values of every key
    //then sorts in descending order. this decides the order in which the keys are put into the hashtable
    private void setValueOfAllKeys()	{
       //set values of keys
        for(Key k : keys)	{
            int firstLetterValue = letters[((int) k.getFirstLetter() - 97)];
            k.firstLetterValue = firstLetterValue;
            int lastLetterValue = letters[((int) k.getLastLetter() - 97)];
            k.lastLetterValue =lastLetterValue;
            int totalValue = firstLetterValue + lastLetterValue;
            k.setTotalValue();
        }
        Collections.sort(keys);
    }
    //creates the g array for use in the cichelli method
    private void initializeGArray(){
        for (int i = 0; i < uniqueLetters.size(); i += 1)	{
            //collumn 0 holds g values. all initialized to zero
            g[i][0] = 0;
            //collumn 1 holds ascci values of letters.
            g[i][1] = (int) uniqueLetters.get(i);
            //collumn 2 holds 0 if g-value is not set and 1 if g-value is set. all initialized to zero.
            g[i][2] = 0;
        }

    }
}
