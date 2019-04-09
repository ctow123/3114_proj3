
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


    /**
     * default constructor
     */
    public Externalsort() {

    }


    /**
     * constructor used for testing
     * 
     * @throws IOException
     */
    public Externalsort(String file) {
        try {
            data = new RandomAccessFile(file, "r");
            count = 0;
            runs = new LinkedList();
            filesize = (int)(data.length() / 16);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


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
        // System.out.println("avaible " + data.length());
        filesize = (int)(data.length() / 16);
// if(data.exists()){
// data.delete();
        inputbuf = new Buffer(512);
        Buffer outputbuf = new Buffer(512);
        MinHeapRecord heaparray = new MinHeapRecord(4096);
        // bitbuffers
        byte[] inputbytebuf = new byte[8192];
        byte[] outputbytebuf = new byte[8192];

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
        Externalsort.initialHeapFill(heaparray);

        Externalsort.fillBuffer(count, inputbuf);
        
        // MAINLOOP

        // build runs
        File f1 = new File("runs.bin");
        if (f1.exists()) {
            f1.delete();
        }
        Externalsort.buildRuns(inputbuf, outputbuf, heaparray);
       // System.out.println("OG " + data.length());
        data.close();
        File f2 = new File(args[0]);
        if (f2.exists()) {
            f2.delete();
        }
        // initial runs
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
        runs.clear();
        int count = 0;
        int pass = 0;

        // passes
        if (merge > 8) {
            // sort 8 runs at a time, use merge 7
            File f3 = new File("runs2.bin");
            if (f3.exists()) {
                f3.delete();
            }
            int offset = 0;
            while (merge > 8) {
                int[] partialmerge = new int[9];
                int[] partialmergecopy = new int[9];
                int mi = 0;
                while (mi < 9) {
                    partialmerge[mi] = mergearray[mi + (8 * offset)];
                    partialmergecopy[mi] = mergearray[mi + (8 * offset)];
                    mi++;
                }
                // if file exists delete before writing
                String load = "runs.bin";
                String store = "runs2.bin";

                Externalsort.mergeRuns(inputbuf, outputbuf, heaparray,
                    partialmerge, partialmergecopy, 7, load, store);
                merge = merge - 8;
                offset++;
                // new runs
                runs.add(mergearray[offset * 8]);

            }
            // cases where 2 runs left after iternations of 8 runs, use args[0]
            // as store
            int[] partialmerge = new int[merge + 2];
            int[] partialmergecopy = new int[merge + 2];
            int mi = 0;
            while (mi < merge + 1) {
                partialmerge[mi] = mergearray[mi + (8 * offset)];
                partialmergecopy[mi] = mergearray[mi + (8 * offset)];
                mi++;
            }
            partialmerge[mi] = filesize;
            partialmergecopy[mi] = filesize;
            String load;
            String store;

            load = "runs.bin";
            store = "runs2.bin";

            Externalsort.mergeRuns(inputbuf, outputbuf, heaparray, partialmerge,
                partialmergecopy, merge, load, store);
            // sorting 2 runs after >8 run merge
            merge = runs.size();

            i = 0;
            while (i < merge) {
                mergearray[i + 1] = runs.get(i);
                mergearraycopy[i + 1] = runs.get(i);
                i++;
            }
            mergearray[i + 1] = filesize;
            mergearraycopy[i + 1] = filesize;
            runs.clear();
            Externalsort.mergeRuns(inputbuf, outputbuf, heaparray, mergearray,
                mergearraycopy, merge, "runs2.bin", args[0]);

        }
        else {
            //System.out.println("OG " + data.length());
//            RandomAccessFile data2 = new RandomAccessFile("runs.bin", "r");
//            System.out.println("runs "  + data2.length());
//            data2.close();
            File f4 = new File(args[0]);
            if (f4.exists()) {
                f4.delete();
            }
            // may need a delete in file store, in file load?
            Externalsort.mergeRuns(inputbuf, outputbuf, heaparray, mergearray,
                mergearraycopy, merge, "runs.bin", args[0]);
           
//            data = new RandomAccessFile(args[0], "r");
//            System.out.println("OG " + data.length());
//            data.close();
//            data2 = new RandomAccessFile("runs.bin", "r");
//            System.out.println("runs "  + data2.length());
//            data2.close();
        }

        // merge
       
       
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
        String file,
        int fileplace,
        int amount,
        int run,
        MinHeapRecord heap)
        throws IOException {
        byte[] eight = new byte[8];
        // read from not OG file but new one
        RandomAccessFile data2 = new RandomAccessFile(file, "r");
        // System.out.println(file + data2.length());
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


    public static void fillBuffer(int fileplace, Buffer inputbuf)
        throws IOException {
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
        boolean newrun = false;
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
                            if (inputbuf.peek().getKey() >= outputbuf.peek()
                                .getKey()) {
                                heaparray.insert(inputbuf.getRecord());
                            }
                            else {
                                heaparray.specialInsert(inputbuf.getRecord());
                            }
                        }

                        outputbuf.insert(heaparray.remove());
                        fakecount++;
                        if (inputbuf.peek().getKey() >= outputbuf.peek()
                            .getKey()) {
                            heaparray.insert(inputbuf.getRecord());
                        }
                        else {
                            heaparray.specialInsert(inputbuf.getRecord());
                        }
                    }
                    else if (count < filesize) {
                        Externalsort.fillBuffer(count, inputbuf);
                    }
                    else {
                        // output buf is empty need to compare to last entry in
                        // CHANGES HERE
                        int last = 0;
                        RandomAccessFile data4 = null;
                        File f4 = new File("runs.bin");
                        if (f4.exists()) {
                            data4 = new RandomAccessFile("runs.bin", "r");
                            last = (int)data4.length();
                            // System.out.println(last);
                            data4.seek(last - 8);
                        }

                        if (outputbuf.getSize() == 0 && last > 0 && !newrun) {
                            byte[] eight = new byte[8];
                            data4.readFully(eight);
                            ByteBuffer bb = ByteBuffer.wrap(eight);
                            Double l = bb.getDouble();
                            if (heaparray.peek().getKey() >= l) {
                                outputbuf.insert(heaparray.remove());
                                fakecount++;
                            }
                            else {
                                heaparray.specialInsert(heaparray.remove());
                            }
                        }
                        else {
                            if (outputbuf.peek() == null || heaparray.peek()
                                .getKey() >= outputbuf.peek().getKey()) {
                                outputbuf.insert(heaparray.remove());
                                fakecount++;
                                newrun = false;
                            }
                            else {
                                heaparray.specialInsert(heaparray.remove());
                                newrun = false;
                            }
                        }

                        if (f4.exists()) {
                            data4.close();
                        }

                    }
                }

            }
            outputbuf.dumpBuffer("output.txt", "runs.bin");
            if (fakecount < filesize) {
                runs.add(fakecount - 1);
                newrun = true;
            }
            Externalsort.reorderHeap(heaparray);

        }
    }


    public static void mergeRuns(
        Buffer inputbuf,
        Buffer outputbuf,
        MinHeapRecord heaparray,
        int[] mergearray,
        int[] mergearraycopy,
        int merge,
        String fileload,
        String filestore)
        throws IOException {
        // multiway merge, this needs to be false
        int count = 0;
        while (!Externalsort.mergeDone(mergearray, mergearraycopy, merge + 1)) {

            // initial load, once
            while (count <= merge) {
                int amount = Externalsort.calcMergeAmount(mergearray,
                    mergearraycopy, count);
                Externalsort.loadRunData(fileload, mergearray[count], amount,
                    count, heaparray);
                mergearray[count] = mergearray[count] + amount;
                count++;
            }
            // load when empty

            if (outputbuf.isFull()) {
                outputbuf.dumpBuffer("output.txt", filestore);
            }
            // check runs
            int runcount = 0;
            while (runcount < merge + 1) {
                // second condition is data left
                if (heaparray.isRunEmpty(runcount)) {
                    int amount = Externalsort.calcMergeAmount(mergearray,
                        mergearraycopy, runcount);
                    Externalsort.loadRunData(fileload, mergearray[runcount],
                        amount, runcount, heaparray);
                    mergearray[runcount] = mergearray[runcount] + amount;
                }
                runcount++;
            }
            outputbuf.insert(heaparray.runsMin(merge + 1));
            //System.out.println("runs "  + data.length());
        }
        while (heaparray.size() > 0) {
            if (outputbuf.isFull()) {
                outputbuf.dumpBuffer("output.txt", filestore);
            }else {
                outputbuf.insert(heaparray.runsMin(merge + 1)); 
            }
            
        }

        outputbuf.dumpBuffer("output.txt", filestore);
        
    }


    public static void buildOutput(String file) throws IOException {
        RandomAccessFile data3 = new RandomAccessFile(file, "r");
        //System.out.println("OG " + data3.length()); 
       // System.out.println(data.length());
        int blocks = (int)data3.length() / 8192;
        byte[] eight = new byte[8];
        for (int i = 1; i < blocks + 1; i++) {
            if (i > 0) {
                data3.seek((i - 1) * 8192);
            }
            else {
                data3.seek((i - 1) * 8192);
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


    public static void initialHeapFill(MinHeapRecord heaparray)
        throws IOException {
        byte[] eight = new byte[8];
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
    }

}
