
import java.io.*;
import java.nio.ByteBuffer;
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
 * @author connor
 *
 */
public class Externalsort {
    private static final int BUFFER_SIZE = 0;
    private static Buffer inputbuf;
    private static int count;
    private static int filesize;
    private static RandomAccessFile data;
    private static LinkedList<Integer> runs;


    // use priority queue for min heap java class
    /**
     * Parses the text file and executes the commands by the given command
     * script to
     * the output text file. will come later.
     * 
     * @param args
     *            - the file to be passed in
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Reads in command line argument args[0]
        runs = new LinkedList();
        data = new RandomAccessFile(args[0], "r");
        //System.out.println("avaible " + data.length());
        filesize = (int)(data.length() / 16);
// if(data.exists()){
// data.delete();
        inputbuf = new Buffer(512);
        Buffer outputbuf = new Buffer(512);
        MinHeapRecord heaparray = new MinHeapRecord(4096);
        // bitbuffers
        byte[] inputbytebuf = new byte[8192];
        byte[] outputbytebuf = new byte[8192];

        byte[] eight = new byte[8];
        count = 0;
        // printing everything to a file
// FileWriter fw = new FileWriter("rawdata.txt", true);
// while (count < (data.length() / 16)) {
// data.readFully(eight);
// ByteBuffer bb = ByteBuffer.wrap(eight);
// long l = bb.getLong();
//
// data.readFully(eight);
// bb = ByteBuffer.wrap(eight);
// double l2 = bb.getDouble();
//
// fw.append(Long.toString(l) + " ");
// fw.append(Double.toString(l2));
// fw.append("\n");
// count++;
// }
// fw.close();

        while (!heaparray.isFull() && count < (data.length() / 16)) {
            data.readFully(eight);
            ByteBuffer bb = ByteBuffer.wrap(eight);
            long l = bb.getLong();

            data.readFully(eight);
            bb = ByteBuffer.wrap(eight);
            double l2 = bb.getDouble();
            heaparray.insert(new Record(l, l2));

            count++;
        }
        // data.seek(filesize*16-8);
        // if count == 4096 then seek 4096*16
// data.readFully(eight);
// ByteBuffer bb = ByteBuffer.wrap(eight);
// System.out.println(bb.getDouble());
// System.out.println(count);
        Externalsort.fillBuffer(count);

        // MAINLOOP

        // build runs
        File f1 = new File("runs.bin");
        if (f1.exists()) {
            f1.delete();
        }
        Externalsort.buildRuns(inputbuf, outputbuf, heaparray);

        File f2 = new File(args[0]);
        if (f2.exists()) {
            f2.delete();
        }
        // passes 
        int merge = runs.size();

        
        int[] mergearray = new int[merge + 2];
        int[] mergearraycopy = new int[merge + 2];

        int i = 0;
        while (i < merge) {
            mergearray[i + 1] = runs.get(i);
            mergearraycopy[i + 1] = runs.get(i);
            i++;
        }
        mergearray[i + 1] = filesize;
        mergearraycopy[i + 1] = filesize;
        int count = 0;
        
        if(merge > 8) {
            
        }
        else {
            Externalsort.mergeRuns(inputbuf, outputbuf, heaparray, mergearray, mergearraycopy, merge, args[0]);
        }
        
        
        

        // merge
        data.close();

        Externalsort.buildOutput(args[0]);
    }


    public static int calcMergeAmount(
        int[] mergearray,
        int[] mergearraycopy,
        int target) {
        int amount = mergearraycopy[target + 1] - mergearray[target];
        if (amount >= 512) {
            return 512;
        }
        else {
            return amount;
        }
    }


    public static boolean mergeDone(
        int[] marray,
        int[] marraycopy,
        int length) {
        int i = 0;
        while (i < length) {
            if (marray[i] == marraycopy[i + 1]) {
                i++;
            }
            else {
                return false;
            }
        }
        if (i == length) {
            return true;
        }
        return false;
    }


    public static void loadRunData(
        int fileplace,
        int amount,
        int run,
        MinHeapRecord heap)
        throws IOException {
        byte[] eight = new byte[8];
        // read from not OG file but new one
        RandomAccessFile data2 = new RandomAccessFile("runs.bin", "r");
        // System.out.println(data2.length());
        data2.seek(fileplace * 16);
        int counter = 0;
        while (counter < amount) {

            data2.readFully(eight);
            ByteBuffer bb = ByteBuffer.wrap(eight);
            long l = bb.getLong();

            data2.readFully(eight);
            bb = ByteBuffer.wrap(eight);
            double l2 = bb.getDouble();
            heap.insertRegular(new Record(l, l2), run);

            counter++;
        }
        // System.out.println("loaded run data" + counter);
        data2.close();
    }


    public static void fillBuffer(int fileplace) throws IOException {
        byte[] eight = new byte[8];
        data.seek(fileplace * 16);
        while (!inputbuf.isFull() && count < filesize) {

            data.readFully(eight);
            ByteBuffer bb = ByteBuffer.wrap(eight);
            long l = bb.getLong();

            data.readFully(eight);
            bb = ByteBuffer.wrap(eight);
            double l2 = bb.getDouble();

            inputbuf.insert(new Record(l, l2));

            count++;
        }
        // System.out.println("filled" + count);
    }


    public static void reorderHeap(MinHeapRecord heap) {
        heap.getSpecialInsert();
        while (heap.getSpecialInsert() < heap.maxsize()) {
            Record temp = heap.removeSpecial(heap.getSpecialInsert() + 1);

            heap.insertReorder(temp);
            heap.setSpecialInsert(heap.getSpecialInsert() + 1);
            // heap.setRealSize(heap.realsize() - 1);
        }
// while (heap.getSpecialInsert() < heap.maxsize()) {
// heap.insert(heap.mainHeap()[heap.getSpecialInsert() + 1]);
// heap.setSpecialInsert(heap.getSpecialInsert() + 1);
// heap.setRealSize(heap.realsize() - 1);
// }
    }


    public static void buildRuns(
        Buffer inputbuf,
        Buffer outputbuf,
        MinHeapRecord heaparray)
        throws IOException {
        int fakecount = 1;
        while (heaparray.realsize() > 0) {
            while (heaparray.getSpecialInsert() != 0 && heaparray.size() > 0) {

                if (outputbuf.isFull()) {
                    outputbuf.dumpBuffer("output.txt", "runs.bin");
                    // System.out.println("dump " + count);
                }

                else {
                    // if equals shouldn't matter
                    if (!inputbuf.isEmpty()) {
                        if (outputbuf.isEmpty()) {
                            outputbuf.insert(heaparray.remove());
                            fakecount++;
                            if (inputbuf.peek().getKey() > outputbuf.peek()
                                .getKey()) {
                                heaparray.insert(inputbuf.getRecord());
                            }
                            else {
                                heaparray.specialInsert(inputbuf.getRecord());
                            }
                        }

                        outputbuf.insert(heaparray.remove());
                        fakecount++;
                        if (inputbuf.peek().getKey() > outputbuf.peek()
                            .getKey()) {
                            heaparray.insert(inputbuf.getRecord());
                        }
                        else {
                            heaparray.specialInsert(inputbuf.getRecord());
                        }
                    }
                    else if (count < filesize) {
                        Externalsort.fillBuffer(count);
                    }
                    else {
                        if (outputbuf.peek() == null || heaparray.peek()
                            .getKey() > outputbuf.peek().getKey()) {
                            outputbuf.insert(heaparray.remove());
                            fakecount++;
                        }
                        else {
                            heaparray.specialInsert(heaparray.remove());
                        }
                    }
                }

            }
            outputbuf.dumpBuffer("output.txt", "runs.bin");
            if (fakecount < filesize) {
                runs.add(fakecount - 1);
            }
            Externalsort.reorderHeap(heaparray);

        }
    }


    public static void mergeRuns(
        Buffer inputbuf,
        Buffer outputbuf,
        MinHeapRecord heaparray,
        int[] mergearray, int[] mergearraycopy, int merge, String file)
        throws IOException {
     // multiway merge, this needs to be false
        while (!Externalsort.mergeDone(mergearray, mergearraycopy, merge + 1)) {

            // initial load, once
            while (count <= merge) {
                int amount = Externalsort.calcMergeAmount(mergearray,
                    mergearraycopy, count);
                Externalsort.loadRunData(mergearray[count], amount, count,
                    heaparray);
                mergearray[count] = mergearray[count] + amount;
                count++;
            }
            // load when empty

            if (outputbuf.isFull()) {
                outputbuf.dumpBuffer("output.txt", file);
            }
            // check runs
            int runcount = 0;
            while (runcount < merge + 1) {
                // second condition is data left
                if (heaparray.isRunEmpty(runcount)) {
                    int amount = Externalsort.calcMergeAmount(mergearray,
                        mergearraycopy, runcount);
                    Externalsort.loadRunData(mergearray[runcount], amount,
                        runcount, heaparray);
                    mergearray[runcount] = mergearray[runcount] + amount;
                }
                runcount++;
            }
            outputbuf.insert(heaparray.runsMin(merge + 1));

        }
        while (heaparray.size() > 0) {
            outputbuf.insert(heaparray.runsMin(merge + 1));
        }

        outputbuf.dumpBuffer("output.txt", file);
    }


    public static void buildOutput(String file) throws IOException {
        RandomAccessFile data3 = new RandomAccessFile(file, "r");
        //System.out.println(data3.length());
        int blocks = (int)data3.length() / 8192;
        byte[] eight = new byte[8];
        for (int i = 1; i < blocks+1; i++) {
            if (i > 0) {
                data3.seek((i-1) * 8192);
            }
            else {
                data3.seek((i-1) * 8192);
            }

            data3.readFully(eight);
            ByteBuffer bb = ByteBuffer.wrap(eight);
            long l = bb.getLong();

            data3.readFully(eight);
            bb = ByteBuffer.wrap(eight);
            double l2 = bb.getDouble();
            System.out.print(l + " " + l2 + " ");
            if (i > 0 && i % 5 == 0) {
                System.out.print("\n");
            }
        }
        data3.close();

    }

}
