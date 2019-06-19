package Pregel;

import java.util.HashSet;
import java.util.Set;

public class Worker<V> {

	public int workerID;
	public Master<V> master = null;
	public Graph<V> graph = null;
	public Set<Vertex<V>> vertices = new HashSet<Vertex<V>>();
	
	public void setWorkerID(int workerID) {
		this.workerID = workerID;
	}

	public void setMaster(Master<V> master) {
		this.master = master;
	}
	
	public void setGraph(Graph<V> graph) {
		this.graph = graph;
	}
	
	public void addVertex(Vertex<V> vertex) {
		vertices.add(vertex);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (workerID == ((Worker<V>)obj).workerID) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(workerID).hashCode();
	}
	
	@Override
	public String toString() {
		return workerID+"";
	}
	
	public void sendMessageToMaster(String meaaageinfo) {
		Message<Worker<V>> msg = new Message<Worker<V>>(this, null, meaaageinfo);
		master.addWorkerMessage(msg);
	}
	
	public boolean allInactive() {
		for (Vertex<V> vertex : vertices) {
			if (vertex.isActive())	return false;
		}
		return true;
	}
	
	public void work() {
		for (Vertex<V> vertex : vertices) {
			if (vertex.isActive()) {
				vertex.compute(graph);
			}
		}
		sendMessageToMaster("worker end");
	}
}
