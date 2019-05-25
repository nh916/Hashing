import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Read {
    public Read() {

    }


//    reads every line and puts takes the keys
    public void readKeys(String file){
        Scanner scan = null;
        String keyFromFile;
        try {
            scan = new Scanner(new FileInputStream(file));

            while (scan.hasNext()){
                keyFromFile = (scan.nextLine());


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
//                System.out.println(wordFromTxtFile);

                if (wordFromTxtFile.contains(",") || wordFromTxtFile.contains(".")){

                }

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



    public static void main(String[] args){
        Read read = new Read();
        read.readKeys("keys-1.txt");
        read.readTxtWordByWord("text-1.txt");

    }
}
