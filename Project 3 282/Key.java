public class Key implements Comparable<Key>{
    public String key;
    public int gFirst;
    public int gLast;
    public Character firstLetter;
    public Character lastLetter;
    public int totalValue;
    public int firstLetterValue;
    public int lastLetterValue;
    public int positionInGFirst;//keeps count of row position of gvalue of select key
    public int positionInGLast;//keeps count of row position of gvalue of select key
    public int wordSize;
    public int unassignedLetterPosition;
    public int counter;//counts the number of times the word appears
    public Key(String newWord){
        key = newWord;
        wordSize = newWord.length();
        gFirst = 0;
        gLast = 0;
        firstLetter = newWord.charAt(0);
        lastLetter = newWord.charAt(newWord.length()-1);
        totalValue = 0;
        positionInGFirst = 0;
        positionInGLast = 0;
        unassignedLetterPosition = 0;
        counter = 0;
    }

   /* public String getData(){
        return key;
    }
    public int getGFrist(){
        return gFirst;
    }
    public int getGLast(){
        return gLast;
    }*/
    public int size(){return wordSize;}
    public Character getFirstLetter(){return firstLetter;}
    public Character getLastLetter(){return lastLetter;}
    public String toString(){
        return "Key: " + key + "\nfirst letter: " + firstLetter +
                "\nlast letter: "  + lastLetter + "\ntotal value: " +
                totalValue + "\n " + "frequency: " + counter ;
    }
    public void setTotalValue(){
        totalValue = firstLetterValue + lastLetterValue;
    }
    public int compareTo(Key comparesTo)	{
        int compareValue = ((Key)comparesTo).totalValue;
        return compareValue-this.totalValue;
    }


}

