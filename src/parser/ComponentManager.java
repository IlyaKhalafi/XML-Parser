package parser;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashMap;

public class ComponentManager {

    private static final String[] jframe_close_operations = {"DO_NOTHING_ON_CLOSE", "HIDE_ON_CLOSE", "DISPOSE_ON_CLOSE", "EXIT_ON_CLOSE"};
    private static final String[] flowlayout_alignment = {"left", "center", "right"};
    private static final String[] borderlayout_gravity = {BorderLayout.CENTER, BorderLayout.NORTH, BorderLayout.WEST, BorderLayout.SOUTH, BorderLayout.EAST};

    private ComponentManager() {
    }

    public static JFrame MakeFrame(HashMap<String, String> attrs, Container parent) {
        JFrame frame = new JFrame();

        if (attrs.get("title") != null)
            frame.setTitle(attrs.get("title"));

        if (attrs.get("width") != null)
            frame.setSize(Integer.parseInt(attrs.get("width")), frame.getHeight());

        if (attrs.get("height") != null)
            frame.setSize(frame.getWidth(), Integer.parseInt(attrs.get("height")));

        if (attrs.get("visibility") != null)
            frame.setVisible(Boolean.parseBoolean(attrs.get("visibility")));

        if (attrs.get("default_close_operation") != null) {
            for (int i = 0; i < 4; i++)
                if (jframe_close_operations[i].equals(attrs.get("default_close_operation")))
                    frame.setDefaultCloseOperation(i);
        }

        if (attrs.get("layout") != null)
            ComponentManager.SetProperLayout(attrs, frame);

        ((XmlParser) parent).setResult(frame);
        return frame;
    }

    public static JButton MakeButton(HashMap<String, String> attrs, Container parent) {
        JButton button = new JButton();

        if (attrs.get("text") != null)
            button.setText(attrs.get("text"));

        if (attrs.get("foreground") != null)
            button.setForeground(getColor(attrs.get("foreground")));

        if (attrs.get("background") != null)
            button.setBackground(getColor(attrs.get("background")));

        if (attrs.get("layout") != null)
            ComponentManager.SetProperLayout(attrs, button);

        if( attrs.get("layout_gravity") != null ) {
            for (String gravity : borderlayout_gravity)
                if (gravity.equalsIgnoreCase(attrs.get("layout_gravity")))
                    parent.add(button, gravity);
        }
        else
            parent.add(button);

        return button;
    }

    public static JLabel MakeLabel(HashMap<String, String> attrs, Container parent) {
        JLabel label = new JLabel();

        if (attrs.get("text") != null)
            label.setText(attrs.get("text"));

        if (attrs.get("tool_tip_text") != null)
            label.setToolTipText(attrs.get("tool_tip_text"));

        if (attrs.get("foreground") != null)
            label.setForeground(getColor(attrs.get("foreground")));

        if (attrs.get("layout") != null)
            ComponentManager.SetProperLayout(attrs, label);

        if( attrs.get("layout_gravity") != null ) {
            for (String gravity : borderlayout_gravity)
                if (gravity.equalsIgnoreCase(attrs.get("layout_gravity")))
                    parent.add(label, gravity);
        }
        else
            parent.add(label);

        return label;
    }

    public static JPanel MakePanel(HashMap<String, String> attrs, Container parent) {
        JPanel panel = new JPanel();

        if (attrs.get("background") != null)
            panel.setBackground(getColor(attrs.get("background")));

        if (attrs.get("layout") != null)
            ComponentManager.SetProperLayout(attrs, panel);

        if( attrs.get("layout_gravity") != null ) {
            for (String gravity : borderlayout_gravity)
                if (gravity.equalsIgnoreCase(attrs.get("layout_gravity")))
                    parent.add(panel, gravity);
        }
        else
            parent.add(panel);

        return panel;
    }

    public static JTextField MakeTextField(HashMap<String, String> attrs, Container parent) {
        JTextField textField = new JTextField();

        if (attrs.get("tool_tip_text") != null)
            textField.setToolTipText(attrs.get("tool_tip_text"));

        if (attrs.get("foreground") != null)
            textField.setForeground(getColor(attrs.get("foreground")));

        if (attrs.get("layout") != null)
            ComponentManager.SetProperLayout(attrs, textField);

        if( attrs.get("layout_gravity") != null ) {
            for (String gravity : borderlayout_gravity)
                if (gravity.equalsIgnoreCase(attrs.get("layout_gravity")))
                    parent.add(textField, gravity);
        }
        else
            parent.add(textField);

        return textField;
    }

    public static JCheckBox MakeCheckBox(HashMap<String, String> attrs, Container parent) {
        JCheckBox checkBox = new JCheckBox();

        if (attrs.get("text") != null)
            checkBox.setText(attrs.get("text"));

        if (attrs.get("foreground") != null)
            checkBox.setForeground(getColor(attrs.get("foreground")));

        if (attrs.get("selected") != null)
            checkBox.setSelected(Boolean.parseBoolean(attrs.get("selected")));

        if (attrs.get("layout") != null)
            ComponentManager.SetProperLayout(attrs, checkBox);

        if( attrs.get("layout_gravity") != null ) {
            for (String gravity : borderlayout_gravity)
                if (gravity.equalsIgnoreCase(attrs.get("layout_gravity")))
                    parent.add(checkBox, gravity);
        }
        else
            parent.add(checkBox);

        return checkBox;
    }

    private static void SetProperLayout(HashMap<String, String> attrs, Container parent) {
        if (attrs.get("layout").equals("BorderLayout"))
            parent.setLayout(new BorderLayout());

        else if (attrs.get("layout").equals("FlowLayout")) {
            for (int i = 0; i < 3; i++)
                if (flowlayout_alignment[i].equals(attrs.get("alignment")))
                    parent.setLayout(new FlowLayout(i));
        }

        else if (attrs.get("layout").equals("GridLayout")) {
            if( attrs.get("rows") == null || attrs.get("cols") == null )
                throw new IllegalArgumentException("GridLayout needs row and column size!");
            else
                parent.setLayout(new GridLayout(Integer.parseInt(attrs.get("rows")), Integer.parseInt(attrs.get("cols"))));
        }

        else
            throw new IllegalArgumentException("Layout was not recognized!");

    }

    private static Color getColor(String name) {
        Color color;
        try {
            Field field = Color.class.getField(name);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null;
        }
        return color;
    }
}