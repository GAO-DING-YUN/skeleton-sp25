package ngrams;

import net.sf.saxon.expr.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private final Map<String, TimeSeries> wordCounts;
    private final TimeSeries totalCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordCounts = new HashMap<>();
        totalCounts = new TimeSeries();

        parseCountsFile(countsFilename);
        parseWordsFile(wordsFilename);
    }

    private void parseCountsFile(String countsFilename) {
        try (BufferedReader br = new BufferedReader(new FileReader(countsFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 2) continue; // 至少需要年份和总计数两个字段

                try {
                    int year = Integer.parseInt(parts[0]);
                    double count = Double.parseDouble(parts[1]);
                    totalCounts.put(year, count);
                } catch (NumberFormatException e) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("解析计数文件失败: " + countsFilename, e);
        }
    }

    private void parseWordsFile(String wordsFilename) {
        try (BufferedReader br = new BufferedReader(new FileReader(wordsFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue; // 至少需要单词、年份、计数三个字段

                try {
                    String word = parts[0];
                    int year = Integer.parseInt(parts[1]);
                    double count = Double.parseDouble(parts[2]);

                    wordCounts.computeIfAbsent(word, k -> new TimeSeries())
                            .put(year, count);
                } catch (NumberFormatException e) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("解析单词文件失败: " + wordsFilename, e);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries oringinal = wordCounts.getOrDefault(word, new TimeSeries());
        return oringinal;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCounts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordData = countHistory(word, startYear, endYear);
        if (wordData.isEmpty()) {
            return new TimeSeries();
        }

        TimeSeries totalData = new TimeSeries(totalCounts, startYear, endYear);
        return wordData.dividedBy(totalData);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            sum = sum.plus(weightHistory(word, startYear, endYear));
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}
