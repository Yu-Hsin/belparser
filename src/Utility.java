import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yu-Hsin Kuo Language Technologies Institutes, Carnegie Mellon
 *         Univeristy
 */

public class Utility {

    static Map<String, String> typeMap; // mapping of modification types
    static Map<String, String> codeMap; // mapping of Amino Acid codes
    static Map<String, String> actMap;
    static Map<String, Function> funMap;
    // initialization for map
    static {
	funMap = new HashMap<String, Function>();
	typeMap = new HashMap<String, String>();
	codeMap = new HashMap<String, String>();
	actMap = new HashMap<String, String>();

	actMap.put("tscript", "transcriptional activity state of");
	actMap.put("transcriptionalActivity",
		"transcriptional activity state of");
	actMap.put("kin", "kinase activity state of");
	actMap.put("kinaseActivity", "kinase activity state of");
	actMap.put("gtp", "gtp bound activity state of");
	actMap.put("gtpBoundActivity", "gtp bound activity state of");
	actMap.put("cat", "catalytic activity state of");
	actMap.put("catalyticActivity", "catalytic activity state of");
	actMap.put("chap", "chaperone activity state of");
	actMap.put("chaperoneActivity", "chaperone activity state of");
	actMap.put("pep", "peptidase activity state of");
	actMap.put("peptidaseActivity", "peptidase activity state of");
	actMap.put("phos", "phosphatase activity state of");
	actMap.put("phosphataseActivity", "phosphatase activity state of");
	actMap.put("ribo", "ribosylation activity state of");
	actMap.put("ribosylationActivity", "ribosylation activity state of");
	actMap.put("tport", "transporter activity state of");
	actMap.put("transportActivity", "transporter activity state of");

	typeMap.put("P", "phosphorylation");
	typeMap.put("A", "acetylation");
	typeMap.put("F", "farnesylation");
	typeMap.put("G", "glycosylation");
	typeMap.put("H", "hydroxylation");
	typeMap.put("M", "methylation");
	typeMap.put("R", "ribosylation");
	typeMap.put("S", "sumoylation");
	typeMap.put("U", "ubiquitination");

	codeMap.put("A", "Alanine");
	codeMap.put("R", "Arginine");
	codeMap.put("N", "Asparagine");
	codeMap.put("D", "Aspartic Acid");
	codeMap.put("C", "Cysteine");
	codeMap.put("E", "Glutamic Acid");
	codeMap.put("Q", "Glutamine");
	codeMap.put("G", "Glycine");
	codeMap.put("H", "Histidine");
	codeMap.put("I", "Isoleucine");
	codeMap.put("L", "Leucine");
	codeMap.put("K", "Lysine");
	codeMap.put("M", "Methionine");
	codeMap.put("F", "Phenylalanine");
	codeMap.put("P", "Proline");
	codeMap.put("S", "Serine");
	codeMap.put("T", "Threonine");
	codeMap.put("W", "Tryptophan");
	codeMap.put("Y", "Tyrosine");
	codeMap.put("V", "Valine");
    }

    /*
     * initialize the function map
     * register the functions
     */

    public void configFunMap() {
	// abundance functions
	funMap.put("a", new Abundance());
	funMap.put("abundance", new Abundance());
	funMap.put("complex", new ComplexAbundance());
	funMap.put("complexAbundance", new ComplexAbundance());
	funMap.put("composite", new CompositeAbundance());
	funMap.put("compositeAbundance", new CompositeAbundance());
	funMap.put("g", new GeneAbundance());
	funMap.put("geneAbundance", new GeneAbundance());
	funMap.put("m", new MicroRNAAbundance());
	funMap.put("microRNAAbundance", new MicroRNAAbundance());
	funMap.put("p", new ProteinAbundance());
	funMap.put("protein", new ProteinAbundance());
	funMap.put("r", new RNAAbundance());
	funMap.put("rnaAbundance", new RNAAbundance());

	// abundance modifier functions
	funMap.put("proteinModification", new ProteinModification());
	funMap.put("pmod", new ProteinModification());
	funMap.put("variant", new Variant());
	funMap.put("var", new Variant());
	funMap.put("fragment", new Fragment());
	funMap.put("frag", new Fragment());
	funMap.put("location", new Location());
	funMap.put("loc", new Location());

	// processes functions
	funMap.put("bp", new BiologicalProcess());
	funMap.put("biologicalProcess", new BiologicalProcess());
	funMap.put("path", new Pathology());
	funMap.put("pathology", new Pathology());
	funMap.put("act", new Activity());
	funMap.put("activity", new Activity());

	// process modifier function
	funMap.put("molecularActivity", new MolecularActivity());
	funMap.put("ma", new MolecularActivity());

	// transformations
	funMap.put("translocation", new Translocations());
	funMap.put("tloc", new Translocations());
	funMap.put("cellSecretion", new CellSecretion());
	funMap.put("sec", new CellSecretion());
	funMap.put("cellSurfaceExpression", new CellSurfaceExpression());
	funMap.put("surf", new CellSurfaceExpression());
	funMap.put("degradation", new Degradation());
	funMap.put("deg", new Degradation());
	funMap.put("reaction", new Reaction());
	funMap.put("rxn", new Reaction());
    }

