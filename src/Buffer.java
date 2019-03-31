import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
     * @throws IOException 
     */
    public void dumpBuffer(String outputFile) throws IOException {
        //PrintWriter pw = new PrintWriter("output.txt");
        FileWriter fw = new FileWriter("output.txt",true);
        // PrintWriter pw = new PrintWriter("output.txt", teu);
        while (!this.isEmpty()) {
            fw.append(Double.toString(this.getRecordFront().getKey()));
            fw.append("\n");
        }
        fw.close();
        front = 1;

    }


    public boolean isFull() {
        if (size < 512) {
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
    
    public Record peek() {
        if(size>0) {
            return buf[size];
        }
        else {
            return null;
        }
    }
}
