import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
public class LexicalAnalysis {

    /**
    * @param args
    */
    static int i;
    static FileReader fr;
    static BufferedReader br;
    static String s;
    static String keyItems[];
    static String OpItems[];
    private final static String[] keywords = { "abstract", "boolean", "byte", "case",
                                    "catch", "char", "class", "continue", "default", "do", "double",
                                    "else", "extends", "final", "finally", "float", "for", "if",
                                    "implements", "import", "instanceof", "int", "interface", "long",
                                    "native", "new", "package", "private", "protected", "public",
                                    "return", "short", "static", "super", "switch", "synchronized",
                                    "this", "throw", "throws", "transient", "try", "void", "volatile",
                                    "while", "false", "true", "null" };
    
    static List<String> keyList = Arrays.asList(keywords);
    static List<String> filteredWords = new ArrayList<>();
    static Set<String> keyWordsFromFile = new HashSet<>();
    static List<String> filteredOp = new ArrayList<>();
    static Set<String> opFromFiles = new HashSet<>();
    static List<String> identifiersFromFile = new ArrayList<>();
    
    static String keyLexar[];
    static String opLexar[];
    
    public LexicalAnalysis(String filePath) throws FileNotFoundException, IOException {
        
        filterWordsFromFile(filePath);
        keyWordsGenerator();
        filterOperatorsFromFile(filePath);
        opGenerator();
        identifierGenerator(filePath);
    }

    public static void main(String[] args) throws IOException {
        LexicalAnalysis lexicalAnalysis = new LexicalAnalysis("/Users/jayantsingh/Desktop/Minor Project/Lexical Analyser/src/NewClass.java");
    }

    public static void filterWordsFromFile(String filePath) throws FileNotFoundException, IOException{
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        while((s=br.readLine())!=null){
          keyItems = s.split("[\\s;(){}\\.\\[\\]\"\"]");
          filteredWords.addAll(Arrays.asList(keyItems));
        }
        
        filteredWords.removeAll(Arrays.asList(null, ""));
        
        keyLexar = filteredWords.toArray(new String[filteredWords.size()]);
        
    }
    
    public static void keyWordsGenerator(){
        int count=0;
        for(String str : keyLexar){
            count = keyList.stream().filter((a) -> (str.equals(a))).map((String _item) -> {
                keyWordsFromFile.add(str);
                return _item;
            }).map((_item) -> 1).reduce(count, Integer::sum);
        }
        System.out.println("Keywords Are : - \n");
        String[] keyWords = keyWordsFromFile.toArray(new String[keyWordsFromFile.size()]);
        for(i=0; i<keyWordsFromFile.size(); i++){
            System.out.println("\t"+keyWords[i]);
        }
    }
    
    public static void filterOperatorsFromFile(String filePath) throws FileNotFoundException, IOException{
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        while((s=br.readLine())!=null){
            OpItems = s.split("[a-zA-Z\\s\\.\\[\\]\"0-9\\t\\n;,/**{}()]");
            filteredOp.addAll(Arrays.asList(OpItems));
        }
        filteredOp.removeAll(Arrays.asList(null, ""));
        //System.out.print(filteredOp);
    }
    
    public static void opGenerator(){
        List<String> op;
        String[] opWord = {"+", "-", "=", "\\", "/", "%", "*", "++", "--", "~", "!", ">>", "<<", ">>>", "<", ">", "<=", ">=", "==", "!=",
                            "&", "^", "|", "&&", "||", "?", ":", "+=", "-=", "*=", "/="};
        op = Arrays.asList(opWord);
        filteredOp.forEach((t) -> {
            op.forEach((t1) -> {
                if(t.equals(t1)){
                    opFromFiles.add(t);
                }
            });
        });
        System.out.println("\nOperators Are : - \n");
        opFromFiles.forEach((str) -> {
            System.out.println("\t"+str);
        });
    }
    
    public static void identifierGenerator(String filePath) throws FileNotFoundException, IOException{
        fr = new FileReader(filePath);
        br = new BufferedReader(fr);
        List<String> idfList = new ArrayList<>();
        String idt[];
        while((s=br.readLine())!=null){
            idt = s.split("[\\s0-9$-/:-?{-~!\"^_`\\[\\]]");
            idfList.addAll(Arrays.asList(idt));
        }
        idfList.removeAll(Arrays.asList(null, ""));
        idfList.removeAll(keyWordsFromFile);
        Set<String> idf = new HashSet<>();
        idf.addAll(idfList);
        System.out.println("\nIdentifiers Are : - \n");
        idf.forEach((str) -> {
            System.out.println("\t"+str);
        });
    }
}