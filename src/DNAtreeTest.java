
import java.io.FileNotFoundException;
import student.TestCase;

/**
 * Tests the simple methods of DNAtree
 * 
 * @author wesley and connor
 * @version 1.0 - using git so the versions are stored there
 *
 */
public class DNAtreeTest extends TestCase {

    private DNATreeOps dnaTree;


    /**
     * sets up the initial conditions
     */
    public void setUp() throws FileNotFoundException {
        DNAtree dnaMain = new DNAtree();
        dnaMain.main(new String[] { "P2sampleinput.txt" });
        dnaTree = dnaMain.getDNAtree();
        dnaMain.search("AAAA");

    }


    /**
     * tests very basic methods
     */
    public void testMethods() {
        assertFalse(dnaTree.root.isFly());
        assertTrue(dnaTree.root.isInternal());

    }
}
