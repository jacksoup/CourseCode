package fileComparison;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class DuplicateDetectorTest {

    @Test
    public void testCompareFiles() throws IOException {
        DuplicateDetector dd = new DuplicateDetector(new File("/Users/neil/Google Drive/Teaching/Sheffield/Reengineering/2019-20/Examples/weka-3.8/weka/src/main/java"), "java");
        dd.fileComparison(new File("output.csv"));
    }

}