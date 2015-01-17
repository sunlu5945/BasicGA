import org.evolsoft.ccSaNSDE.CCFrame;
import org.evolsoft.functions.Function_1;

public class Test {

	public static void main(String[] args) {
		CCFrame test = new CCFrame(new Function_1());
		test.CCRun(1000, 50 , 10000000, 1);
	}

}
