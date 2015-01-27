import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/** 
 * @author Yu-Hsin Kuo
 * Language Technologies Institutes, 
 * Carnegie Mellon Univeristy
 * 
 * This is a translator that translate a BEL models to the text form. 
 * We mostly follow the functions defined in V2.0.
 */

public class ParseBEL {
    
    Set<String> orderKwSet;
    Set<String> nOrderKwSet;
    
    Map <String, String> orderKwMap; //relation mapping
    Map <String, String> nOrderKwMap;
    
    String [] arguments; 
    String relKw;

    public ParseBEL() {
	new Utility().configFunMap();;
	arguments = new String[2];
	
	orderKwMap = new HashMap <String, String> ();
	nOrderKwMap = new HashMap <String, String> ();
	initRelation();
    }

    /*
     * initialize the relationships
     */
    public void initRelation () {
	orderKwSet = new HashSet<String>(Arrays.asList(
		" decreases ", "-|",
		" directlyDecreases ", "=|", 
		" increases ", "->",
		" directlyIncreases ", "=>", 
		" causesNoChange ", "cnc", 
		" regulates ", " reg ", 
		" rateLimitingStepOf ", 
		"transcribedTo", ":>",
		"translatedTo", ">>",
		"hasMember", "hasMembers", 
		"hasComponent", "hasComponents", 
		"isA", "subProcessOf", "analogous", "analogousTo",  
		"biomarkerFor", "prognosticBiomarkerFor"));
	
	orderKwMap.put("-|", " decreases ");
	orderKwMap.put("decreases", " decreases ");
	orderKwMap.put("=|", " directly decreases ");
	orderKwMap.put("directlyDecreases", " directly decreases ");
	orderKwMap.put("->", " increases ");
	orderKwMap.put("increases", " increases ");
	orderKwMap.put("=>", " directly increases ");
	orderKwMap.put("directlyIncreases", " directly increases ");
	orderKwMap.put("causeNoChange", " cause no change ");
	orderKwMap.put("cnc", " cause no change ");
	orderKwMap.put("regulates", " regulates ");
	orderKwMap.put("reg", " regulates ");
	orderKwMap.put("rateLimitingStepOf", " is a rate-limiting step for ");
	orderKwMap.put("transcribedTo", " is transcribed to ");
	orderKwMap.put("translatededTo", " is translated to ");
	orderKwMap.put("hasMember", " has member ");
	orderKwMap.put("hasMembers", " has members ");
	orderKwMap.put("hasComponent", " has component ");
	orderKwMap.put("hasComponents", " has components ");
	orderKwMap.put("isA", " is a ");
	orderKwMap.put("subProcessOf", " is sub-process of ");
	orderKwMap.put("analogous", " is analogous to ");
	orderKwMap.put("analogousTo", " is analogous to ");
	orderKwMap.put("biomarkerFor", " is a biomarker for ");
	orderKwMap.put("prognosticBiomarkerFor", " is a prognostic biomarker for ");
	
	
	nOrderKwSet = new HashSet <String> (Arrays.asList(
		"orthologous", 
		"negativeCorrelation", "neg", 
		"positiveCorrelation", "pos", 
		"association", "--"));
	
	nOrderKwMap.put("orthologous", " are orthologous");
	nOrderKwMap.put("negativeCorrelation", " are negative correlated");
	nOrderKwMap.put("neg", " are negative correlated");
	nOrderKwMap.put("positiveCorrelation", " are positive correlated");
	nOrderKwMap.put("pos", " are positive correlated");
	nOrderKwMap.put("association", " are associated");
	nOrderKwMap.put("--", " are associated");
    }
    
    
    
    public void parseFile(String fn) {
	try {
	    BufferedReader br = new BufferedReader(new FileReader(fn));
	    String line = "";
	    while ((line = br.readLine()) != null) {
		while (line.contains("\\")) { 
		    // contains a new-line symbol
		    line = line.replace("\\", "");
		    line += br.readLine();
		}
		line = removeComment(line);
		
		
		if (isRelation(line, orderKwSet, orderKwMap)) { 
		    // is a relation (with order) statement
		    
		    String left = Utility.parseFunc(arguments[0]);
		    String right = Utility.parseFunc(arguments[1]);
		    
		    System.out.println("Original statement: " + line);
		    System.out.println("Translation result:" + left + relKw  + right);
		    System.out.println();

		} else if (isRelation(line, nOrderKwSet, nOrderKwMap)) {
		    // is a relation (non-order) statement
		    String left = Utility.parseFunc(arguments[0]);
		    String right = Utility.parseFunc(arguments[1]);
		    
		    System.out.println("Original statement: " + line);
		    System.out.println("Translation result:" + left + " and " + right + relKw);
		    System.out.println();		    
		} else if (isSingle(line)) {
		    String out = Utility.parseFunc(line);
		    
		    System.out.println("Original statement: " + line);
		    System.out.println("Transaltion result: " + out);
		    System.out.println();		   
		} 
	    }

	    br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    public boolean isSingle(String line) {
	return line.contains("complex") || line.contains("tloc") || line.contains("translocation") ;
    }
    
    
    public boolean isRelation(String line, Set <String> set, Map<String, String> map) {
	for (String entryStr : set) {
	    if (line.contains(entryStr)) {
		// parse arguments here
		parseArgs(line, entryStr);
		relKw = map.get(entryStr);
		return true;
	    }
	}
	return false;
    }

    
    
    /**
     * update the argument array
     * @param line the original statement
     * @param word the relationships keyword
     */
    public void parseArgs(String line, String word) {
	int pos = line.indexOf(word);
	int len = word.length();
	String left = line.substring(0, pos).trim();
	String right = line.substring(pos + len).trim();
	arguments[0] = left;
	arguments[1] = right;
    }

    
    /**
     * remove the comment part in the statement
     * @param line
     * @return a statement without comment
     */
    public String removeComment(String line) {
	int pos = line.indexOf("#"); //find the position of "#"
	return pos > 0 ? line.substring(0, pos) : line;
    }

    public static void main(String[] args) {
	ParseBEL p = new ParseBEL();
	p.parseFile(args[0]);
    }
}