    /*
     * classes of "abundance functions"
     */

    class Abundance implements Function {
	public String execute(String argWrd) {
	    return parseFunc(argWrd);
	}
    }

    class ComplexAbundance implements Function {
	// might have errors if there are some unexpected commas in the values
	public String execute(String argWrd) {
	    String[] strArr = argWrd.split(",");
	    String ans = "complex (";
	    for (int i = 0; i < strArr.length; i++) {
		ans += parseFunc(strArr[i].trim()) + ", ";
	    }
	    ans = ans.substring(0, ans.length() - 2);
	    ans += ")";
	    return ans;
	}
    }

    class CompositeAbundance implements Function {
	// similar to ComplexAbudance
	// might have errors if there are some unexpected commas in the values
	public String execute(String argWrd) {
	    String[] strArr = argWrd.split(",");
	    String ans = "composite (";
	    for (int i = 0; i < strArr.length; i++) {
		ans += parseFunc(strArr[i].trim()) + ", ";
	    }
	    ans = ans.substring(0, ans.length() - 2);
	    ans += ")";
	    return ans;
	}

    }

    class GeneAbundance implements Function {
	public String execute(String argWrd) {
	    return "gene abundacne of " + parseFunc(argWrd);
	}
    }

    class MicroRNAAbundance implements Function {
	public String execute(String argWrd) {
	    return "microRNA abundacne of " + parseFunc(argWrd);
	}
    }

    class ProteinAbundance implements Function {
	public String execute(String argWrd) {
	    int index = argWrd.indexOf(",");
	    String tran = "protein";
	    if (index <= 0) {
		tran += " " + argWrd;
	    } else {
		tran += " " + parseFunc(argWrd.substring(0, index)) + " "
			+ parseFunc(argWrd.substring(index + 1).trim());
	    }
	    return tran;
	}
    }

    class RNAAbundance implements Function {
	public String execute(String argWrd) {
	    return "RNA abundacne of " + parseFunc(argWrd);
	}
    }

    /*
     * classes of abundance modifier functions
     */
    class ProteinModification implements Function {
	public String execute(String argWrd) {
	    // should be either (position, code, type) or (type, code, position)
	    String[] strArr = argWrd.split(",");
	    if (strArr.length == 1) { // must be type
		return "with unspecified " + typeMap.get(strArr[0]);
	    } else if (strArr.length == 2) {
		String type = "";
		String code = "";
		if (typeMap.get(strArr[0].trim()) != null
			&& codeMap.get(strArr[1].trim()) != null) {
		    type = typeMap.get(strArr[0].trim());
		    code = codeMap.get(strArr[1].trim());
		} else {
		    type = typeMap.get(strArr[1].trim());
		    code = codeMap.get(strArr[0].trim());
		}
		return "with " + type + "at an unspecified " + code;

	    } else {
		String code = codeMap.get(strArr[1].trim());
		String type = typeMap.get(strArr[0].trim()) != null ? typeMap
			.get(strArr[0].trim()) : typeMap.get(strArr[2].trim());
		String pos = typeMap.get(strArr[0].trim()) != null ? strArr[2]
			: strArr[0];
		return "with " + type + " at " + code + " " + pos;
	    }

	}
    }

    class Variant implements Function {
	public String execute(String argWrd) {
	    return null;
	}
    }
    
    class Fragment implements Function {
	public String execute(String argWrd) {
	    String [] strArr = argWrd.split(",");
	    if (strArr.length == 1)
		return "fragment with range " + strArr[0];
	    else
		return "fragment with range " + strArr[0] + " descriptor " + strArr[1];
	}
    }
    
    class Location implements Function {
	public String execute (String argWrd) {
	    return null;
	}
    }

    /*
     * classes of process functions
     */

    class BiologicalProcess implements Function {
	public String execute(String argWrd) {
	    return "biological process of " + parseFunc(argWrd);
	}
    }

    class Pathology implements Function {
	public String execute(String argWrd) {
	    return "pathology process of " + parseFunc(argWrd);
	}
    }

    class Activity implements Function {
	public String execute(String argWrd) {
	    int index = argWrd.indexOf(",");
	    if (index <= 0) {
		return "activity state of " + parseFunc(argWrd.trim());
	    } else {
		return parseFunc(argWrd.substring(index + 1).trim()) + " "
			+ parseFunc(argWrd.substring(0, index));
	    }
	}
    }

