package Pregel;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Vertex<V> {
	
	public Master<V> master;
	public Worker<V> worker;
	public int vertexID;
	public V vertexValue = null;
	public boolean active = true;
	public Queue<Message<Vertex<V>>> lastSuperStepMessages = new LinkedList<Message<Vertex<V>>>();
	public Queue<Message<Vertex<V>>> thisSuperStepMessages = new LinkedList<Message<Vertex<V>>>();
	
	public Vertex(int id) {
		vertexID = id;
		active = true;
	}
	
	public void setVertexValue(V vertexValue) {
		this.vertexValue = vertexValue;
	}
	
	public boolean isActive() {
		return active;
	}

	public void changeToActive() {
		active = true;
	}
	
	public void changeToInactive() {
		active = false;
	}
	
	public void addThisMsg(Message<Vertex<V>> msg) {
		thisSuperStepMessages.add(msg);
	}
	
	public void clearMsg() {
		lastSuperStepMessages = thisSuperStepMessages;
		thisSuperStepMessages = new LinkedList<>();
	}
	
	public void sendMessage(Vertex<V> dst, String meaaageinfo) {
		Message<Vertex<V>> msg = new Message<Vertex<V>>(this, dst, meaaageinfo);
		dst.addThisMsg(msg);
	}
	
	public abstract void changeValue();
	
	public abstract void compute(Graph<V> graph);
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this.vertexID == ((Vertex<V>)obj).vertexID) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return String.valueOf(vertexID).hashCode();
	}
	
	@Override
	public String toString() {
		return vertexID + ":" + vertexValue;
	}
	
}
