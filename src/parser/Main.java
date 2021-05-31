package parser;

import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        JFrame frame = parseMML("test.xml");
    }

    public static JFrame parseMML(String fileName) {
        String doc_data = "";

        try {
            Scanner sc = new Scanner(new File(fileName));
            while(sc.hasNext())
                doc_data += sc.nextLine();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        XmlParser parser = new XmlParser(doc_data);
        parser.BuildFrame();
        try { parser.getFrame().revalidate(); }
        catch (Exception e) { System.out.println(e.getMessage()); }

        return parser.getFrame();
    }
}
