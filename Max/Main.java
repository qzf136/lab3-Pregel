package Max;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Pregel.Graph;
import Pregel.Master;
import Pregel.Read;
import Pregel.Vertex;
import Pregel.Worker;

public class Main {

//	int[] data = {2,1,0,1};
	
	public Graph<Integer> createGraph(String pathname) throws Exception {
		
		Read.readFile(pathname);
		
		Graph<Integer> graph = new Graph<Integer>();
		Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
		Map<Vertex<Integer>, Set<Vertex<Integer>>> dstMap = new HashMap<Vertex<Integer>, Set<Vertex<Integer>>>();
		Map<Vertex<Integer>, Set<Vertex<Integer>>> srcMap = new HashMap<Vertex<Integer>, Set<Vertex<Integer>>>();
		Map<Integer, Vertex<Integer>> int2Vertex = new HashMap<Integer, Vertex<Integer>>();
		Set<Integer> verticesInt = Read.vertices;
		for (int i : verticesInt) {
			Vertex<Integer> vertex = new Max_Vertex(i);
			vertex.setVertexValue(new Random().nextInt(100));
			vertices.add(vertex);
			int2Vertex.put(i, vertex);
		}
		Map<Integer, Set<Integer>> dstMapInt = Read.dstMap;
		Set<Integer> keys = dstMapInt.keySet();
		for (int i : keys) {
			Vertex<Integer> v1 = graph.int2Vertex.get(i);
			Set<Integer> dstSetInt = dstMapInt.get(i);
			Set<Vertex<Integer>> dstSet = new HashSet<Vertex<Integer>>();
			for (int j : dstSetInt) {
				Vertex<Integer> v2 = graph.int2Vertex.get(j);
				dstSet.add(v2);
			}
			dstMap.put(v1, dstSet);
		}
		Map<Integer, Set<Integer>> srcMapInt = Read.srcMap;
		Set<Integer> srckeys = srcMapInt.keySet();
		for (int i : srckeys) {
			Vertex<Integer> v1 = graph.int2Vertex.get(i);
			Set<Integer> srcSetInt = srcMapInt.get(i);
			Set<Vertex<Integer>> srcSet = new HashSet<Vertex<Integer>>();
			for (int j : srcSetInt) {
				Vertex<Integer> v2 = graph.int2Vertex.get(j);
				srcSet.add(v2);
			}
			dstMap.put(v1, srcSet);
		}
		graph.vertices = vertices;
		graph.srcMap = srcMap;
		graph.dstMap = dstMap;
		graph.int2Vertex = int2Vertex;
		return graph;
	}
	
	public void run(String pathname) throws Exception {
		Graph<Integer> graph = createGraph(pathname);
		Master<Integer> master = new Master<Integer>();
		master.graph = graph;
		for (Vertex<Integer> vertex : graph.vertices) {
			vertex.master = master;
		}
		for (int i = 0; i < master.workerNum; i++) {
			Worker<Integer> worker = new Worker<Integer>();
			worker.setWorkerID(i);
			worker.setGraph(graph);
			worker.setMaster(master);
			master.addWorker(worker);
		}
		master.splitGraph();
		master.run();
	}
	
	public static void main(String[] args) throws Exception {
		Main max = new Main();
		max.run("src/web-Google.txt");
//		max.run("src/data.txt");
	}
}