    /*
     * classes of process modifier function
     */

    class MolecularActivity implements Function {
	public String execute(String argWrd) {
	    return actMap.get(argWrd);
	}
    }

    /*
     * classes of transformation functions
     */
    public List<String> parseTran(String argWrd) {
	List<String> ans = new ArrayList<String>();
	String[] strArr = argWrd.split(",");
	if (strArr.length != 3)
	    return null;
	ans.add(strArr[0]);
	String str1 = strArr[1].trim().length() == 0 ? "unknown place"
		: strArr[1].trim();
	ans.add(str1);
	String str2 = strArr[2].trim().length() == 0 ? "unknown place"
		: strArr[2].trim();
	ans.add(str2);
	return ans;
    }

    class Translocations implements Function {
	public String execute(String argWrd) {
	    List<String> strArr = parseTran(argWrd);
	    if (strArr == null)
		return null;
	    return parseFunc(strArr.get(0)) + " translocates from "
		    + strArr.get(1) + " to " + strArr.get(2);
	}
    }

    class CellSecretion implements Function {
	public String execute(String argWrd) {
	    List<String> strArr = parseTran(argWrd);
	    if (strArr == null)
		return null;
	    return parseFunc(strArr.get(0)) + " moves from " + strArr.get(1)
		    + "(intracellular) to " + strArr.get(2)
		    + " (extracellular)";
	}
    }

    class CellSurfaceExpression implements Function {
	public String execute(String argWrd) {
	    List<String> strArr = parseTran(argWrd);
	    if (strArr == null)
		return null;
	    return parseFunc(strArr.get(0)) + " moves from " + strArr.get(1)
		    + "(intracellular) to " + strArr.get(2) + " (cell surface)";
	}
    }

    class Degradation implements Function {
	public String execute(String argWrd) {
	    return "degradation of " + parseFunc(argWrd);
	}
    }

    class Reaction implements Function {
	public String execute(String argWrd) {
	    if (!(argWrd.contains("reactants") && argWrd.contains("products"))) // invalid
		return null;
	    int index = argWrd.indexOf("products");
	    String reactants = argWrd.substring(0, index - 1);
	    String products = argWrd.substring(index);
	    reactants = reactants.substring(reactants.indexOf("(") + 1,
		    reactants.length() - 1);
	    products = products.substring(products.indexOf("(") + 1,
		    products.length() - 1);
	    String[] strArr1 = reactants.split(",");
	    String[] strArr2 = products.split(",");
	    reactants = "";
	    products = "";
	    for (int i = 0; i < strArr1.length; i++)
		reactants += parseFunc(strArr1[i].trim()) + ", ";
	    for (int i = 0; i < strArr2.length; i++)
		products += parseFunc(strArr2[i].trim()) + ", ";
	    reactants = reactants.substring(0, reactants.length() - 2);
	    products = products.substring(0, products.length() - 2);

	    return "the reaction with reactants " + reactants
		    + " and products " + products;// TODO
	}
    }

    public static String variant(String argWrd) { // incomplete TODO
	return "variant" + " " + argWrd.substring(argWrd.indexOf(".") + 1);
    }

    public static String pmod(String argWrd) {
	String[] strArr = argWrd.split(",");
	if (strArr.length == 1) {
	    return "with unspecified " + typeMap.get(strArr[0]);
	} else if (strArr.length == 2) {
	    String type = typeMap.get(strArr[0].trim()) == null ? typeMap
		    .get(strArr[1].trim()) : typeMap.get(strArr[0].trim());
	    if (typeMap.get(strArr[0].trim()) == null
		    && codeMap.get(strArr[0].trim()) == null
		    || typeMap.get(strArr[1].trim()) == null
		    && codeMap.get(strArr[1].trim()) == null) {
		return typeMap.get(strArr[0].trim()) == null ? "with " + type
			+ " at " + strArr[0].trim() : "with " + type + " at "
			+ strArr[1].trim();
	    } else {
		return "with " + type + " at an unspecified "
			+ codeMap.get(strArr[1].trim());
	    }
	} else {

	    return "with " + typeMap.get(strArr[2].trim()) + " at "
		    + codeMap.get(strArr[1].trim()) + " " + strArr[0].trim();
	}
    }

    /*
     * parse function
     */
    public static String parseFunc(String word) {
	int pos = word.indexOf("(");
	if (pos <= 0) // function not found
	    return word;

	String funWrd = word.substring(0, pos);
	String argWrd = word.substring(pos + 1, word.length() - 1);
	String transWrd = "";

	Function func = funMap.get(funWrd);
	if (func == null)
	    System.err.println("Error: function not found: " + funWrd);
	else
	    transWrd = func.execute(argWrd);

	if (transWrd == null) // nothing is returned -> throw an error
	    System.err.println("Error in function: " + funWrd);

	return transWrd;
    }
}
