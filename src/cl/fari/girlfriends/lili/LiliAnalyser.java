package cl.fari.girlfriends.lili;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Andrés Farías on 17-08-17.
 */
public class LiliAnalyser {

    public static void main(String[] args) throws IOException, ParseException {

        File liliFile = new File("/Users/andres/Dropbox/Lili/Whatsapp/_chat.txt");
        FileReader fileReader = new FileReader(liliFile);
        BufferedReader br = new BufferedReader(fileReader);
        String line;

        /* Se lee cada linea del archivo */
        int i = 0;
        int lili = 0, andres = 0;
        Map<String, Integer> dateHash = new HashMap<>();
        Map<String, Integer> wordCounter = new HashMap<>();

        while ((line = br.readLine()) != null) {
            System.out.println(++i + " " + line);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YY");
            if (isNewLine(line)) {
                // Perday Message counter
                String parsedDate = line.substring(0, 8);
                if (!dateHash.containsKey(parsedDate)) {
                    dateHash.put(parsedDate, 0);
                }
                dateHash.put(parsedDate, dateHash.get(parsedDate) + 1);

                // Message Counter
                if (line.contains(" Lili Linda: ")) {
                    lili++;
                } else {
                    andres++;
                }
            }

            // Word counter
            StringTokenizer wordsIter;
            if (isNewLine(line)) {
                String substring;
                try {
                    substring = line.substring(19);
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println(e);
                    continue;
                }
                wordsIter = new StringTokenizer(substring);
            } else {
                wordsIter = new StringTokenizer(line);
            }
            while (wordsIter.hasMoreElements()) {
                String word = wordsIter.nextToken();
                if (!wordCounter.containsKey(word)) {
                    wordCounter.put(word, 0);
                }
                wordCounter.put(word, wordCounter.get(word) + 1);
            }

        }

        System.out.println("Lili messages: " + lili);
        System.out.println("Andres messages: " + andres);

        System.out.println("MESSAGES BY DATE\n\n: " + dateHash);

        System.out.println("\n\nWORDS\n\n: " + dateHash);
        printMap(wordCounter);

    }

    public static void printMap(Map<String, Integer> map) {
        int i = 0;
        while (i < 50) {
            Pair<String, Integer> champion = getChampion(map);
            String key = champion.getKey();
            if (champion.getKey().length() > 3) {
                System.out.println(key + " | " + champion.getValue());
                i++;
            }
            map.remove(key);
        }
    }

    private static Pair<String, Integer> getChampion(Map<String, Integer> map) {

        Pair<String, Integer> champion = new Pair<>("Perdedor", 0);
        for (String k : map.keySet()) {
            Integer v = map.get(k);

            if (v > champion.getValue()) {
                champion = new Pair<String, Integer>(k, v);
            }
        }

        return champion;
    }

    private static boolean isNewLine(String line) {
        if (line.length() < 9) return false;

        String date = line.substring(0, 8);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YY");

        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

}
