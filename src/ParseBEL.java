import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParseBEL {
    Set<String> relationKeyWord;
    Map <String, String> relmap; //relation mapping
    String [] arguments;
    String [] transResults;

    public ParseBEL() {
	arguments = new String[2];
	transResults = new String [3];
	relmap = new HashMap <String, String> ();
	relationKeyWord = new HashSet<String>(Arrays.asList(
		" decreases ", "-|",
		" directlyDecreases ", "=|", 
		" increases ", "->",
		" directlyIncreases ", "=>", 
		" causesNoChange ", "cnc", 
		" regulates ", " reg ", 
		" rateLimitingStepOf "));
	initRelMap();
    }

    
    public void initRelMap () { //can make it load some config file to initialize
	relmap.put("-|", "decreases");
	relmap.put(" decreases ", "decreases");
	relmap.put("=|", "directly decreases");
	relmap.put(" directlyDecreases ", "directly decreases");
	relmap.put("->", "increases");
	relmap.put(" increases ", "increases");
	relmap.put("=>", "directly increases");
	relmap.put(" directlyIncreases ", "directly increases");
	relmap.put(" causeNoChange ", "cause no change");
	relmap.put(" cnc ", "cause no change");
	relmap.put(" regulates ", "regulates");
	relmap.put(" reg ", "regulates");
	relmap.put(" rateLimitingStepOf ", "is a rate-limiting step for");
    }
    
    
    
    public void parseFile(String fn) {
	try {
	    BufferedReader br = new BufferedReader(new FileReader(fn));
	    String line = "";
	    while ((line = br.readLine()) != null) {
		while (line.contains("\\")) {
		    line = line.replace("\\", "");
		    line += br.readLine();
		}
		line = removeComment(line);
		if (isRelation(line)) { //is a relation statement 
		    System.out.println("Original statement: " + line);
		    String left = Utility.parseFunc(arguments[0]);
		    String right = Utility.parseFunc(arguments[1]);
		    transResults[0] = left;
		    transResults[2] = right;
		    System.out.println("Translation result:");// + left + " " + transResults[1] + " " + right);
		    System.out.println(left);
		    System.out.println(transResults[1]);
		    System.out.println(right);
		    System.out.println("=================");
		} else if (isComplex(line) || line.contains("tloc") || line.contains("translocation")) {
		    String out = Utility.parseFunc(line);
		    System.out.println("Original statement: " + line);
		    System.out.println("Transaltion result: ");// + out);
		    System.out.println(out);
		    System.out.println("=================");
		} 
	    }

	    br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    public boolean isComplex(String line) {
	
	return line.contains("complex");
    }
    
    public boolean isRelation(String line) {
	for (String entryStr : relationKeyWord) {
	    if (line.contains(entryStr)) {
		// parse arguments here
		parseArgs(line, entryStr);
		transResults[1] = relmap.get(entryStr);
		return true;
	    }
	}
	
	return false;
    }

    public void parseArgs(String line, String word) {
	int pos = line.indexOf(word);
	int len = word.length();
	String left = line.substring(0, pos).trim();
	String right = line.substring(pos + len).trim();
	arguments[0] = left;
	arguments[1] = right;
    }


    public String removeComment(String line) {
	int pos = line.indexOf("//");
	return pos > 0 ? line.substring(0, pos) : line;
    }

    public static void main(String[] args) {
	ParseBEL p = new ParseBEL();
	p.parseFile(args[0]);
    }
}
