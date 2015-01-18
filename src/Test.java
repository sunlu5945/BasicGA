import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.RandomMatrices;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
//		CCFrame test = new CCFrame(new Function_1());
//		test.CCRun(Integer.parseInt(args[0]), Integer.parseInt(args[1]) , Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		DenseMatrix64F M = RandomMatrices.createOrthogonal(2, 2, new Random());
		M.print();
	}

}
