# Smart XML analyzer
XMl analyzer that helps to find user-selected element in changed XML file

## How to use
Use this command to run the app
```$shell
java -jar smart_xml_analyzer-1.0-SNAPSHOT.jar <input_origin_file_path> <input_other_sample_file_path> <element_id>
```
Compiled jar is located in this repository as smart_xml_analyzer-1.0-SNAPSHOT.jar
## Sample usage
```$shell
java -jar smart_xml_analyzer-1.0-SNAPSHOT.jar D:\Projects\smart_xml_analyzer\sample\sample-0-origin.html D:\Projects\smart_xml_analyzer\sample\sample-3-the-escape.html make-everything-ok-button
html[0]  > body[1]  > div[0]  > div[1]  > div[2]  > div[0]  > div[0]  > div[2]  > a[0]

Decisions:
Matched attribute "onclick" with value "javascript:window.okDone(); return false;"
Matched attribute "rel" with value "next"
Matched attribute "href" with value "#ok"
Matched attribute "class" with value "btn btn-success"
```