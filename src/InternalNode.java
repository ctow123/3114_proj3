/**
 * This extends the generic node class, and provides different implementation of
 * methods.
 * this class represents a internal node in the DNAtree that has 5 children
 * 
 * @author connor and wesley
 * @version 1.0 - using git so the versions are stored there
 */
public class InternalNode extends BaseNode {

    private BaseNode a;
    private BaseNode c;
    private BaseNode g;
    private BaseNode t;
    private BaseNode money;


    /**
     * the constructor to initialize the children
     * 
     * @param node
     *            the node you want all the children to be set to, typically
     *            node will take a value of a flyweight node
     */
    public InternalNode(BaseNode node) {
        a = node;
        c = node;
        g = node;
        t = node;
        money = node;

    }


    /**
     * Prints the structure of the DNAtree, this print is used for internal
     * nodes. internal nodes can have children, so this is the recursive call
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing, each level down you go in the calls the
     *            x or levels will increase
     */
    public void printNew(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println("I");
        a.printNew(x + 1);
        c.printNew(x + 1);
        g.printNew(x + 1);
        t.printNew(x + 1);
        money.printNew(x + 1);
    }


    /**
     * Prints the structure of the DNAtree with sequence lengths, this print is
     * used for internal
     * nodes. internal nodes can have children, so this is the recursive call
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing, each level down you go in the calls the
     *            x or levels will increase
     */
    public void printNewLength(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println("I");
        a.printNewLength(x + 1);
        c.printNewLength(x + 1);
        g.printNewLength(x + 1);
        t.printNewLength(x + 1);
        money.printNewLength(x + 1);
    }


    /**
     * Prints the structure of the DNAtree with sequence stats, this print is
     * used for internal
     * nodes. internal nodes can have children, so this is the recursive call
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing, each level down you go in the calls the
     *            x or levels will increase
     */
    public void printNewStats(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println("I");
        a.printNewStats(x + 1);
        c.printNewStats(x + 1);
        g.printNewStats(x + 1);
        t.printNewStats(x + 1);
        money.printNewStats(x + 1);
    }


    /**
     * used to print the sequences, used in the search method of DNAtree. this
     * is where the recursive call happens because internal nodes can have
     * children
     * 
     * @param x
     *            does nothing
     */
    public void printSearch(int x) {
        a.printSearch(x + 1);
        c.printSearch(x + 1);
        g.printSearch(x + 1);
        t.printSearch(x + 1);
        money.printSearch(x + 1);
    }


    /**
     * used to count the total number of nodes in the tree. this
     * is where the recursive call happens because internal nodes can have
     * children
     * 
     * @param x
     *            does nothing
     * @return the total number of nodes under the internal node
     */
    public int printCount(int x) {
        return 1 + a.printCount(x + 1) + c.printCount(x + 1) + g.printCount(x
            + 1) + t.printCount(x + 1) + money.printCount(x + 1);
    }


    /**
     * used to count the total number of leaf nodes in the tree. this
     * is where the recursive call happens because internal nodes can have
     * children
     * 
     * @return the total number of leafs under the internal node
     */
    public int countLeaf() {
        return a.countLeaf() + c.countLeaf() + g.countLeaf() + t.countLeaf()
            + money.countLeaf();
    }


    /**
     * @return true is the node is an internal node
     */
    public boolean isInternal() {
        return true;
    }


    /**
     * @param node
     *            the char of the node you want
     * @return the child node based on the char passed in
     */
    public BaseNode get(char node) {
        switch (node) {
            case 'A':
                return a;
            case 'C':
                return c;
            case 'G':
                return g;
            case 'T':
                return t;
            case '$':
                return money;
            default:
                return null;
        }

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
        switch (nodetoset) {
            case 'A':
                a = setter;
                break;
            case 'C':
                c = setter;
                break;
            case 'G':
                g = setter;
                break;
            case 'T':
                t = setter;
                break;
            case '$':
                money = setter;
                break;
            default:
                // nothing
        }
    }


    /**
     * @param i
     *            the number of the node you want
     * @return the child node based on the number passed in
     */
    public BaseNode getbyNum(int i) {
        switch (i) {
            case 0:
                return a;
            case 1:
                return c;
            case 2:
                return g;
            case 3:
                return t;
            case 4:
                return money;
            default:
                return null;
        }

    }
}
