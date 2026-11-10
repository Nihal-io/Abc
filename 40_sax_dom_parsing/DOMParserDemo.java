/*
 * Question 40: XML document processing using SAX and DOM.
 *
 * DOMParserDemo.java - reads students.xml with DOM (Document Object Model).
 * DOM loads the whole document into memory as a tree of nodes. We can then walk the tree,
 * search it, and also MODIFY it (here a new student is added and a grade attribute is set),
 * then write the changed tree back out as XML.
 *
 * Compile: javac DOMParserDemo.java
 * Run:     java DOMParserDemo            (or: java DOMParserDemo other.xml)
 */
import java.io.File;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMParserDemo {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "students.xml";
        try {
            // 1. Parse the file into a DOM tree
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            doc.getDocumentElement().normalize();

            System.out.println("===== DOM Parser: " + fileName + " =====");
            System.out.println("Root element : " + doc.getDocumentElement().getNodeName());

            // 2. Read all <student> nodes from the tree
            NodeList list = doc.getElementsByTagName("student");
            System.out.println("Students found: " + list.getLength());
            System.out.println();
            System.out.printf("%-7s %-16s %-8s %-6s %-10s%n", "RollNo", "Name", "Course", "Marks", "City");
            System.out.println("--------------------------------------------------");

            Element topper = null;
            int topMarks = -1;
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element s = (Element) node;
                    int marks = Integer.parseInt(getText(s, "marks"));
                    System.out.printf("%-7s %-16s %-8s %-6d %-10s%n",
                            s.getAttribute("rollno"), getText(s, "name"), getText(s, "course"),
                            marks, getText(s, "city"));
                    if (marks > topMarks) {
                        topMarks = marks;
                        topper = s;
                    }
                    // Modify the tree: add a grade attribute to every student
                    s.setAttribute("grade", marks >= 75 ? "A" : marks >= 60 ? "B" : "C");
                }
            }
            if (topper != null) {
                System.out.println("\nTopper: " + getText(topper, "name") + " with " + topMarks + " marks");
            }

            // 3. Search the tree: list only MCA students
            System.out.print("MCA students: ");
            StringBuilder mca = new StringBuilder();
            for (int i = 0; i < list.getLength(); i++) {
                Element s = (Element) list.item(i);
                if (getText(s, "course").equals("MCA")) {
                    if (mca.length() > 0) mca.append(", ");
                    mca.append(getText(s, "name"));
                }
            }
            System.out.println(mca);

            // 4. Create a new node and append it to the root
            Element newStudent = doc.createElement("student");
            newStudent.setAttribute("rollno", "106");
            newStudent.setAttribute("grade", "A");
            addChild(doc, newStudent, "name", "Kavya Nair");
            addChild(doc, newStudent, "course", "MCA");
            addChild(doc, newStudent, "marks", "88");
            addChild(doc, newStudent, "city", "Kochi");
            doc.getDocumentElement().appendChild(newStudent);
            System.out.println("\nAdded new student 106 (Kavya Nair). Total now: "
                    + doc.getElementsByTagName("student").getLength());

            // 5. Print the modified DOM tree as XML (not saved, to keep the input file unchanged)
            System.out.println("\nModified XML (last student shown):");
            System.out.println(toXml(newStudent));
        } catch (Exception e) {
            System.out.println("Error while parsing: " + e.getMessage());
        }
    }

    /** Text content of the first child element with the given tag. */
    private static String getText(Element parent, String tag) {
        NodeList nl = parent.getElementsByTagName(tag);
        return nl.getLength() > 0 ? nl.item(0).getTextContent().trim() : "";
    }

    private static void addChild(Document doc, Element parent, String tag, String value) {
        Element e = doc.createElement(tag);
        e.appendChild(doc.createTextNode(value));
        parent.appendChild(e);
    }

    /** Serialise a node to an indented XML string using the Transformer API. */
    private static String toXml(Node node) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StringWriter sw = new StringWriter();
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString().trim();
    }
}
