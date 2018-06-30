# XMLComparator
## My Second Open Source Project

>src/xml/XMLTag : a class which describes and creates an instance of an XMLTag which can be an opening tag, closing tag or a self-closing tag. 

src/xml/XMLNode : A node for the XMLTree which consists of a tag which it holds and a list of children nodes. 

scr/xml/XMLTree : A abstract class for an XMLTree which is a N-ary tree representing a XML file. 

scr/xml/XMLTreeSmall : Uses recursion for the implementation of all required methods. Extends XMLTree

scr/xml/XMLTreeLarge : Uses self-implemented recursion using a stack for very large files. Cannot use XMLTreeSmall for large 
files as too many recursion calls cause a StackOverflowError. Extends XMLTree. 

src/test/CompareXML : uses a comand line interface which takes two filepaths as arguments and explains to the user how the two files differ. 
