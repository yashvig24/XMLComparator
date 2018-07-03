# XMLComparator
## My Second Open Source Project

>src/model/XMLTag : a class which describes and creates an instance of an XMLTag which can be an opening tag, closing tag or a self-closing tag. 

>src/model/XMLNode : A node for the XMLTree which consists of a tag which it holds and a list of children nodes. 

>scr/model/XMLTree : A abstract class for an XMLTree which is a N-ary tree representing a XML file. 

>scr/model/XMLTreeSmall : Uses recursion for the implementation of all required methods. Extends XMLTree

>scr/model/XMLTreeLarge : Uses self-implemented recursion using a stack for very large files. Cannot use XMLTreeSmall for large files as too many recursion calls cause a StackOverflowError. Extends XMLTree. 

>src/controller/CompareXML : uses a comand line interface which takes two filepaths as arguments and explains to the user how the two files differ. 

> src/view/CompareMain : produces a GUI which is drag and drop enabled. This means that you can drag and drop two files and hit the compare button to see the differences between the two files. 