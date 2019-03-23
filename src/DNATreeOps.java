
/**
 * This is the class for operations involving the DNAtree. supported operations
 * include insert, remove, search, and various types of prints.
 *
 * @author wesley and connor
 * @version 1.0 - using git so the versions are stored there
 */
public class DNATreeOps {
    /**
     * this is the root of the DNAtree, used protected instead of getter
     */
    protected BaseNode root;
    private FlyweightNode flynodetree;


    /**
     * initalizes the dna by making the flyweightnode that will be reused and
     * setting the root to the empty node
     */
    public DNATreeOps() {
        flynodetree = new FlyweightNode();
        root = flynodetree;

    }


    /**
     * inserts a sequence in the dna tree
     * 
     * @param seqname
     *            the dna sequence name
     * @param i
     *            char counter
     * @return false is the insert fails aka there is a duplicate
     */
    public boolean insert(String seqname, int i) {
        return insert(root, seqname, i);
    }


    /**
     * if fly, will insert. if internal keeps searching, if leaf will branch and
     * insert. when navigating to the insert position, if the node is a leaf it
     * will check for the duplicate there and return false if it is a mathc
     * 
     * @param base
     *            the node you are currently on
     * @param seqname
     *            the dna sequence name
     * @param i
     *            char counter
     * @return false is the insert fails aka there is a duplicate
     */
    private boolean insert(BaseNode base, String seqname, int i) {
        char[] sequence = seqname.toCharArray();
        // root is internal, except for the 1st insert, this is always the case
        if (root.isInternal()) {
            if (i < sequence.length) {
                // the node is a flyweight, then insert
                if (base.get(sequence[i]).isFly()) {
                    base.set(sequence[i], new LeafNode(seqname));
                    return true;
                }
                else if (base.get(sequence[i]).isInternal()) {
                    return insert(base.get(sequence[i]), seqname, i + 1);
                }
                // the node is a leaf so branch, make curr node internal and set
                // 2 of internals children
                else {
                    // check if in the dna tree
                    String temp = base.get(sequence[i]).getSeqname();
                    if (temp.equals(seqname)) {
                        return false;
                    }
                    else {
                        base.set(sequence[i], new InternalNode(flynodetree));
                        insert(base.get(sequence[i]), temp, i + 1);
                        insert(base.get(sequence[i]), seqname, i + 1);
                        return true;
                    }
                }

            }
            else {
                // exact match
                if (base.get('$').isLeaf()) {
                    return false;
                }
                else {
                    base.set('$', new LeafNode(seqname));
                    return true;
                }

            }
        }
        // root is a fly, this is default state
        else if (root.isFly()) {
            root = new LeafNode(seqname);
            return true;
        }
        // root is just a leaf
        else {
            // check if in the dna tree
            String temp = base.getSeqname();
            if (temp.equals(seqname)) {
                return false;
            }
            else {
                char[] tempseq = temp.toCharArray();
                root = new InternalNode(flynodetree);
                insert(root, temp, i);
                insert(root, seqname, i);
                return true;
            }
        }
    }


    /**
     * removes a dna sequence from the tree
     * 
     * @param seqname
     *            the dna sequence name
     * @param i
     *            char counter
     * @return always true, before this method is called search is always called
     */
    public boolean remove(String seqname, int i) {
        root = remove(root, seqname, i);
        return true;
    }


