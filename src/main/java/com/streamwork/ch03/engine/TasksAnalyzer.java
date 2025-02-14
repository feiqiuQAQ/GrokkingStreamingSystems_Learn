package com.streamwork.ch03.engine;

import com.streamwork.ch03.common.Task;
import org.checkerframework.checker.units.qual.Length;

import java.util.*;

public class TasksAnalyzer {

    class Node extends HashMap<String, String> {
        public Node(String name, int parallelism, String address) {
            super();

            this.put("name", name);
            this.put("parallelism", String.valueOf(parallelism));
            this.put("address", address);
        }
    }

    class Edge extends HashMap<String, String> {
        public Edge(TasksAnalyzer.Node from, TasksAnalyzer.Node to) {
            super();

            this.put("from", from.get("name"));
            this.put("to", to.get("name"));
            this.put("from_parallelism", from.get("parallelism"));
            this.put("to_parallelism", to.get("parallelism"));
            this.put("from_address", from.get("address"));
            this.put("to_address", to.get("address"));
        }
    }

//    public BidiMap<String, Integer> bidiMap = new DualHashBidiMap<>();

    private final String jobName;
    private final List<TasksAnalyzer.Node> sources = new ArrayList<TasksAnalyzer.Node>();
    private final List<TasksAnalyzer.Node> operators = new ArrayList<TasksAnalyzer.Node>();
    private final List<TasksAnalyzer.Edge> edges = new ArrayList<TasksAnalyzer.Edge>();

    private final List<Task> taskList = new ArrayList<Task>();

    private final List<String> know_hosts = new ArrayList<String>(List.of("127.0.0.1", "127.0.0.2"));
    private final Set<String> allocatedAddresses = new TreeSet<>();

    private int count = 0;

    public TasksAnalyzer(final String jobName, final List<Connection> connectionList) {
        this.jobName = jobName;
        Map<TasksAnalyzer.Node, Integer> incomingCountMap = new HashMap<TasksAnalyzer.Node, Integer>();
        for (Connection connection: connectionList) {
            TasksAnalyzer.Node from = new TasksAnalyzer.Node(connection.from.getComponent().getName(), connection.from.getComponent().getParallelism(), allocateAddress());
            TasksAnalyzer.Node to = new TasksAnalyzer.Node(connection.to.getComponent().getName(), connection.to.getComponent().getParallelism(), allocateAddress());

            Integer count = incomingCountMap.getOrDefault(to, 0);
            incomingCountMap.put(from, count);
            count = incomingCountMap.getOrDefault(to, 0);
            incomingCountMap.put(to, count + 1);

            edges.add(new TasksAnalyzer.Edge(from, to));
        }
        for (TasksAnalyzer.Node node: incomingCountMap.keySet()) {
            if (incomingCountMap.get(node) == 0) {
                sources.add(node);
            } else {
                operators.add(node);
            }
        }


    }

    private String allocateAddress() {
        String address;
        String ip;
        String port;

        do {
            if (count >= know_hosts.size()) {
                count = 0;
            }
            count++;
            ip = know_hosts.get(count - 1);
            port = String.valueOf((int) ((Math.random() * (15000 - 14000)) + 14000));
            address = ip + ":" + port;
        } while (allocatedAddresses.contains(address));

        return address;
    }

}
