
public class Buffer {
    private Record[] buf;
    private int size;
    private int maxsize;
    private int specialinsert;


    public Buffer(int size) {
        this.maxsize = maxsize;
        this.size = 0;
        this.specialinsert = maxsize;
        Heap = new Record[this.maxsize + 1];
        Heap[0] = new Record(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
}
