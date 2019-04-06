import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.*;

public class Buffer {
    private Record[] buf;
    private int size;
    private int maxsize;
    private int specialinsert;
    private int front;


    public Buffer(int size) {
        this.front = 1;
        this.maxsize = size;
        this.size = 0;
        this.specialinsert = maxsize;
        buf = new Record[this.maxsize + 1];
        buf[0] = new Record(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }


    /**
     * dumps buffer to an output file when full
     * 
     * @throws IOException
     */
    public void dumpBuffer(String outputtxt, String outputFile) throws IOException {
        // PrintWriter pw = new PrintWriter("output.txt");
        //FileWriter fw = new FileWriter(outputtxt, true);
        // PrintWriter pw = new PrintWriter("output.txt", teu);

        // bits output
        OutputStream outputStream = new BufferedOutputStream(
            new FileOutputStream(outputFile, true));

        Record temp;
        while (!this.isEmpty()) {
            temp = this.getRecordFront();
            //fw.append(Long.toString(temp.getID()) + " ");
            //fw.append(Double.toString(temp.getKey()));
            //fw.append("\n");
            // bits output
            byte[] bytes = new byte[8];
            byte[] bytes2 = new byte[8];
            ByteBuffer.wrap(bytes).putLong(temp.getID());
            ByteBuffer.wrap(bytes2).putDouble(temp.getKey());
            outputStream.write(bytes);
            outputStream.write(bytes2);
        }
        //fw.close();
        front = 1;
        outputStream.close();

    }


    public boolean isFull() {
        if (size < maxsize) {
            return false;
        }
        else {
            return true;
        }
    }


    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }


    public void insert(Record record) {
        if (!this.isFull()) {
            buf[++size] = record;
        }
    }


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
