package main;
import java.io.*;
import  java.util.*;

public class WordNet {
    private final DirectedGraph synsetGraph;
    private final Map<Integer, Set<String>> idToWords;
    private final Map<String, Set<Integer>> wordToIds;

    public WordNet(String synsetsPath, String hyponymsPath) throws IOException {
        this.synsetGraph = new DirectedGraph();
        idToWords = new HashMap<>();
        wordToIds = new HashMap<>();

        parseSynetsFile(synsetsPath);
        parseHyponysFile(hyponymsPath);
    }

    public void parseSynetsFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                int synsetId = Integer.parseInt(parts[0]);
                synsetGraph.addNode(synsetId);

                String[] words = parts[1].split(" ");
                Set<String> wordSet = new HashSet<>(Arrays.asList(words));
                idToWords.put(synsetId, wordSet);

                for (String word : words) {
                    wordToIds.putIfAbsent(word, new HashSet<>());
                    wordToIds.get(word).add(synsetId);
                }
            }
        }
    }

    public void parseHyponysFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int hypernymId = Integer.parseInt(parts[0]);

                for (int i = 1; i < parts.length; i++) {
                    int hyponymId = Integer.parseInt(parts[i]);
                    synsetGraph.addEdge(hypernymId, hyponymId);
                }
            }
        }
    }

    public List<String> getHyponyms(String word) {
        Set<Integer> synsetIds = wordToIds.getOrDefault(word, Collections.emptySet());
        if (synsetIds.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Integer> allHyponymIds = new HashSet<>();
        for (int synsetId : synsetIds) {
            allHyponymIds.addAll(synsetGraph.getAllReachableNodes(synsetId));
        }

        Set<String> hyponymWords = new HashSet<>();
        for (int id : allHyponymIds) {
            hyponymWords.addAll(idToWords.getOrDefault(id, Collections.emptySet()));
        }

        List<String> sortedHyponyms = new ArrayList<>(hyponymWords);
        Collections.sort(sortedHyponyms);
        return sortedHyponyms;
    }
}
