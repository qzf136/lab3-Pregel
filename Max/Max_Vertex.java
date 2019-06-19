package Max;

import java.util.Queue;
import java.util.Set;

import Pregel.Graph;
import Pregel.Message;
import Pregel.Read;
import Pregel.Vertex;

public class Max_Vertex extends Vertex<Integer>{
	
	public Max_Vertex(int id) {
		super(id);
	}

	@Override
	public void changeValue() {
		// TODO Auto-generated method stub
		Queue<Message<Vertex<Integer>>> lastMsg = lastSuperStepMessages;
		while (!lastMsg.isEmpty()) {
			Message<Vertex<Integer>> msg = lastMsg.poll();
			int val = Integer.parseInt(msg.info);
			if (val > vertexValue) {
				setVertexValue(val);
			}
		}
	}

	@Override
	public void compute(Graph<Integer> graph) {
		// TODO Auto-generated method stub
		if (!lastSuperStepMessages.isEmpty()) {
			int lastVal = vertexValue;
			changeValue();
			int newVal = vertexValue;
			if (lastVal == newVal) {
				changeToInactive();				
			} else {
				changeToActive();
			}
		} else {
			if (master.superStep != 0)
				changeToInactive();
		}
		if (isActive()) {
			Set<Integer> out = Read.dstMap.get(vertexID);
			for (int vertexInt : out) {
				Vertex<Integer> vertex = graph.int2Vertex.get(vertexInt);
				sendMessage(vertex, vertexValue+"");
			}
		}
		
	}

}
	
