import java.util.HashMap;
import java.util.Map;


public class Utility {

    static Map <String, String> typeMap;
    static Map <String, String> codeMap;
    
    //initialization
    static {
	typeMap = new HashMap <String, String>();
	codeMap = new HashMap <String, String>();
	
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
    
    public static String proAbundance (String argWrd) {
	int index = argWrd.indexOf(",");
		
	String tran = "protein";
	
	if (index <= 0) {
	    tran += " " + argWrd;
	} else {
	    tran += " " + parseFunc(argWrd.substring(0, index)) + " " + parseFunc(argWrd.substring(index + 1).trim());
	} 
	return tran;
    }
    
    public static String abundance (String argWrd) {
	//return "abundance of " + parseFunc(argWrd);
	return parseFunc(argWrd);
    }
    
    public static String geneAbun (String argWrd) {
	return "gene abundacne of " + parseFunc(argWrd);
    }
    
    public static String microRNA (String argWrd) {
	return "microRNA abundacne of " + parseFunc(argWrd);
    }
    
    public static String rnaAbun (String argWrd) {
	return "RNA abundacne of " + parseFunc(argWrd);
    }
    
    public static String path (String argWrd) {
	return "pathology process of " + argWrd;
    }
    
    public static String biopro (String argWrd) {
	return "biological process of " + argWrd;
    }
    
    public static String variant (String argWrd) { //incomplete TODO
	return "variant" + " " + argWrd.substring(argWrd.indexOf(".") + 1);
    }
    
    public static String pmod (String argWrd) {
	String [] strArr = argWrd.split(",");
	if (strArr.length == 1) {
	    return "with unspecified " + typeMap.get(strArr[0]);
	} else if (strArr.length == 2) {
	    String type = typeMap.get(strArr[0].trim()) == null? typeMap.get(strArr[1].trim()) : typeMap.get(strArr[0].trim());
	    if (typeMap.get(strArr[0].trim()) == null && codeMap.get(strArr[0].trim()) == null ||  
		typeMap.get(strArr[1].trim()) == null && codeMap.get(strArr[1].trim()) == null) {
		return typeMap.get(strArr[0].trim()) == null ? "with " + type + " at " + strArr[0].trim() : "with " + type + " at " + strArr[1].trim();
	    } else {
		return "with " + type + " at an unspecified " + codeMap.get(strArr[1].trim());
	    }
	} else {
	    
	    return "with " + typeMap.get(strArr[2].trim()) + " at " + codeMap.get(strArr[1].trim()) + " " + strArr[0].trim();
	}
    }
    
    public static String complex (String argWrd) { //might incur some bugs if there are some unexpected commas
	
	String [] strArr = argWrd.split(",");
	String ans = "(";
	for (int i = 0; i < strArr.length; i++) {
	    ans += parseFunc(strArr[i].trim()) + ", ";
	}
	ans = ans.substring(0, ans.length() - 2);
	ans += ") complex";
	return ans;
    }
    
    public static String activity (String argWrd) {
	int index = argWrd.indexOf(",");
	if (index <= 0) {
	    return "activity state of " + parseFunc(argWrd.trim());
	} else {
	    return parseFunc(argWrd.substring(index + 1).trim()) + " " + parseFunc(argWrd.substring(0, index));  
	}
	
    }
    
    public static String molActi (String argWrd) {
	if (argWrd.equals("tscript") || argWrd.equals("transcriptionalActivity"))
	    return "transcriptional activity state of";
	else if (argWrd.equals("kin") || argWrd.equals("kinaseActivity")) 
	    return "kinase activity state of";
	else if (argWrd.equals("gtp") || argWrd.equals("gtpBoundActivity"))
	    return "gtp bound activity state of";
	else if (argWrd.equals("cat") || argWrd.equals("catalyticActivity"))
	    return "catalytic activity state of";
	else if (argWrd.equals("chap") || argWrd.equals("chaperoneActivity"))
	    return "chaperone activity state of";
	else if (argWrd.equals("pep") || argWrd.equals("peptidaseActivity"))
	    return "peptidase activity state of";
	else if (argWrd.equals("phos") || argWrd.equals("phosphataseActivity"))
	    return "phosphatase activity state of";
	else if (argWrd.equals("ribo") || argWrd.equals("ribosylationActivity"))
	    return "ribosylation activity state of";
	else if (argWrd.equals("tport") || argWrd.equals("transportActivity"))
	    return "transporter activity state of";
	else 
	    return "ERROR!";
    }
    
    public static String tloc (String argWrd) {
	String [] strArr = argWrd.split(",");
	if (strArr.length != 3) System.err.println ("ERROR in tloc");
	String str1 = strArr[1].trim().length() == 0? "unknown place" : strArr[1].trim();
	String str2 = strArr[2].trim().length() == 0? "unknown place" : strArr[2].trim();
	return parseFunc(strArr[0]) + " translocates from " + str1 + " to " + str2;
    }
    
    /*
     * 
     * 
     * 
     * 
     */
    public static String parseFunc (String word) {
	int pos = word.indexOf("(");
	if (pos <= 0) return word;
	
	String funWrd = word.substring(0, pos);
	String argWrd = word.substring(pos + 1, word.length() - 1);
	String transWrd = "";
	
	if (funWrd.equals("p") || funWrd.equals("proteinAbundance")) {
	    transWrd = proAbundance(argWrd);
	} else if (funWrd.equals("var") || funWrd.equals("variant")) {
	    transWrd = variant(argWrd);
	} else if (funWrd.equals("pmod") || funWrd.equals("proteinModification")) {
	    transWrd = pmod(argWrd);
	} else if (funWrd.equals("complex") || funWrd.equals("complexAbundance")) {
	    transWrd = complex(argWrd);
	} else if (funWrd.equals("activity") || funWrd.equals("act")) {
	    transWrd = activity(argWrd);
	} else if (funWrd.equals("ma") || funWrd.equals("molecularActivity")) {
	    transWrd = molActi(argWrd);
	} else if (funWrd.equals("a") || funWrd.equals("abundance")) {
	    transWrd = abundance(argWrd);
	} else if (funWrd.equals("g") || funWrd.equals("geneAbundance")) {
	    transWrd = geneAbun(argWrd);
	} else if (funWrd.equals("m") || funWrd.equals("microRNAAbundance")) {
	    transWrd = microRNA(argWrd);
	} else if (funWrd.equals("r") || funWrd.equals("rnaAbundance")) {
	    transWrd = rnaAbun(argWrd);
	} else if (funWrd.equals("path") || funWrd.equals("pathology")) {
	    transWrd = path (argWrd);
	} else if (funWrd.equals("bp") || funWrd.equals("biologicalProcess")) {
	    transWrd = biopro (argWrd);
	} else if (funWrd.equals("translocation") || funWrd.equals("tloc")) {
	    transWrd = tloc (argWrd);
	} else if (funWrd.equals("compositeAbundance") || funWrd.equals("composite")) {
	    //TODO
	}
	
	
	return transWrd;
    }
}
