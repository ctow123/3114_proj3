
/**
 * This makes a Minheap which stores and has operations for Record objects.
 * This also contains operations for merging runs as the heap has to be used not
 * just for building the runs but also merging them.
 * 
 * This implementation has a blank record object at position 0 in the array. so
 * [1] is the top of the minHeap
 * 
 * specialinsert, is for storing values in the heap that are not part of
 * building the current run
 * 
 * records with key of 0 will be considered null, or not counted in the heap
 * size
 * 
 * @version 1.0 - using git so the versions are stored there
 * @author connor
 *
 */

public class MinHeapRecord {
    private Record[] heap;
    private int size;
    private int maxsize;
    private int specialinsert;
    private int realsize;
    private static final int FRONT = 1;


    /**
     * constructor for the heap
     * 
     * @param maxsize
     *            maxsize of the heap
     */
    public MinHeapRecord(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        this.realsize = 0;
        this.specialinsert = maxsize;
        heap = new Record[this.maxsize + 1];
        heap[0] = new Record(-10, -10.0);
    }


    /**
     * 
     * @param pos
     *            the position in the minheap
     * @return the array position of the parent node
     */
    public int parent(int pos) {
        return pos / 2;
    }


    /**
     * 
     * @param pos
     *            the position in the minheap
     * @return the array position of the left child
     */
    public int leftChild(int pos) {
        return (2 * pos);
    }


    /**
     * 
     * @param pos
     *            the position in the minheap
     * @return the array position of the right child
     */
    public int rightChild(int pos) {
        return (2 * pos) + 1;
    }


    /**
     * 
     * @param pos
     *            record position in the minheap
     * @return true or false whether the record is a LeafNode or not
     */
    public boolean isLeaf(int pos) {
        return !(size >= rightChild(pos) || size >= leftChild(pos));
    }


    /**
     * swaps two records in the heap
     * 
     * @param firstpos
     *            position of the first record
     * @param secondpos
     *            position of the second record
     */
    private void swap(int firstpos, int secondpos) {
        Record tmp;
        tmp = heap[firstpos];
        heap[firstpos] = heap[secondpos];
        heap[secondpos] = tmp;
    }


    /**
     * creates a minheap by minifying (making sure that all child are greater
     * than the record at specified position) the record at the specified
     * position
     * 
     * @param pos
     *            the position you want to minify
     */
    private void minifyHeap(int pos) {

        // node is non leaf and has two children
        if (!isLeaf(pos)) {
            if (heap[leftChild(pos)].getKey() != 0 && heap[rightChild(pos)]
                .getKey() != 0) {
                if (heap[pos].getKey() > heap[leftChild(pos)].getKey()
                    || heap[pos].getKey() > heap[rightChild(pos)].getKey()) {

                    // Swap with the left child, and minify left
                    if (heap[leftChild(pos)].getKey() < heap[rightChild(pos)]
                        .getKey()) {
                        swap(pos, leftChild(pos));
                        minifyHeap(leftChild(pos));
                    }

                    // Swap with the right child and minify right
                    else {
                        swap(pos, rightChild(pos));
                        minifyHeap(rightChild(pos));
                    }
                }
            }
            // only has left child
            else if (heap[leftChild(pos)].getKey() != 0) {
                if (heap[pos].getKey() > heap[leftChild(pos)].getKey()) {
                    swap(pos, leftChild(pos));
                    minifyHeap(leftChild(pos));

                }
            }
            // only has right child
            else {
                if (heap[pos].getKey() > heap[rightChild(pos)].getKey()) {
                    swap(pos, rightChild(pos));
                    minifyHeap(rightChild(pos));
                }
            }
        }
    }


