package main;

import java.util.*;

public class DirectedGraph {
    private final Map<Integer, List<Integer>> adjacencyList;

    public DirectedGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addNode(int Node) {
        adjacencyList.putIfAbsent(Node, new ArrayList<>());
    }

    public void addEdge(int fromNode, int toNode) {
        if (!adjacencyList.containsKey(toNode) || !adjacencyList.containsKey(fromNode)) {
            throw new IllegalArgumentException("两节点有一个不存在");
        }
        List<Integer> nextNode = adjacencyList.get(fromNode);
        if (!nextNode.contains(toNode)) {
            nextNode.add(toNode);
        }
    }

    public List<Integer> getNeighbors(int node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public Set<Integer> getAllReachableNodes(int startNode) {
        Set<Integer> AllSet = new HashSet<>();
        if (!adjacencyList.containsKey(startNode)) {
            return AllSet;
        }
        AllSet.add(startNode);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int node : getNeighbors(current)) {
                if (!AllSet.contains(node)) {
                    queue.add(node);
                    AllSet.add(node);
                }
            }
        }
        return AllSet;
    }
}
