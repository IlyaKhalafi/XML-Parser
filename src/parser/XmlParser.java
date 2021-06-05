package parser;

import javax.swing.JFrame;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class XmlParser extends Container{

    private String[] elements; // Content of xml file
    private Stack<Container> parents = new Stack<>(); // Parent of current element in process..
    private JFrame result;

    public XmlParser(String doc) {
        this.elements = extractElements(doc);
        this.parents.push(this);
        result = null;
    }

    public void BuildFrame() {
        if( result != null )
            return;

        for(int i = 0; i < elements.length; i++) {

            if( elements[i].startsWith("</") )
                parents.pop();

            else {
                Container c = MakeObjectAtOffset(i, parents.peek());
                if( !elements[i].endsWith("/>") )
                    parents.push(c);
            }
        }
    }


    private Container MakeObjectAtOffset(int offset, Container parent) {
        HashMap<String, String> tags = CollectTags(elements[offset]);
        Container comp = null;

        if ( tags.get("element_name").equalsIgnoreCase("JFrame") )
            comp = ComponentManager.MakeFrame( tags, parent );

        else if ( tags.get("element_name").equalsIgnoreCase("JButton") )
            comp = ComponentManager.MakeButton( tags, parent );

        else if ( tags.get("element_name").equalsIgnoreCase("JLabel") )
            comp = ComponentManager.MakeLabel( tags, parent );

        else if ( tags.get("element_name").equalsIgnoreCase("JPanel") )
            comp = ComponentManager.MakePanel( tags, parent );

        else if ( tags.get("element_name").equalsIgnoreCase("JTextField") )
            comp = ComponentManager.MakeTextField( tags, parent );

        else if ( tags.get("element_name").equalsIgnoreCase("JCheckBox") )
            comp = ComponentManager.MakeCheckBox( tags, parent );
        else
            throw new IllegalArgumentException("Element is not valid!");

        return comp;
    }

    private HashMap<String, String> CollectTags(String element) {
        HashMap<String, String> tags = new HashMap<>();
        String[] parts = validateElement(element).split(" ");

        tags.put("element_name", parts[1]);

        for(int offset = 1; offset < parts.length; offset++) {

            if( parts[offset].equals("=") ) {
                String key = parts[offset-1];

                String value = "";
                offset++;
                while( !parts[offset].equals(">") && !parts[offset].equals("/>") && !parts[offset+1].equals("=") )
                    value += parts[offset++] + " ";

                tags.put(key, value.trim());
            }
        }

        return tags;
    }

    public void setResult(JFrame frame) { this.result = frame; }

    private String validateElement(String element) {
        // 1. Pushing Space between <, >, /> and other parts
        // 2. Uniting all whitespace with a single space
        // 3. Removing double quotations

        String standard_element = "";
        Boolean isWhiteSpace = false;

        String s = "";
        for(int i = 0; i < element.length(); i++) {
            if( element.charAt(i) == '=' )
                s += " = ";
            else
                s += element.charAt(i);
        }
        element = s;

        for(int i = 0; i < element.length(); i++) {

            if( isWhiteSpace == true && !Character.isWhitespace(element.charAt(i)) ) {
                isWhiteSpace = false;
                standard_element += " ";
            }

            if( Character.isWhitespace(element.charAt(i)) )
                isWhiteSpace = true;

            else if( element.charAt(i) == '<' && element.charAt(i+1) == '/' ) {
                String temp = "";

                do { temp += element.charAt(i); }
                while( i < element.length() && element.charAt(i++) != '>' );

                if( !standard_element.endsWith(" ") )
                    standard_element += " ";
                standard_element += temp;
            }

            else if( element.charAt(i) == '<' && element.charAt(i+1) != ' ' && element.charAt(i+1) != '/' )
                standard_element += "< ";

            else if( element.charAt(i) == '/' && element.charAt(i+1) == '>' && !standard_element.endsWith(" ") ) {
                standard_element += " />";
                i++;
            }

            else if( element.charAt(i) == '>' && !standard_element.endsWith(" ") && element.charAt(i-1) != '/' )
                standard_element += " >";

            else if( element.charAt(i) == '\"' )
                continue;

            else
                standard_element += element.charAt(i);
        }

        return standard_element;
    }
    
    private String[] extractElements(String doc) {
        ArrayList<String> data = new ArrayList<>();

        for(int i = 0; i < doc.length(); i++) {

            if( doc.charAt(i) == '<' ) {
                String temp = "";

                do { temp += doc.charAt(i); }
                while( i < doc.length() && doc.charAt(i++) != '>' );

                data.add(temp.trim());
            }
        }

        return data.toArray(new String[data.size()]);
    }

    public JFrame getFrame() { return result; }
}