    /**
     * inserts record into the minheap
     * 
     * @param element
     *            Record you want to insert
     * @return true if insert is successful, false otherwise
     */
    public boolean insert(Record element) {
        if (size < maxsize && size < specialinsert) {
            heap[++size] = element;
            int current = size;
            realsize++;
            while (heap[current].getKey() < heap[parent(current)].getKey()) {
                swap(current, parent(current));
                current = parent(current);
            }
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * removes the minimum record from the minheap, replaces the minimum with
     * the largest value in the heap and then resorts it
     * 
     * @return the top of the minheap (record at array pos 1)
     */
    public Record remove() {
        Record popped = heap[FRONT];
        heap[FRONT] = heap[size];
        heap[size] = new Record(0, 0);
        size--;
        realsize--;
        minifyHeap(FRONT);
        return popped;
    }


    /**
     * 
     * @return the record at the top of the minheap (the minimum record, array
     *         position 1)
     */
    public Record peek() {
        if (size > 0) {
            return heap[FRONT];
        }
        else {
            return null;
        }
    }

    // MERGE AND BUILD RUNS CODE


    // Merge operations -------------------------
    /**
     * inserting element for merging location is location of the run
     * 
     * @param element
     *            the record to insert
     * @param location
     *            location in heap to insert at
     * @return true on success
     */
    public boolean insertRegular(Record element, int location) {
        if (size < maxsize) {
            int insertloc = (512 * location) + 1;
            int insertlocpos = this.findEmptySpot(insertloc);
            heap[insertlocpos] = element;
            size++;
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * find the next empty spot to insert a record
     * 
     * @param startpos
     *            position in the heap array you want to start looking
     * @return int of the next empty spot
     */
    public int findEmptySpot(int startpos) {
        int end = startpos + 512;
        int poscount = startpos;
        while (poscount < end) {
            if (heap[poscount] == null || heap[poscount].getKey() == 0) {
                return poscount;
            }
            poscount++;
        }
        if (poscount == end) {
            return -1;
        }
        return -1;

    }


    /**
     * valid runs values are 0-7, checks if the run is empty.
     * 
     * @param runnum
     *            the run you want to check (0-7)
     * @return true if the run is empty
     */
    public boolean isRunEmpty(int runnum) {
        int end = runnum * 512 + 513;
        int poscount = runnum * 512 + 1;
        while (poscount < end) {
            if (heap[poscount] == null || heap[poscount].getKey() == 0) {
                poscount++;
            }
            else {
                return false;
            }
        }
        return (poscount == end);
    }


    /**
     * finding the min value of x number of runs loaded, removing and return the
     * record
     * what if run is empty
     * 
     * @param runstosearch
     *            how many runs you need to compare values from 1 - 8
     * @return the minimum record of the front of all the runs compared
     */
    public Record runsMin(int runstosearch) {
        int[] recordlocs = new int[runstosearch];
        Record[] records = new Record[runstosearch];
        int first = -1;
        int i = 0;
        // building location of records and records to compare
        while (i < runstosearch) {
            if (!this.isRunEmpty(i)) {
                if (first == -1) {
                    first = i;
                }
                recordlocs[i] = this.runCurrPos(i);
                records[i] = heap[this.runCurrPos(i)];
            }
            else {
                recordlocs[i] = -1;
            }
            i++;
        }
        // finding minimum
        double minval = records[first].getKey();
        int minpos = first;
        i = 0;
        while (i < runstosearch) {
            if (recordlocs[i] != -1 && Double.compare(minval,
                heap[recordlocs[i]].getKey()) > 0) {

                minval = records[i].getKey();
                minpos = i;

            }
            i++;
        }
        if (minpos != -1) {
            size--;
            Record temp = heap[recordlocs[minpos]];
            heap[recordlocs[minpos]] = new Record(0, 0);
            return temp;
        }
        else {
            return null;
        }
    }


    /**
     * current position in the run, the first key that isnt zero
     * 
     * @param run
     *            the run values 0-7 you want the current position for
     * @return the integer value of current position
     */
    public int runCurrPos(int run) {
        int end = run * 512 + 513;
        int poscount = run * 512 + 1;
        while (poscount < end) {
            if (heap[poscount].getKey() != 0) {
                return poscount;
            }
            poscount++;
        }
        if (poscount == end) {
            return run * 512 + 1;
        }
        return -1;

    }


    // Building runs operations ---------

    /**
     * used to insert record when can be put into the buffer, stores it in the
     * heap, but is not part of the tree
     * 
     * @param element
     *            the record to be inserted
     * @return true if successful
     */
    public boolean specialInsert(Record element) {
        if (specialinsert > size && specialinsert > 0) {
            // Record temp = heap[specialinsert];
            heap[specialinsert] = element;
            realsize++;
            specialinsert--;
            return true;
        }
        else {
            return false;
        }

    }


    /**
     * used in reordering the heap, removes a record at a position of your
     * choice
     * 
     * @param pos
     *            position to remove record
     * @return the removed record
     */
    public Record removeSpecial(int pos) {
        Record popped = heap[pos];
        heap[pos] = new Record(0, 0);
        realsize--;
        return popped;
    }


    /**
     * used when reordering the heap with all the special inserts that are
     * stored but not part of the tree
     * 
     * @param element
     *            record you want to insert
     * @return true if successful, false otherwise
     */
    public boolean insertReorder(Record element) {
        if (size < maxsize) {
            heap[++size] = element;
            int current = size;
            realsize++;
            while (heap[current].getKey() < heap[parent(current)].getKey()) {
                swap(current, parent(current));
                current = parent(current);
            }
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * 
     * @return the heap object
     */
    public Record[] mainHeap() {
        return heap;
    }


    /**
     * 
     * @return the location of special insert
     */
    public int getSpecialInsert() {
        return specialinsert;
    }


    /**
     * 
     * @return true if the heap is full, false otherwise
     */
    public boolean isFull() {
        return (size == maxsize);
    }


    /**
     * 
     * @return the size of the heap
     */
    public int size() {
        return size;
    }


    /**
     * 
     * @return the maxsize field
     */
    public int maxsize() {
        return maxsize;
    }


    /**
     * 
     * @param x
     *            sets the specialinsert location in the heap
     */
    public void setSpecialInsert(int x) {
        specialinsert = x;
    }


    /**
     * 
     * @param x
     *            realsize of the heap
     */
    public void setRealSize(int x) {
        realsize = x;
    }


    /**
     * 
     * @return the realsize of the heap
     */
    public int realsize() {
        return realsize;
    }

}
