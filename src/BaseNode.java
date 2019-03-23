/**
 * this is the basenode which flyweight,leaf, and internal have subclasses of
 * the subclasses are used a lot. this contains default implementations of the
 * subclass methods
 * 
 * @author wesleyn and connor
 * @version 1.0 - using git so the versions are stored there
 */
public class BaseNode {

    /**
     * Default constructor
     */
    public BaseNode() {
        // basenode is never actually used (object created), only child classes
    }


    /**
     * 
     * @param a
     *            char of the child node you want
     * @return the child node based on the char passed in
     * 
     */
    public BaseNode get(char a) {
        return null;

    }


    /**
     * 
     * @param i
     *            number of the child node you want
     * @return the child node based on the number passed in
     * 
     */
    public BaseNode getbyNum(int i) {
        return null;

    }


    /**
     * changes the node to which one of the children point
     * 
     * @param nodetoset
     *            selects which node to change or set
     * @param setter
     *            node passed in that the old node is changed to, can pass in
     *            flyweight,leaf, or internal nodes because of inheritance
     */
    public void set(char nodetoset, BaseNode setter) {
        // only subclasses need to implement this method as basenode isn't used
    }


    /**
     * 
     * @return false because nodes are not default flyweight nodes
     */
    public boolean isFly() {
        return false;
    }


    /**
     * 
     * @return false because nodes are not default internal nodes
     */
    public boolean isInternal() {
        return false;
    }


    /**
     * 
     * @return false because nodes are not default leaf nodes
     */
    public boolean isLeaf() {
        return false;
    }


    /**
     * Prints the structure of the DNAtree.
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing, each level down you go in the calls the
     *            x or levels will increase
     */
    public void printNew(int x) {
        // only subclasses need to implement this method as basenode isn't used
    }


    /**
     * Prints the structure of the DNAtree with sequence lengths.
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing, each level down you go in the calls the
     *            x or levels will increase
     */
    public void printNewLength(int x) {
        // only subclasses need to implement this method as basenode isn't used
    }


    /**
     * Prints the structure of the DNAtree with sequence stats.
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing, each level down you go in the calls the
     *            x or levels will increase
     */
    public void printNewStats(int x) {
        // only subclasses need to implement this method as basenode isn't used
    }


    /**
     * 
     * @param x
     *            does nothing
     * @return the number of total nodes in the tree
     */
    public int printCount(int x) {
        return 0;

    }


    /**
     * 
     * @return the number of leaf nodes in the tree
     */
    public int countLeaf() {
        return 0;
    }


    /**
     * used to print the sequences, used in the search method of DNAtree.
     * 
     * @param x
     *            does nothing
     */
    public void printSearch(int x) {
        // only subclasses need to implement this method as basenode isn't used
    }


    /**
     * default returns null
     * 
     * @return the string of the sequence of a leaf node
     */
    public String getSeqname() {
        return null;
    }

}
