

public class MinHeapRecord {
    private Record[] Heap;
    private int size;
    private int maxsize;
    private int specialinsert;
    private int realsize;
    private static final int FRONT = 1;


    public MinHeapRecord(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        this.realsize = 0;
        this.specialinsert = maxsize;
        Heap = new Record[this.maxsize + 1];
        Heap[0] = new Record(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }


    // Function to return the position of
    // the parent for the node currently
    // at pos
    public int parent(int pos) {
        return pos / 2;
    }


    // Function to return the position of the
    // left child for the node currently at pos
    public int leftChild(int pos) {
        return (2 * pos);
    }


    // Function to return the position of
    // the right child for the node currently
    // at pos
    public int rightChild(int pos) {
        return (2 * pos) + 1;
    }


    // Function that returns true if the passed
    // node is a leaf node
    public boolean isLeaf(int pos) {
        if (size >= rightChild(pos) || size >= leftChild(pos)) {
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
            if(Heap[leftChild(pos)].getKey() != 0 && Heap[rightChild(pos)].getKey() != 0) {
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
            else if(Heap[leftChild(pos)].getKey() != 0){
                if (Heap[pos].getKey() > Heap[leftChild(pos)].getKey()) {
                        swap(pos, leftChild(pos));
                        minHeapify(leftChild(pos));
                 
                }
            }
            else {
                if (Heap[pos].getKey() > Heap[rightChild(pos)].getKey()) {               
                        swap(pos, rightChild(pos));
                        minHeapify(rightChild(pos));      
                }  
            }
            // OG code
//            if (Heap[pos].getKey() > Heap[leftChild(pos)].getKey() || Heap[pos]
//                .getKey() > Heap[rightChild(pos)].getKey()) {
//
//                // Swap with the left child and heapify
//                // the left child
//                if (Heap[leftChild(pos)].getKey() < Heap[rightChild(pos)]
//                    .getKey()) {
//                    swap(pos, leftChild(pos));
//                    minHeapify(leftChild(pos));
//                }
//
//                // Swap with the right child and heapify
//                // the right child
//                else {
//                    swap(pos, rightChild(pos));
//                    minHeapify(rightChild(pos));
//                }
//            }
            
        }
    }


    // Function to insert a node into the heap
    public boolean insert(Record element) {
        if (size < maxsize && size < specialinsert) {
            Heap[++size] = element;
            int current = size;
            realsize++;
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
        realsize--;
        minHeapify(FRONT);
        return popped;
    }


    public Record peek() {
        if (size > 0) {
            return Heap[FRONT];
        }
        else {
            return null;
        }
    }


    // MY CODE


    // merge stuff
    /**
     * inserting element for merging location is location of the run
     * 
     * @param element
     * @param location
     * @return
     */
    public boolean insertRegular(Record element, int location) {
        if (size < maxsize) {
            int insertloc = (512 * location) + 1;
            int insertlocpos = this.findEmptySpot(insertloc);
            Heap[insertlocpos] = element;
            size++;
            return true;
        }
        else {
            return false;
        }
    }


    private int findEmptySpot(int startpos) {
        int end = startpos + 512;
        int poscount = startpos;
        while (poscount < end) {
            if (Heap[poscount] == null || Heap[poscount].getKey() == 0) {
                return poscount;
            }
            poscount++;
        }
        if (poscount == end) {
            return -1;
        }
        return -1;

    }


    public boolean isRunEmpty(int runnum) {
        int end = runnum * 512 + 513;
        int poscount = runnum * 512 + 1;
        while (poscount < end) {
            if (Heap[poscount].getKey() == 0) {
                poscount++;
            }
            else {
                return false;
            }
        }
        if (poscount == end) {
            return true;
        }
        return false;
    }


    /**
     * finding the min value of x number of runs loaded, removing and return the
     * record
     * what if run is empty
     * 
     * @param runstosearch
     * @return
     */
    public Record runsMin(int runstosearch) {
        int[] recordlocs = new int[runstosearch];
        Record[] records = new Record[runstosearch];
        int first = -1;
        int i = 0;
        // building location of records and records to compare
        while (i < runstosearch) {
            if (!this.isRunEmpty(i)) {
                if(first == -1) {
                    first = i;
                }
                recordlocs[i] = this.runCurrPos(i);
                records[i] = Heap[this.runCurrPos(i)];
            }
            else {
                recordlocs[i] = -1;
            }
            i++;
        }
        //finding minimum
        double minval = records[first].getKey();
        int minpos = first;
        i = 0;
        while (i < runstosearch) {
            if (recordlocs[i] != -1) {
                if(Double.compare(minval, Heap[recordlocs[i]].getKey())>0) {
                    minval = records[i].getKey();
                    minpos = i;
                }
            }
            i++;
        }
        if(minpos != -1) {
            size--;
            Record temp = Heap[recordlocs[minpos]];
            Heap[recordlocs[minpos]] = new Record(0, 0);
            return temp;
        }
        else {
            return null;
        }       
    }


    /**
     * current position in the run
     * 
     * @param run
     * @return
     */
    public int runCurrPos(int run) {
        int end = run * 512 + 513;
        int poscount = run * 512 + 1;
        while (poscount < end) {
            if (Heap[poscount].getKey() != 0) {
                return poscount;
            }
            poscount++;
        }
        if (poscount == end) {
            return run * 512 + 1;
        }
        return -1;

    }


    // runs stuff

    public boolean specialInsert(Record element) {
        if (specialinsert > size && specialinsert > 0) {
            // Record temp = Heap[specialinsert];
            Heap[specialinsert] = element;
            realsize++;
            specialinsert--;
            return true;
        }
        else {
            return false;
        }

    }
    
    // used to reorder the heap, removes record as specific position
    public Record removeSpecial(int pos) {
        Record popped = Heap[pos];  
        Heap[pos] = new Record(0, 0);
        realsize--;
        //minHeapify(FRONT);
        return popped;
    }

     //used when reordering the array
    public boolean insertReorder(Record element) {
        if (size < maxsize) {
            Heap[++size] = element;
            int current = size;
            realsize++;
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
    public Record[] mainHeap() {
        return Heap;
    }


    public int getSpecialInsert() {
        return specialinsert;
    }


    public boolean isFull() {
        if (size == maxsize) {
            return true;
        }
        else {
            return false;
        }
    }


    public int size() {
        return size;
    }


    public int maxsize() {
        return maxsize;
    }


    public void setSpecialInsert(int x) {
        specialinsert = x;
    }


    public void setRealSize(int x) {
        realsize = x;
    }
    


    public int realsize() {
        return realsize;
    }

}
