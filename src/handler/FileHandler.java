package handler;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileHandler {
    private final static boolean DEBUGMODE = false;

    /**
     * Metoden skriver til en fil med den kaldte titel og tekst
     * @param title navnet på den fil man vil skrive.
     * @param lineArr et array indeholdende de linier man ønsker skal stå i filen. 
     * @throws IOException
     */
    public static void writeFile(String title, String[] lineArr) throws IOException {
        FileWriter fw = new FileWriter(title);
        PrintWriter pw = new PrintWriter(fw);

        for (int i = 0; i < lineArr.length; i++) {
            pw.println(lineArr[i]);
        }
        pw.close();
        
    }

    /**
     * Metoden læser fra en fil med den kaldte titel ved hjælp af en Scanner og 
     * en ArrayList<String>
     * @param url filsti og filnavn på filen man vil læse fra
     * @return ArrayList<String> af aflæste linjer i filen.
     * @throws FileNotFoundException
     */
    public static ArrayList<String> readFile(String url) throws FileNotFoundException {
        
        Scanner fileScanner = new Scanner(new File(url));
        ArrayList<String> stringList = new ArrayList<>();

        while (fileScanner.hasNext()) {
            stringList.add(fileScanner.nextLine());
        }
        fileScanner.close();
        return stringList;
    }

}
