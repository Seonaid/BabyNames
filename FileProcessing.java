import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Reads a collection of US babynames data from 1880 - 2014 and processes to find trends
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FileProcessing {
    public void printFileSummary(FileResource fr){
        int totalNames = 0;
        int totalBirths = 0;
        int totalMaleNames = 0;
        int totalFemaleNames = 0;
        for (CSVRecord record: fr.getCSVParser(false)){
            if (record.get(1).equals("F")) {
                totalFemaleNames += 1;
            } else {
                totalMaleNames += 1;
            }
            totalBirths += Integer.parseInt(record.get(2));
        }
        totalNames = totalMaleNames + totalFemaleNames;
        System.out.println("Total births: " + totalBirths);
        System.out.println("Total Female Names: " + totalFemaleNames);
        System.out.println("Total Male Names: " + totalMaleNames);
        System.out.println("Total Names: " + totalNames);        
    }
    
    public void testPrintFileSummary(){
        FileResource fr = new FileResource();
        printFileSummary(fr);
    }
    
    public int getRank(int year, String name, String gender){
        System.out.println("Year: " + year);
        System.out.println("Looking for "  + gender + " name: " + name);
        int totalMaleNames = 0;
        int totalFemaleNames = 0;
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        printFileSummary(fr);
        for (CSVRecord record: fr.getCSVParser(false)){
            
            if (gender.equals("F")) {
                if (record.get(1).equals("F")){
                totalFemaleNames += 1;
                    if (record.get(0).equals(name)){
                        return totalFemaleNames;
                    }
                }
            } else {
                if (record.get(1).equals("M")) {
                totalMaleNames += 1;
                    if (record.get(0).equals(name)){
                        return totalMaleNames;
                    }
                }                
            }
        }
        // if name is not found, return -1 for index
        return -1;
    }
    
    public void testGetRank(){
        System.out.println("\nStart testGetRank tests...");
        // expected output rank is 82
        if (getRank(1900, "Ellen", "F") != 82) System.out.println("Error finding F name 1990");
        // System.out.println("Ellen in 1990: " + getRank(1900, "Ellen", "F"));
        
        if (getRank(1900, "John", "M") != 1) System.out.println("Error finding M name 1990");
        
        // Case: name that is not found returns -1
        if (getRank(2010, "Seonaid", "F") != -1) System.out.println("Error looking for missing F name");
        System.out.println("All testGetRank tests run.");
    }
    
    public String getName(int year, int rank, String gender){
        System.out.println("Year: " + year);
        System.out.println("Looking for "  + gender + " number: " + rank);
        int totalMaleNames = 0;
        int totalFemaleNames = 0;
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        printFileSummary(fr);
        for (CSVRecord record: fr.getCSVParser(false)){
            
            if (gender.equals("F")) {
                if (record.get(1).equals("F")){
                totalFemaleNames += 1;
                    if (totalFemaleNames == rank){
                        return record.get(0);
                    }
                }
            } else {
                if (record.get(1).equals("M")) {
                totalMaleNames += 1;
                    if (totalMaleNames == rank){
                        return record.get(0);
                    }
                }                
            }
        }
        return "NO NAME FOUND";
    }
    
    public void testGetName(){
        System.out.println("\nStart testGetName tests...");
        
        // expected name is Ellen
        if (!getName(1900, 82, "F").equals("Ellen")) System.out.println("Error finding F name 1990");
        // System.out.println("Ellen in 1990: " + getRank(1900, "Ellen", "F"));
        
        if (!getName(1900, 1, "M").equals("John")) System.out.println("Error finding M name 1990");
        
        // Case: name that is not found returns -1
        if (getName(2010, 30000, "F") != "NO NAME FOUND") System.out.println("Error looking for missing F name");
        System.out.println("All testGetName tests run.");    
    }
}
