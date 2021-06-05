# XML Frame Parser

Simple XML Parser that parses XML file and make java swing JFrame with supported component.

## Supported Components

JFrame :
  - title [String]
  - width [Integer]
  - height [Integer]
  - visibility [Boolean]
  - layout [String]

JButton :
  - text [String]
  - foreground [String]
  - background [String]
  - layout [String]

JLabel :
  - text [String]
  - tool-tip-text [String]
  - foreground [String]
  - layout [String]

JPanel :
  - background [String]
  - layout [String]

JTextField :
  - tool-tip-text [String]
  - foreground [String]
  - layout [String]

JCheckBox :
  - text [String]
  - foreground [String]
  - selected [Boolean]
  - layout [String]

# Supported Layouts

BorderLayout :
  - layout_gravity [String] (For children of tag)

    - center (Default)
    - north
    - south
    - west
    - east

FlowLayout :
  - alignment [String] (For tag itself)

    - center (Default)
    - left
    - right

GridLayout :
  - cols [Integer] (Necessary)
  - rows [Integer] (Necessary)

# Example


<JFrame
        title="Xml parser"
        width="500"
        height="300"
        layout="BorderLayout">

    <JPanel
            layout_gravity="north"
            background="black"
            layout="FlowLayout"
            alignment="left">

        <JButton text="File"/>
        <JButton text="Edit"/>
        <JButton text="View"/>
        <JButton text="Navigate"/>
        <JButton text="Code"/>

    </JPanel>

    <JPanel
            layout_gravity="center"
            background="gray"
            layout="GridLayout"
            rows="4"
            cols="2">

        <JLabel text="name : "/>
        <JTextField/>
        <JLabel text="age : "/>
        <JTextField/>
        <JLabel text="height : "/>
        <JTextField/>
        <JLabel text="weight : "/>
        <JTextField/>
    </JPanel>

    <JPanel
            layout_gravity="west"
            background="gray"
            layout="GridLayout"
            rows="3"
            cols="1">

        <JCheckBox
                text="Single"
                selected="true"/>

        <JCheckBox text="Old"/>
        <JCheckBox text="Owner"/>
    </JPanel>
</JFrame>

![] (https://github.com/IlyaKhalafi/XML-Parser/blob/master/examples/output.png)
