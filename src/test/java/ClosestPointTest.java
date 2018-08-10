import com.company.model.Point;
import com.company.service.impl.ClosestPoint;
import com.company.service.impl.ClosestPointPreSorted;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by adere on 10/08/2018.
 */
public class ClosestPointTest {

    public static Point p1;
    public static Point p2;

    public static String fileName;
    public static String notFoundFile = "notfound.txt" ;

    @BeforeClass
    public static void setup() {

        fileName = "/Users/alidere/closestPoint/sample_inputs/test_input.txt";

        p1 = new Point();
        List<Double> coordinates1 = new ArrayList<>();
        coordinates1.add(2.2);
        coordinates1.add(1.0);
        p1.setCoordinates(coordinates1);
        p1.setOriginalIndex(5);

        p2 = new Point();
        List<Double> coordinates2 = new ArrayList<>();
        coordinates2.add(2.8);
        coordinates2.add(1.0);
        p2.setCoordinates(coordinates2);
        p2.setOriginalIndex(4);
    }


    @Test
    public void testFindClosestPair() {

        ClosestPoint closestPoint = new ClosestPoint();
        closestPoint.setFILENAME(fileName);
        closestPoint.perform();

        assert (closestPoint.getClosestPair()[0] != null);
        assert (closestPoint.getClosestPair()[1] != null);
        assert (closestPoint.getClosestPair()[0].equals(p1));
        assert (closestPoint.getClosestPair()[1].equals(p2));

    }


    @Test
    public void testFindClosestPairPreSorted() {

        ClosestPointPreSorted closestPoint = new ClosestPointPreSorted();
        closestPoint.setFILENAME(fileName);
        closestPoint.perform();

        assert (closestPoint.getClosestPair()[0] != null);
        assert (closestPoint.getClosestPair()[1] != null);
        assert (closestPoint.getClosestPair()[0].equals(p1));
        assert (closestPoint.getClosestPair()[1].equals(p2));

    }


    @Test
    public void testWhenFileNotFound() {
        ClosestPoint closestPoint = new ClosestPoint();
        closestPoint.setFILENAME(notFoundFile);
        closestPoint.perform();


        assert (closestPoint.getClosestPair()[0] == null);
        assert (closestPoint.getClosestPair()[1] == null);
    }



}
