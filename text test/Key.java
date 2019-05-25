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
        this.key = newWord;
        this.wordSize = newWord.length();
        this.gFirst = 0;
        this.gLast = 0;
        this.firstLetter = newWord.charAt(0);
        this.lastLetter = newWord.charAt(newWord.length()-1);
        this.totalValue = 0;
        this.positionInGFirst = 0;
        this.positionInGLast = 0;
        this.unassignedLetterPosition = -1;
        this.counter = 0;
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
    public int length(){return wordSize;}
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

