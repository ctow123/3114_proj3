/**
 * This extends the generic node class, and provides different implementation of
 * methods.
 * this class represents a leaf node in the DNAtree or one that contains a
 * sequence and
 * has no children
 * 
 * @author connor and wesley
 * @version 1.0 - using git so the versions are stored there
 */
public class LeafNode extends BaseNode {
    private char[] sequence;


    /**
     * this creates a leaf node with the sequence name
     * 
     * @param seqname
     *            the string version of the sequence name
     */
    public LeafNode(String seqname) {
        sequence = seqname.toCharArray();

    }


    /**
     * 
     * @return true if the node is a leaf
     */
    public boolean isLeaf() {
        return true;
    }


    /**
     * @return The string value of the sequence
     */
    public String getSeqname() {
        return String.valueOf(sequence);
    }


    /**
     * Prints the structure of the DNAtree, this print is used for leaf nodes
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing
     */
    public void printNew(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println(String.valueOf(sequence));
    }


    /**
     * Prints the structure of the DNAtree with sequence lengths, this print is
     * used for leaf nodes
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing
     */
    public void printNewLength(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        System.out.println(String.valueOf(sequence) + " " + sequence.length);
    }


    /**
     * Prints the structure of the DNAtree with sequence stats, this print is
     * used for leaf nodes
     * 
     * @param x
     *            what level of the DNAtree, the method is printing,
     *            this controls spacing
     */
    public void printNewStats(int x) {
        for (int count = 0; count < x * 2; count++) {
            System.out.print(" ");
        }
        double aC = 0;
        double cC = 0;
        double gC = 0;
        double tC = 0;
        double total = sequence.length;
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i] == 'A') {
                aC++;
            }
            else if (sequence[i] == 'C') {
                cC++;
            }
            else if (sequence[i] == 'G') {
                gC++;
            }
            else {
                tC++;
            }

        }
        String aS = String.format("%.2f", (float)aC / total * 100);
        String cS = String.format("%.2f", (float)cC / total * 100);
        String gS = String.format("%.2f", (float)gC / total * 100);
        String tS = String.format("%.2f", (float)tC / total * 100);

        System.out.println(String.valueOf(sequence) + " A:" + aS + " C:" + cS
            + " G:" + gS + " T:" + tS);
    }


    /**
     * used to count the total number of nodes in the tree
     * 
     * @param x
     *            does nothing
     * @return 1, because a leafnode is 1 node
     */
    public int printCount(int x) {
        return 1;
    }


    /**
     * used to print the sequences, used in the search method of DNAtree
     * 
     * @param x
     *            does nothing
     * 
     */
    public void printSearch(int x) {
        System.out.println("sequence: " + String.valueOf(sequence));
    }


    /**
     * used to count the total number of leaf nodes
     * 
     * @return 1, because a leafnode is 1 node
     */
    public int countLeaf() {
        return 1;
    }

}
