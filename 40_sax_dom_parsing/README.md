# Q40 – XML document processing using SAX and DOM

**Question 40:** XML document processing using SAX and DOM.

## Files
| File | Purpose |
|------|---------|
| `students.xml` | Input XML: 5 `<student rollno="...">` records with `name`, `course`, `marks` and `city`. |
| `SAXParserDemo.java` | **SAX** (event based). Uses `SAXParserFactory` → `SAXParser.parse(file, handler)`, where the handler extends `DefaultHandler` and overrides `startDocument`, `startElement` (reads the `rollno` attribute), `characters`, `endElement` and `endDocument`. It prints each student as the events arrive, then the count and the average marks. |
| `DOMParserDemo.java` | **DOM** (tree based). Uses `DocumentBuilderFactory` → `DocumentBuilder.parse()` to load the whole tree, then `getElementsByTagName`, `getAttribute` and `getTextContent`. It prints a table, finds the topper, lists the MCA students, **modifies** the tree (`setAttribute("grade")`, `createElement` + `appendChild` for a new student) and prints the new node with `Transformer`. The input file is not changed. |

Only the JDK's built-in `javax.xml.parsers`, `org.xml.sax` and `org.w3c.dom` packages are used, so no extra jars are needed. The code works with Java 8 and later.

## How to run
```bash
cd 40_sax_dom_parsing
javac SAXParserDemo.java DOMParserDemo.java
java SAXParserDemo          # optional argument: path of another XML file
java DOMParserDemo
```
(To keep `.class` files out of the folder, use `javac -d /tmp/out *.java` and then `java -cp /tmp/out SAXParserDemo`. Run it from this folder so `students.xml` is found.)

## SAX vs DOM
| SAX | DOM |
|-----|-----|
| Event driven: reads the file in one pass and calls handler methods | Builds the complete tree in memory |
| Very little memory, fast, good for large files | Uses more memory; best for small and medium files |
| Read-only and forward-only | Can move around, search and modify the tree |
| You must keep track of the state yourself | Random access via `getElementsByTagName` and similar methods |

## Sample output (actual run)
`java SAXParserDemo`
```
===== SAX Parser (event based) =====
[event] startDocument

[event] startElement <student> rollno=101
  Name   : Ravi Kumar
  Course : MCA
  Marks  : 86  (First Class)
  City   : Hyderabad
[event] endElement </student>

[event] startElement <student> rollno=102
  Name   : Priya Sharma
  Course : MBA
  Marks  : 74  (First Class)
  City   : Delhi
[event] endElement </student>

... (103, 104, 105 similar) ...

[event] endDocument
Total students : 5
Average marks  : 75.20
```

`java DOMParserDemo`
```
===== DOM Parser: students.xml =====
Root element : students
Students found: 5

RollNo  Name             Course   Marks  City
--------------------------------------------------
101     Ravi Kumar       MCA      86     Hyderabad
102     Priya Sharma     MBA      74     Delhi
103     Arjun Reddy      MCA      91     Bengaluru
104     Sneha Patel      M.Tech   58     Ahmedabad
105     Mohammed Irfan   MCA      67     Chennai

Topper: Arjun Reddy with 91 marks
MCA students: Ravi Kumar, Arjun Reddy, Mohammed Irfan

Added new student 106 (Kavya Nair). Total now: 6

Modified XML (last student shown):
<student grade="A" rollno="106">
  <name>Kavya Nair</name>
  <course>MCA</course>
  <marks>88</marks>
  <city>Kochi</city>
</student>
```
