
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
        // Reads in command line argument
        InputStream inputStream = new BufferedInputStream(new FileInputStream(
            "sampleInput16.bin"), 8192);
        OutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream("output.bin"));
        // print writer uses plain text
        PrintWriter outputStreamtxt = new PrintWriter("output.txt");
        // dataoutput stream writes in binary
        // Scanner file = new Scanner(new File(args[0]));
        System.out.println("avaible " + inputStream.available());
        // 1024, 8192, priorityqueue in java is minheap
        Record[] inputbufarray = new Record[512];
        Record[] outputbufarray = new Record[512];
        MinHeapRecord heaparray = new MinHeapRecord(4096);
        RandomAccessFile data = new RandomAccessFile("sampleInput16.bin", "r");
        
        System.out.print(data.length() + " \n");
        // building initial , need to loop eventually
        // makes the runs
        // then worry about merge sort
        byte[] eight = new byte[8];
        for (long i = 0, len = data.length() / 16; i < len; i++) {
            data.readFully(eight);
            ByteBuffer bb = ByteBuffer.wrap(eight);
            long l = bb.getLong();

            data.readFully(eight);
            bb = ByteBuffer.wrap(eight);
            double l2 = bb.getDouble();
            if (i <= 4096) {
                heaparray.insert(new Record(l, l2));
                // heaparray1.add(new Record(l, l2));

            }
            if (i >= 4096 && i < 4096 + 512) {
                inputbufarray[(int)(i - 4096)] = new Record(l, l2);
            }
        }
        for (int j = 0; j < 4096; j++) {

        }
        // this converts double to bytes and writes to buffered output stream,
        // if writing to dataoutputstream default is bytes
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble((heaparray.mainHeap()[(int)(8)]
            .getKey()));
        outputStream.write(bytes);

        // heaparray.print();
        System.out.println(inputbufarray[(int)(511)].getKey());

        // begin buildling the runs
        for (int k = 0; k < 512; k++) {
            Record temp = heaparray.remove();
            outputStreamtxt.write(Double.toString((temp.getKey())));
            outputStreamtxt.write("\n");
            heaparray.insert(inputbufarray[(int)k]);
            

        }
        System.out.println(heaparray.getSpecialInsert());
        // need buffer fill fcn
        
        // need buffer dump fcn

        // merge

        // close files
        outputStream.close();
        outputStreamtxt.close();
    }

}