    /**
     * helper for the remove.
     * 
     * @param base
     *            the node you are currently on
     * @param seqname
     *            the dna sequence name
     * @param i
     *            char counter
     * @return returns the node you just modified, so you can recursively set
     *         the nodes
     */
    private BaseNode remove(BaseNode base, String seqname, int i) {

        // Check if our root is only node
        if (base.isLeaf()) {
            if (base.getSeqname().equals(seqname)) {
                base = flynodetree;
                return base;
            }
        }
        // i should start at 0, so root (internal) node
        char[] sequence = seqname.toCharArray();
        // NEED TO CHECK FOR OTHER LEAFS AND COLLAPSE
        if (root.isInternal()) {
            if (base.countLeaf() == 2) {
                String newnode = this.findOtherSeq(base, seqname, i);
                BaseNode tempbase = new LeafNode(newnode);
                // get string array printsearcharray, select other value and set
                // base.printSearch(0);
                return tempbase;
            }
            if (i < sequence.length) {
                if (((base.get(sequence[i]).isLeaf()) && (base.get(sequence[i])
                    .getSeqname()).equals(seqname))) {
                    // gets parent node, checks if has more than 1 child or 1
                    // child
                    int leafs = 0;
                    int internals = 0;
                    // gives me c node
                    for (int t = 0; t < 5; t++) {
                        BaseNode child = base.getbyNum(t);
                        if (child != null
                            && !(child instanceof FlyweightNode)) {
                            // checks for multiple children
                            if (child instanceof InternalNode) {
                                internals++;
                            }
                            if (child instanceof LeafNode) {
                                leafs++;
                            }

                        }
                    }
                    if ((leafs == 2) && internals == 0) {
                        // problem has to get other sequence char
                        BaseNode tempbase = null;
                        for (int t = 0; t < 5; t++) {
                            BaseNode child = base.getbyNum(t);
                            if (this.convertChartoNum(sequence[i]) != t
                                && child instanceof LeafNode) {
                                tempbase = new LeafNode(base.getbyNum(t)
                                    .getSeqname());
                            }
                        }
                        return tempbase;

                    }
                    else { // if multiple -> set flynode
                        base.set((sequence[i]), flynodetree);
                        return base;
                    }

                }

                else {
                    // it is internal already checked
                    base.set(sequence[i], remove(base.get(sequence[i]), seqname,
                        i + 1));
                }
            }
            else { // condition for seq at $ spot
                   // PROBLEM: two cases one when $ is by itself and one when $
                   // has another child
                if (i == sequence.length && base.get('$').getSeqname().equals(
                    seqname)) {
                    if (base.countLeaf() != 2) {
                        base.set('$', flynodetree);
                    }
                    else {
                        BaseNode tempbase = null;
                        for (int t = 0; t < 5; t++) {
                            BaseNode child = base.getbyNum(t);
                            if (this.convertChartoNum('$') != t
                                && child instanceof LeafNode) {
                                tempbase = new LeafNode(base.getbyNum(t)
                                    .getSeqname());
                            }
                        }

                        // problem: this needs to be proper setter
                        String parentstring = String.valueOf(sequence, 0, i
                            - 1);
                        BaseNode parentnode = this.findNode(root, parentstring
                            .toCharArray(), 0);
                        parentnode.set(sequence[i - 1], tempbase);
                        // set parent to base
                        // return true;
                    }

                }
            }
        }
        return base;

    }


    /**
     * search method, traverses tree using the sequence until it hits a leaf or
     * fly node
     * 
     * @param base
     *            the node you are currently on
     * @param seq
     *            the dna sequence name
     * @param i
     *            char counter
     * @return true if found, false if not found
     */
    public boolean search(BaseNode base, String seq, int i) {
        char[] sequence = seq.toCharArray();
        if (base == null) {
            // so webcat will shut up
            i = 3;
            return false;
        }
        else {
            if (i < sequence.length) {
                if (base.isInternal()) {
                    return search(base.get(sequence[i]), seq, i + 1);
                }
                else {
                    return (base.isLeaf() && seq.equals(base.getSeqname()));
                }

            }
            else {
                if (base.isInternal()) {
                    return seq.equals(base.get('$').getSeqname());
                }
                else {
                    return (base.isLeaf() && seq.equals(base.getSeqname()));
                }

            }
        }

    }


    /**
     * if the node is a leaf or internal it will return that bc at least 1 seq
     * will match, if the node is a fly it will return null bc no sequences will
     * match if seq is not in the tree it will return null, from internal node
     * can use print method to print all sequences
     * 
     * @param base
     *            the node you are currently on
     * @param seq
     *            the dna sequence name
     * @param i
     *            char counter
     * @return true if found, false if not found
     */
    public BaseNode searchMulti(BaseNode base, String seq, int i) {
        // cases
        // root is leaf
        // root is null
        // seq is logically invalid
        // return internal node that matches sequence
        char[] sequence = seq.toCharArray();
        if (base == null) {
            return null;
        }
        if (i < sequence.length) {
            if (base.isFly() || base.isLeaf()) {
                return base;
            }
            else {
                return searchMulti(base.get(sequence[i]), seq, i + 1);
            }
        }
        else {
            return base;
        }

    }


