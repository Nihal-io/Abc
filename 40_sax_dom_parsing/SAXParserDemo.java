/*
 * Question 40: XML document processing using SAX and DOM.
 *
 * SAXParserDemo.java - reads students.xml with SAX (Simple API for XML).
 * SAX is event based: the parser reads the file from top to bottom and calls
 * startDocument / startElement / characters / endElement / endDocument on our
 * DefaultHandler. Nothing is kept in memory unless we store it ourselves.
 *
 * Compile: javac SAXParserDemo.java
 * Run:     java SAXParserDemo            (or: java SAXParserDemo other.xml)
 */
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParserDemo {

    public static void main(String[] args) {
        String fileName = args.length > 0 ? args[0] : "students.xml";
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            StudentHandler handler = new StudentHandler();
            parser.parse(new File(fileName), handler);
        } catch (Exception e) {
            System.out.println("Error while parsing: " + e.getMessage());
        }
    }

    /** Event handler: receives a callback for every piece of the XML document. */
    static class StudentHandler extends DefaultHandler {
        private StringBuilder text = new StringBuilder();   // text of the current element
        private int count = 0;
        private int totalMarks = 0;
        private String name, course, marks, city;

        @Override
        public void startDocument() throws SAXException {
            System.out.println("===== SAX Parser (event based) =====");
            System.out.println("[event] startDocument");
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs)
                throws SAXException {
            text.setLength(0);
            if (qName.equals("student")) {
                count++;
                System.out.println("\n[event] startElement <student> rollno=" + attrs.getValue("rollno"));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            text.append(ch, start, length);                // may be called several times
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String value = text.toString().trim();
            switch (qName) {
                case "name":   name = value;   break;
                case "course": course = value; break;
                case "marks":  marks = value;  break;
                case "city":   city = value;   break;
                case "student":
                    int m = Integer.parseInt(marks);
                    totalMarks += m;
                    System.out.println("  Name   : " + name);
                    System.out.println("  Course : " + course);
                    System.out.println("  Marks  : " + marks + "  (" + (m >= 60 ? "First Class" : "Second Class") + ")");
                    System.out.println("  City   : " + city);
                    System.out.println("[event] endElement </student>");
                    break;
                default:
                    break;
            }
            text.setLength(0);
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("\n[event] endDocument");
            System.out.println("Total students : " + count);
            if (count > 0) {
                System.out.printf("Average marks  : %.2f%n", (double) totalMarks / count);
            }
        }
    }
}
