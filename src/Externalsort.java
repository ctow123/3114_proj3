
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
 * This sorts a binary file in ascending order using replacement selection and
 * merge sort
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
    private static MinHeapRecord heaparray;


    /**
     * default constructor
     */
    public Externalsort() {
        // for testing
    }


    /**
     * reads a file from the arguments and sorts that file
     * 
     * @param args
     *            - the file to be passed in
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        runs = new LinkedList();
        data = new RandomAccessFile(args[0], "r");
        filesize = (int)(data.length() / 16);
        inputbuf = new Buffer(512);
        Buffer outputbuf = new Buffer(512);
        heaparray = new MinHeapRecord(4096);
        count = 0;

        // bitbuffers
        // byte[] inputbytebuf = new byte[8192];
        // byte[] outputbytebuf = new byte[8192];

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
// setting up the heap and input buf initially
        Externalsort.initialHeapFill(heaparray);
        Externalsort.fillBuffer(count, inputbuf);

        // MAINLOOP

        // build runs and clean initial file so it can be overwritten
        File f1 = new File("runs.bin");
        if (f1.exists()) {
            f1.delete();
        }
        Externalsort.buildRuns(inputbuf, outputbuf, heaparray);
        data.close();

        File f2 = new File(args[0]);
        if (f2.exists()) {
            f2.delete();
        }

        // initialize runs
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

        // passes
        // the if merge > 8 handles the passes, the else is if you only need one
        // pass
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
            // cases where 2 runs left after iterations of 8 runs, use args[0]
            // ex. if u have 10 runs
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
        // when runs are less than 8 only need a single pass
        else {
            File f4 = new File(args[0]);
            if (f4.exists()) {
                f4.delete();
            }
            Externalsort.mergeRuns(inputbuf, outputbuf, heaparray, mergearray,
                mergearraycopy, merge, "runs.bin", args[0]);

        }

        // aftering merging via runs and passes build the output

        Externalsort.buildOutput(args[0]);

    }


    /**
     * calculates how many records to read into the heap
     * 
     * @param mergearray
     *            array with the run locations
     * @param mergearraycopy
     *            copy of the array with the run locations
     * @param target
     *            the run (0-7)
     * @return int value of how many records need to be read in from the input
     *         file
     */
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


    /**
     * calculates if merging is done, by checking if the the runs have been
     * fully read
     * 
     * @param marray
     *            array with the run locations
     * @param marraycopy
     *            copy of the array with the run locations
     * @param length
     *            of the above arrays (the number of runs)
     * @return true if merging is done (all runs have been read)
     */
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
        return (i == length);
    }


    /**
     * loads data into the heap for a specified run
     * 
     * @param file
     *            file you're reading data from
     * @param fileplace
     *            the place in the file to start
     * @param amount
     *            how many records you want to read
     * @param run
     *            the run you want to fill (0-7)
     * @param heap
     *            the heap object
     * @throws IOException
     */
    public static void loadRunData(
        String file,
        int fileplace,
        int amount,
        int run,
        MinHeapRecord heap)
        throws IOException {
        byte[] eight = new byte[8];
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
        data2.close();
    }


    /**
     * fills the input buffer by reading in data from the inputfile at the
     * specified starting place, used for building the runs
     * 
     * @param fileplace
     *            the current place in the file you want to read data from
     * @param inputbuff
     *            the input buffer
     * @throws IOException
     */
    public static void fillBuffer(int fileplace, Buffer inputbuff)
        throws IOException {
        byte[] eight = new byte[8];
        data.seek(fileplace * 16);
        while (!inputbuff.isFull() && count < filesize) {

            data.readFully(eight);
            ByteBuffer bb = ByteBuffer.wrap(eight);
            long l = bb.getLong();

            data.readFully(eight);
            bb = ByteBuffer.wrap(eight);
            double l2 = bb.getDouble();

            inputbuff.insert(new Record(l, l2));

            count++;
        }
    }


    /**
     * reinserts all the special inserts back into the heap as regular inserts
     * (so they will actually be part of the tree) for the next run
     * 
     * @param heap
     *            the heap object
     */
    public static void reorderHeap(MinHeapRecord heap) {
        heap.getSpecialInsert();
        while (heap.getSpecialInsert() < heap.maxsize()) {
            Record temp = heap.removeSpecial(heap.getSpecialInsert() + 1);
            heap.insertReorder(temp);
            heap.setSpecialInsert(heap.getSpecialInsert() + 1);
        }

    }


    /**
     * from the input file builds you initial runs file, which you will then
     * merge
     * 
     * @param inputbuff
     *            input buffer
     * @param outputbuff
     *            output buffer
     * @param heap
     *            the heap
     * @throws IOException
     */
    public static void buildRuns(
        Buffer inputbuff,
        Buffer outputbuff,
        MinHeapRecord heap)
        throws IOException {
        int fakecount = 1;
        boolean newrun = false;
        // while heap is not empty
        while (heap.realsize() > 0) {
            // for current run keep going until heap is full or "empty" not
            // counting specialinserts
            while (heap.getSpecialInsert() != 0 && heap.size() > 0) {

                if (outputbuff.isFull()) {
                    outputbuff.dumpBuffer("output.txt", "runs.bin");
                }

                else {
                    // if inputbuf isn't empty do an insert
                    if (!inputbuf.isEmpty()) {
                        if (outputbuff.isEmpty()) {
                            outputbuff.insert(heap.remove());
                            fakecount++;
                            if (inputbuff.peek().getKey() >= outputbuff.peek()
                                .getKey()) {
                                heap.insert(inputbuff.getRecord());
                            }
                            else {
                                heap.specialInsert(inputbuff.getRecord());
                            }
                        }

                        outputbuff.insert(heap.remove());
                        fakecount++;
                        if (inputbuff.peek().getKey() >= outputbuff.peek()
                            .getKey()) {
                            heap.insert(inputbuff.getRecord());
                        }
                        else {
                            heap.specialInsert(inputbuff.getRecord());
                        }
                    }
                    // fill the inputbuf if still file left to read
                    else if (count < filesize) {
                        Externalsort.fillBuffer(count, inputbuf);
                    }
                    // fill outputbuf from heap
                    else {
                        // if output buf is empty need to compare to last entry
                        // in
                        // CHANGES HERE
                        int last = 0;
                        RandomAccessFile data4 = null;
                        File f4 = new File("runs.bin");
                        if (f4.exists()) {
                            data4 = new RandomAccessFile("runs.bin", "r");
                            last = (int)data4.length();
                            data4.seek(last - 8);
                        }

                        if (outputbuff.getSize() == 0 && last > 0 && !newrun) {
                            byte[] eight = new byte[8];
                            data4.readFully(eight);
                            ByteBuffer bb = ByteBuffer.wrap(eight);
                            Double l = bb.getDouble();
                            if (heap.peek().getKey() >= l) {
                                outputbuff.insert(heap.remove());
                                fakecount++;
                            }
                            else {
                                heap.specialInsert(heap.remove());
                            }
                        }
                        else {
                            if (outputbuff.peek() == null || heap.peek()
                                .getKey() >= outputbuff.peek().getKey()) {
                                outputbuff.insert(heap.remove());
                                fakecount++;
                                newrun = false;
                            }
                            else {
                                heap.specialInsert(heap.remove());
                                newrun = false;
                            }
                        }

                        if (f4.exists()) {
                            data4.close();
                        }

                    }
                }

            }
            outputbuff.dumpBuffer("output.txt", "runs.bin");
            // heap is "empty" might still be special inserts, mark the end of
            // the run
            if (fakecount < filesize) {
                runs.add(fakecount - 1);
                newrun = true;
            }
            // put special inserts back
            Externalsort.reorderHeap(heap);

        }
    }


    /**
     * merges x number of runs from an input file into an output file
     * 
     * @param inputbuff
     *            the input buffer
     * @param outputbuf
     *            the output buffer
     * @param heap
     *            the heap
     * @param mergearray
     *            the array with the merge locations
     * @param mergearraycopy
     *            a copy of the array with the merge locations
     * @param merge
     *            the number of runs to merge
     * @param fileload
     *            the file you are merging runs from
     * @param filestore
     *            the file you are writing the output to
     * @throws IOException
     */
    public static void mergeRuns(
        Buffer inputbuff,
        Buffer outputbuf,
        MinHeapRecord heap,
        int[] mergearray,
        int[] mergearraycopy,
        int merge,
        String fileload,
        String filestore)
        throws IOException {
        int countm = 0;
        // while merge isnt done
        while (!Externalsort.mergeDone(mergearray, mergearraycopy, merge + 1)) {

            // initial load, of the runs into the heap
            while (countm <= merge) {
                int amount = Externalsort.calcMergeAmount(mergearray,
                    mergearraycopy, countm);
                Externalsort.loadRunData(fileload, mergearray[countm], amount,
                    countm, heap);
                mergearray[countm] = mergearray[countm] + amount;
                countm++;
            }
            // check outputbuf if full
            if (outputbuf.isFull()) {
                outputbuf.dumpBuffer("output.txt", filestore);
            }
            // check runs, loads when empty, and then insert into outputbuf
            int runcount = 0;
            while (runcount < merge + 1) {
                // second condition is data left
                if (heap.isRunEmpty(runcount)) {
                    int amount = Externalsort.calcMergeAmount(mergearray,
                        mergearraycopy, runcount);
                    Externalsort.loadRunData(fileload, mergearray[runcount],
                        amount, runcount, heap);
                    mergearray[runcount] = mergearray[runcount] + amount;
                }
                runcount++;
            }
            outputbuf.insert(heap.runsMin(merge + 1));
        }
        // after merge is "done" all data is loaded sort the remainder of the
        // heap
        while (heap.size() > 0) {
            if (outputbuf.isFull()) {
                outputbuf.dumpBuffer("output.txt", filestore);
            }
            else {
                outputbuf.insert(heap.runsMin(merge + 1));
            }
        }
        outputbuf.dumpBuffer("output.txt", filestore);

    }


    /**
     * builds the text output you see on the console, the first record of every
     * block in the file
     * 
     * @param file
     *            the file you are reading the data from when printing to the
     *            console
     * @throws IOException
     */
    public static void buildOutput(String file) throws IOException {
        RandomAccessFile data3 = new RandomAccessFile(file, "r");
        // System.out.println("OG " + data3.length());
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


    /**
     * loads 8 blocks of data into the heap, used only once to fill the heap
     * after the file is opened
     * 
     * @param heaparray1
     *            the heap that will be filled
     * @throws IOException
     */
    public static void initialHeapFill(MinHeapRecord heaparray1)
        throws IOException {
        byte[] eight = new byte[8];
        while (!heaparray1.isFull() && count < (data.length() / 16)) {
            data.readFully(eight);
            ByteBuffer bb = ByteBuffer.wrap(eight);
            long l = bb.getLong();

            data.readFully(eight);
            bb = ByteBuffer.wrap(eight);
            double l2 = bb.getDouble();
            heaparray1.insert(new Record(l, l2));

            count++;
        }
    }


    /**
     * for testing
     * 
     * @return the heap object
     */
    public static MinHeapRecord getHeap() {
        return heaparray;
    }
}
