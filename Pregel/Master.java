package Pregel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Master<V> {

	public Graph<V> graph = null;
	public int workerNum = 5;
	public int superStep = -1;
	public List<Worker<V>> workers = new ArrayList<Worker<V>>();
	public Queue<Message<Worker<V>>> workerMessages = new LinkedList<Message<Worker<V>>>();
	
	public void addWorker(Worker<V> worker) {
		this.workers.add(worker);
	}
	
	public void addWorkerMessage(Message<Worker<V>> msg) {
		this.workerMessages.add(msg);
	}
	
	public void splitGraph() {
		Set<Vertex<V>> vertices = graph.vertices;
		for(Vertex<V> vertex : vertices) {
			int v = vertex.vertexID;
			int workerID = v % workerNum;
			Worker<V> worker = workers.get(workerID);
			worker.addVertex(vertex);
			vertex.worker = worker;
		}
	}
	
	public boolean allVerticesInactive() {
		for (Worker<V> worker : workers) {
			if (worker.allInactive() == false) {
				return false;
			}
		}
		return true;
	}
	
	public boolean allWorkersEnd() {
		if (workerMessages.size() == workerNum) return true;
		else return false;
	}
	
	public void suprtStep() {
		superStep++;
		workerMessages.clear();
		for (Worker<V> worker : workers) {
			for (Vertex<V> vertex : worker.vertices) {
				vertex.clearMsg();
			}
		}
		for (Worker<V> worker : workers) {
			worker.work();
		}
	}
	
	public void run() {
		
		suprtStep();
		for (int j = 0; j < 4; j++) {
			System.out.print(graph.int2Vertex.get(j).vertexValue + "\t");
			System.out.print(graph.int2Vertex.get(j).thisSuperStepMessages + "\t");
			System.out.println(graph.int2Vertex.get(j).isActive());
		}
		System.out.println();
		System.out.println();
		while (allWorkersEnd() && !allVerticesInactive()) {
			suprtStep();
			for (int j = 0; j < 4; j++) {
				System.out.print(graph.int2Vertex.get(j).vertexValue + "\t");
				System.out.print(graph.int2Vertex.get(j).thisSuperStepMessages + "\t");
				System.out.println(graph.int2Vertex.get(j).isActive());
			}
			System.out.println();
			System.out.println();
		}
	}	
}
