/**
 * This is the flyweight or empty leaf nodes class
 * 
 * @author connor and wesley
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class FlyweightNode extends BaseNode {
    /**
     * constructor - this node will contain no info, it's empty
     */
    public FlyweightNode() {
        // Empty because it is a placeholder
    }


    /**
     * @return true if the node is a flyweight node
     */
    public boolean isFly() {
        return true;
    }


    /**
     * Prints the structure of the DNAtree, this print is used for flyweight
     * nodes
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing
     */
    public void printNew(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println("E");
    }


    /**
     * Prints the structure of the DNAtree with sequence lengths, this print is
     * used for flyweight nodes
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing
     */
    public void printNewLength(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println("E");
    }


    /**
     * Prints the structure of the DNAtree with sequence stats, this print is
     * used for flyweight nodes
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing
     */
    public void printNewStats(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println("E");
    }


    /**
     * used to count the total number of nodes in the tree
     * 
     * @param x
     *            does nothing
     * @return returns 1 for our adder
     */
    public int printCount(int x) {
        return 1;
    }

}
