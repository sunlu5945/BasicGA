import org.evolsoft.ccSaNSDE.CCFrame;
import org.evolsoft.functions.*;

public class Test {

	public static void main(String[] args) {
		Function function = new Function_4();
		CCFrame test = new CCFrame(function);
//		test.CCRun(Integer.parseInt(args[0]), Integer.parseInt(args[1]) , Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		test.grouping(100);
		test.showAllGroups("test");
	}

}
