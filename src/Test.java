import org.evolsoft.component.Code;
import org.evolsoft.component.Encode;
import org.evolsoft.component.Individual;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Individual individual = new Individual();
		Code code = new Code();
		code = Encode.createIntPriorityCode(5);
		individual.addSection(code);
		Individual temp = individual.clone();
		individual.showIndivdual("individual");
		temp.showIndivdual("temp");
		temp.getSection(0).addGene(3);
		temp.showIndivdual("Changed temp");
		individual.showIndivdual("after individual");
	}

}
