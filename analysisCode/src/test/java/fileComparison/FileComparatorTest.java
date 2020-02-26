package fileComparison;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileComparatorTest {

    @Test
    public void detailedComparisonTest() throws IOException {
        FileComparator fc = new FileComparator(new File("/Users/neil/Google Drive/Teaching/Sheffield/Reengineering/2019-20/Examples/weka-3.8/weka/src/main/java/weka/gui/explorer/ClustererPanel.java"),new File("/Users/neil/Google Drive/Teaching/Sheffield/Reengineering/2019-20/Examples/weka-3.8/weka/src/main/java/weka/gui/explorer/ClassifierPanel.java"));
        boolean[][] comparison = fc.detailedCompare();
        //fc.printRelations(comparison,new File("output.csv"));
        fc.comparisonBitmap(comparison,new File("output.png"));
    }

}