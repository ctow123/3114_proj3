
public class MinHeapRecord {
    private Record[] Heap;
    private int size;
    private int maxsize;
    private int specialinsert;

    private static final int FRONT = 1;


    public MinHeapRecord(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        this.specialinsert = maxsize;
        Heap = new Record[this.maxsize + 1];
        Heap[0] = new Record(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }


    // Function to return the position of
    // the parent for the node currently
    // at pos
    private int parent(int pos) {
        return pos / 2;
    }


    // Function to return the position of the
    // left child for the node currently at pos
    private int leftChild(int pos) {
        return (2 * pos);
    }


    // Function to return the position of
    // the right child for the node currently
    // at pos
    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }


    // Function that returns true if the passed
    // node is a leaf node
    private boolean isLeaf(int pos) {
        if (size > rightChild(pos) || size > leftChild(pos)) {
            return false;
        }
        return true;
    }


    // Function to swap two nodes of the heap
    private void swap(int fpos, int spos) {
        Record tmp;
        tmp = Heap[fpos];
        Heap[fpos] = Heap[spos];
        Heap[spos] = tmp;
    }


    // Function to heapify the node at pos
    private void minHeapify(int pos) {

        // If the node is a non-leaf node and greater
        // than any of its child
        if (!isLeaf(pos)) {
            if (Heap[pos].getKey() > Heap[leftChild(pos)].getKey() || Heap[pos]
                .getKey() > Heap[rightChild(pos)].getKey()) {

                // Swap with the left child and heapify
                // the left child
                if (Heap[leftChild(pos)].getKey() < Heap[rightChild(pos)]
                    .getKey()) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }

                // Swap with the right child and heapify
                // the right child
                else {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }


    // Function to insert a node into the heap
    public boolean insert(Record element) {
        if (size < maxsize && size < specialinsert) {
            Heap[++size] = element;
            int current = size;

            while (Heap[current].getKey() < Heap[parent(current)].getKey()) {
                swap(current, parent(current));
                current = parent(current);
            }
            return true;
        }
        else {
            return false;
        }
    }


    // Function to print the contents of the heap
    public void print() {
        if (size % 2 == 0) {
            int i;
            for (i = 1; i < size / 2; i++) {
                System.out.print(" PARENT : " + Heap[i].getKey()
                    + " LEFT CHILD : " + Heap[2 * i].getKey() + " RIGHT CHILD :"
                    + Heap[2 * i + 1].getKey());
                System.out.println();
            }
            System.out.print(" PARENT : " + Heap[i].getKey() + " LEFT CHILD : "
                + Heap[2 * i].getKey() + " RIGHT CHILD : none");
            System.out.println();
        }
        else {
            for (int i = 1; i <= size / 2; i++) {
                System.out.print(" PARENT : " + Heap[i].getKey()
                    + " LEFT CHILD : " + Heap[2 * i].getKey() + " RIGHT CHILD :"
                    + Heap[2 * i + 1].getKey());
                System.out.println();
            }
        }

    }


    // Function to build the min heap using
    // the minHeapify
    public void minHeap() {
        for (int pos = (size / 2); pos >= 1; pos--) {
            minHeapify(pos);
        }
    }


    // Function to remove and return the minimum
    // element from the heap
    public Record remove() {
        Record popped = Heap[FRONT];
        Heap[FRONT] = Heap[size];
        Heap[size] = new Record(0, 0);
        size--;
        minHeapify(FRONT);
        return popped;
    }


    // Driver code
    public static void main(String[] arg) {
        System.out.println("The Min Heap is ");
        MinHeap minHeap = new MinHeap(15);
        minHeap.insert(5);
        minHeap.insert(3);
        minHeap.insert(17);
        minHeap.insert(10);
        minHeap.insert(84);
        minHeap.insert(19);
        minHeap.insert(6);
        minHeap.insert(22);
        minHeap.insert(9);
        minHeap.minHeap();

        minHeap.print();
        System.out.println("The Min val is " + minHeap.remove());
    }


    // MY CODE
    public boolean specialInsert(Record element) {
        if (specialinsert > size) {
            Record temp = Heap[specialinsert];
            Heap[specialinsert] = element;
            if (temp == null || temp.getKey() != 0) {
                this.insert(temp);
            }
            specialinsert--;
            return true;
        }
        else {
            return false;
        }

    }


    public Record[] mainHeap() {
        return Heap;
    }


    public int getSpecialInsert() {
        return specialinsert;
    }

}