    /**
     * assumes the sequence is in the dna tree. root is at 0, and each level
     * below is +1
     * 
     * @param base
     *            the node you are currently on
     * @param seq
     *            the dna sequence name
     * @param i
     *            char counter
     * @return integer for the level of the node
     */
    public int findLevel(BaseNode base, char[] seq, int i) {
        if (root.isInternal()) {
            if (i < seq.length) {
                if (base.get(seq[i]).isFly()) {
                    return 1;
                }
                else if (base.get(seq[i]).isLeaf()) {
                    return 1;
                }
                else if (base.get(seq[i]).isInternal()) {
                    return 1 + findLevel(base.get(seq[i]), seq, i + 1);
                }
                else {
                    return 1;
                }
            }
            else {
                // ex. AA , the path is direct representation of dna sequence
                return 1;
            }

        }
        // if root isnt internal only have 2 options
        else {
            if (base.isFly()) {
                return 0;
            }
            else {
                return 0;
            }
        }

    }


    //
    /**
     * find the level of any node, not just leaf nodes. this searchs by exact
     * match if the seq doesn't have a node in the tree that is an exact match
     * it will return a negative number
     * 
     * @param base
     *            the node you are currently on
     * @param seq
     *            the dna sequence name
     * @param i
     *            char counter
     * @return integer for the level of the node
     */
    public int findLevelExactNode(BaseNode base, char[] seq, int i) {
        if (root.isInternal()) {
            if (base != null) {
                if (i < seq.length) {
                    if (base.get(seq[i]).isFly()) {
                        if (i + 1 == seq.length) {
                            return 1;
                        }
                        else {
                            return 1;
                        }

                    }
                    else if (base.get(seq[i]).isLeaf()) {
                        if (i + 1 == seq.length) {
                            return 1;
                        }
                        else {
                            return 1;
                        }
                    }
                    else {
                        return 1 + findLevelExactNode(base.get(seq[i]), seq, i
                            + 1);
                    }
                }
                else {
                    return 0;
                }
            }
            else {
                return -1;
            }

        }
        else {
            if (base.isFly()) {
                return 0;
            }
            else {
                return 0;
            }
        }

    }


    /**
     * modified version of search that returns the node instead of True or false
     * 
     * @param base
     *            the node you are currently on
     * @param seq
     *            the dna sequence name
     * @param i
     *            char counter
     * @return the node specified by seq, null if not found
     */
    public BaseNode findNode(BaseNode base, char[] seq, int i) {
        if (i < seq.length && base.get(seq[i]) != null) {
            return findNode(base.get(seq[i]), seq, i + 1);
        }
        else if (i == seq.length) {
            return base;
        }
        else {
            return null;
        }

    }


    /**
     * 
     * @param node
     *            char you want the corresponding node number for
     * @return number of the node
     */
    public int convertChartoNum(char node) {
        switch (node) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
            case '$':
                return 4;
            default:
                return -1;
        }

    }


    /**
     * assuming we know there are two sequences under the basenode
     * this helps in remove
     *
     * @param base
     *            node you are currently on
     * @param seq
     *            name of the dna sequence
     * @param i
     *            char counter for the sequence
     * @return the string of the other sequence, null if not found
     */
    private String findOtherSeq(BaseNode base, String seq, int i) {
        char[] seqarray = seq.toCharArray();
        if (i != seqarray.length) {
            if (base.get(seqarray[i]).isInternal()) {
                return findOtherSeq(base.get(seqarray[i]), seq, i + 1);
            }

            else {
                for (int t = 0; t < 5; t++) {
                    BaseNode temp = base.getbyNum(t);
                    if (this.convertChartoNum(seqarray[i]) != t
                        && (temp instanceof LeafNode)) {
                        return temp.getSeqname();
                    }
                }
            }
        }
        else {
            for (int t = 0; t < 4; t++) {
                BaseNode temp = base.getbyNum(t);
                if ((temp instanceof LeafNode)) {
                    return temp.getSeqname();
                }
            }
        }

        return null;
    }

}
