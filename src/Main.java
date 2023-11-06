

import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {

        // Setting logger and file handler to write files to web_traffic_report.txt
        logger.setLevel(Level.INFO);
        FileHandler handler = new FileHandler("src/web_traffic_report.txt");
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);


        //Opening arraylist to store each request in the form of string in arraylist as object
        ArrayList<String> request = new ArrayList<>();

        //using BufferedReader class to read access.txt file
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/access.txt")));


        //using while loop to assign each request to the arraylist named request;
        int numberOfRequests = 0; // number of requests
        String s;
        while ((s = reader.readLine()) != null) {
            request.add(s);
            numberOfRequests++;
        }
        //creating REGEX for validating IP address
        String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
        String ipRegex = "\\[" + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\]";
        //creating regex for date
        String dateRegex = "\\[\\d{4}\\-\\d{2}\\-\\d{2}";
        //creating regex for error code
        String codeRegex="\\d{3}";




        String[] request1 = request.get(0).split(" ");
        String[] request2 = request.get(1).split(" ");
        String[] request3 = request.get(2).split(" ");
        String[] request4 = request.get(3).split(" ");



        //creating arraylist to keep  address and adding ip addresses
        ArrayList<String> ipAddresses = new ArrayList<>();
        ipAddresses.add(request1[1]);
        ipAddresses.add(request2[1]);
        ipAddresses.add(request3[1]);
        ipAddresses.add(request4[1]);
        // Validating ip addresses
        int validips = 0;
        for (String ipAddress : ipAddresses) {
            if (Pattern.matches(ipRegex, ipAddress)) {
                System.out.println(ipAddress + " is valid ip");
                validips++;
            }
        }


        //creating arraylist to keep dates and adding dates
        ArrayList<String> dates = new ArrayList<>();
        dates.add(request1[4]);
        dates.add(request2[4]);
        dates.add(request3[4]);
        dates.add(request4[4]);
        for (String date : dates) {
            if(Pattern.matches(dateRegex, date)){
                System.out.println(date+" is valid date");
            }
        }
        //creating arraylist to keep codes and validateing AND COUNTING 404 CODES
        String check404="404";
        int counter404=0;

        ArrayList<String> Codes=new ArrayList<>();
        Codes.add(request1[8].substring(0, 3));
        Codes.add(request2[8].substring(0, 3));
        Codes.add(request3[8].substring(0, 3));
        Codes.add(request4[8].substring(0, 3));
        for (String code : Codes) {
            if(Pattern.matches(codeRegex, code)){
                System.out.println(code+" is valid");
                if(code.equals(check404))
                {
                    counter404++;
                }

            }
        }
        System.out.println(counter404);

        String report1="THE NUMBER OF REQUESTS IS: "+numberOfRequests;
        String report2="THE NUMBER OF VALID IPS IS "+validips;
        String report3="THE NUMBER OF CODES WITH 404 IS "+check404;

         //WRITING REPORT
        logger.log(Level.INFO,report1);
        logger.log(Level.INFO,report2);
        logger.log(Level.INFO,report3);




    }
}