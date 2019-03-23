
import java.io.*;
import java.util.*;

// On my honor: //
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified. //
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course. //
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * In this project, we turn to the problem of searching for matching sequences
 * in a sequence database. A key element in many bioinformatics problems is the
 * biological sequence. A biological sequence is just a list of characters
 * chosen from some alphabet. Two of the common biological sequences are DNA
 * (composed of the four characters A, C, G, and T). In this project, you will
 * be storing and searching for DNA sequences, defined to be strings on the
 * alphabet A, C, G,and T.
 * 
 * we use a tree structure to store sequences in a way that allows for
 * efficient search. Not only can we determine whether a specified sequence is
 * in the database, but we can also find any sequence in the database that
 * matches a search prefix
 * 
 * 
 * This is the main class where we run our command
 * line script and test the different functions called by a script.
 * 
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor and wesley
 *
 */
public class DNAtree {
    private static DNATreeOps dna;


    /**
     * Parses the text file and executes the commands by the given command
     * script to
     * the output text file. will come later.
     * 
     * @param args
     *            - the file to be passed in
     * @throws FileNotFoundException
     *             - exception if there is no files
     */
    public static void main(String[] args) throws FileNotFoundException {
        // initialize tree structure
        dna = new DNATreeOps();
        // Reads in command line argument
        Scanner file = new Scanner(new File(args[0])); // args[0]
        String command;

        while (file.hasNextLine()) {
            String string = file.nextLine();
            // eliminating tabs to empty string
            //string = string.replaceAll("\t", "");
            // splitting array by every whitespace
            String[] linearray = string.trim().split("\\s+");
            // reads the command - calling function

            command = linearray[0];
            // removing whitespace
            command = command.replaceAll("\\s+", "");

            if (command.equals("insert")) {
                if (linearray.length >= 2) {
                    String name = linearray[1];
                    DNAtree.insert(name);
                }

            }
            else if (command.equals("remove")) {
                if (linearray.length >= 2) {
                    String name = linearray[1];
                    // System.out.println(name);
                    DNAtree.remove(name);
                }

            }
            // search single sequence or search multiple sequences
            else if (command.equals("search")) {
                if (linearray.length == 2) {
                    String name = linearray[1];
                    char[] sequence = name.toCharArray();

                    if ((sequence[sequence.length - 1]) == '$') {
                        name = String.valueOf(sequence, 0, sequence.length - 1);
                        DNAtree.search(name);
                    }
                    else {
                        DNAtree.searchMulti(name);
                    }
                }
            }
            else if (command.equals("print")) {
                if (linearray.length >= 2) {
                    String commandspecify = linearray[1];
                    // System.out.println(commandspecify);
                    if (commandspecify.equals("lengths")) {
                        DNAtree.printNewLength();
                    }
                    else if (commandspecify.equals("stats")) {
                        DNAtree.printNewStats();
                    }

                }
                else {
                    DNAtree.printNew();
                    // call regular print method
                }
            }
        }
    }


    /**
     * inserts a sequence into the DNA tree, doesn't insert duplicates
     * 
     * @param seqname
     *            name of the sequence to insert
     */
    public static void insert(String seqname) {
        // search if duplicate don't insert
// if (!dna.search(dna.root, seqname, 0)) {
//
// System.out.println("sequence " + seqname + " inserted at level "
// + dna.findLevel(dna.root, seqname.toCharArray(), 0));
// }
// else {
// System.out.println("sequence " + seqname + " already exists");
//
// }
        if (dna.insert(seqname, 0)) {
            System.out.println("sequence " + seqname + " inserted at level "
                + dna.findLevel(dna.root, seqname.toCharArray(), 0));
        }
        else {
            System.out.println("sequence " + seqname + " already exists");
        }
    }


    /**
     * removes a sequence from dnatree or prints does not exist, if its not in
     * it
     * 
     * @param name
     *            the sequence you are removing
     */
    public static void remove(String name) {
        // search if the sequence even exists in the dna tree
        if (dna.search(dna.root, name, 0)) {
            dna.remove(name, 0);
            System.out.println("sequence " + name + " removed");
        }
        else {
            System.out.println("sequence " + name + " does not exist");

        }
    }


    /**
     * search method for specific match
     * * @param name
     * the sequence you are searching for
     */
    public static void search(String name) {
        if (dna.search(dna.root, name, 0)) {
            System.out.println("# of nodes visited: " + (dna.findLevel(dna.root,
                name.toCharArray(), 0) + 1));
            System.out.println("sequence: " + name);
        }
        else {
            System.out.println("# of nodes visited: " + (dna.findLevel(dna.root,
                name.toCharArray(), 0) + 1));
            System.out.println("no sequence found");
        }
    }


    /**
     * search method for all matches
     * * @param name
     * the sequence you searching for
     */
    public static void searchMulti(String name) {
        BaseNode multinode = dna.searchMulti(dna.root, name, 0);
        int test = 0;
        int nodesvisitedbase = dna.findLevelExactNode(dna.root, name
            .toCharArray(), 0);
        if (multinode != null && multinode.isInternal()) {
            test = multinode.printCount(0);
            System.out.println("# of nodes visited: " + (test
                + nodesvisitedbase));
            multinode.printSearch(0);
        }
        else if (multinode != null && multinode.isLeaf()) {
            test = multinode.printCount(0);
            System.out.println("# of nodes visited: " + (test
                + nodesvisitedbase));
            int prefixlength = name.toCharArray().length;
            String prefix = null;
            if (multinode.getSeqname().toCharArray().length >= prefixlength) {
                prefix = String.valueOf(multinode.getSeqname().toCharArray(), 0,
                    prefixlength);
            }
            if (name.equals(prefix)) {
                multinode.printSearch(0);
            }
            else {
                System.out.println("no sequence found");

            }

        }
        else {
            test = multinode.printCount(0);
            System.out.println("# of nodes visited: " + (test
                + nodesvisitedbase));
            System.out.println("no sequence found");
        }

    }


    /**
     * new print method with spacing
     */
    public static void printNew() {
        System.out.println("tree dump:");
        BaseNode root = dna.root;
        root.printNew(0);

    }


    /**
     * printing for sequence length
     */
    public static void printNewLength() {
        System.out.println("tree dump:");
        BaseNode root = dna.root;
        root.printNewLength(0);

    }


    /**
     * printing for sequence length
     */
    public static void printNewStats() {
        System.out.println("tree dump:");
        BaseNode root = dna.root;
        root.printNewStats(0);

    }


    /**
     * used mainly for testing
     * 
     * @return - returns the dna tree
     */
    public static DNATreeOps getDNAtree() {
        return dna;
    }

}
