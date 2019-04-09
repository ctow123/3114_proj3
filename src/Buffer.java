import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * represents a buffer that stores 1 block (8192bytes) or 512 records, each
 * record being 16 bytes
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor
 *
 */
public class Buffer {
    private Record[] buf;
    private int size;
    private int maxsize;
    private int front;


    /**
     * constructor for buffer
     * 
     * @param size
     *            size of the buffer
     */
    public Buffer(int size) {
        this.front = 1;
        this.maxsize = size;
        this.size = 0;
        buf = new Record[this.maxsize + 1];
        buf[0] = new Record(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }


    /**
     * dumps the contents in bytes of the buffer to an output file
     * 
     * @param outputtxt
     *            textfile for debugging purposes
     * @param outputFile
     *            the binary file
     * @throws IOException
     */
    public void dumpBuffer(String outputtxt, String outputFile)
        throws IOException {
        // PrintWriter pw = new PrintWriter("output.txt");
        // FileWriter fw = new FileWriter(outputtxt, true);
        // PrintWriter pw = new PrintWriter("output.txt", teu);

        // bits output
        OutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(outputFile, true));

        Record temp;
        while (!this.isEmpty()) {
            temp = this.getRecordFront();
            // fw.append(Long.toString(temp.getID()) + " ");
            // fw.append(Double.toString(temp.getKey()));
            // fw.append("\n");
            // bits output
            byte[] bytes = new byte[8];
            byte[] bytes2 = new byte[8];
            ByteBuffer.wrap(bytes).putLong(temp.getID());
            ByteBuffer.wrap(bytes2).putDouble(temp.getKey());
            outputStream.write(bytes);
            outputStream.write(bytes2);
        }
        // fw.close();
        front = 1;
        outputStream.close();
// RandomAccessFile dat= new RandomAccessFile(outputFile, "r");
// System.out.println("buf dump " + dat.length());
// dat.close();
    }


    /**
     * 
     * @return true if the buffer is full false otherwise
     */
    public boolean isFull() {
        return size >= maxsize;

    }


    /**
     * 
     * @return true if the buffer is empty false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }


    /**
     * inserts record into the buffer
     * 
     * @param record
     *            record to insert
     */
    public void insert(Record record) {
        if (!this.isFull()) {
            buf[++size] = record;
        }
    }


    /**
     * 
     * @return size of the buffer
     */
    public int getSize() {
        return size;
    }


    /**
     * removes the record at the end of they array
     * 
     * @return the record at the end of the array
     */
    public Record getRecord() {
        if (size > 0) {
            Record temp = buf[size];
            buf[size] = new Record(0, 0);
            size--;
            return temp;
        }
        else {
            return null;
        }
    }


    /**
     * removes and returns the record at the front of the array, used in merging
     * 
     * @return the record at the front of the array
     */
    public Record getRecordFront() {
        if (size > 0) {
            Record temp = buf[front];
            buf[front] = new Record(0, 0);
            size--;
            front++;
            return temp;
        }
        else {
            return null;
        }
    }


    /**
     * only returns a preview, does not remove
     * 
     * @return the record at the end of the array
     */
    public Record peek() {
        if (size > 0) {
            return buf[size];
        }
        else {
            return null;
        }
    }
}
