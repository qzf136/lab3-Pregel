package Pregel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<V> {

	public Set<Vertex<V>> vertices = new HashSet<Vertex<V>>();
	public Map<Vertex<V>, Set<Vertex<V>>> dstMap = new HashMap<Vertex<V>, Set<Vertex<V>>>();
	public Map<Vertex<V>, Set<Vertex<V>>> srcMap = new HashMap<Vertex<V>, Set<Vertex<V>>>();
	public Map<Integer, Vertex<V>> int2Vertex = new HashMap<Integer, Vertex<V>>();
	
	public void addint2Vertex(int i, Vertex<V> vertex) {
		int2Vertex.put(i, vertex);
	}
	
	public void addVertex(Vertex<V> vertex) {
		vertices.add(vertex);
	}
	
	public void addDst(Vertex<V> v1, Vertex<V> dst) {
		if (dstMap.containsKey(v1)) {
			Set<Vertex<V>> set = dstMap.get(v1);
			set.add(dst);
			dstMap.put(v1, set);
		} else {
			Set<Vertex<V>> set = new HashSet<Vertex<V>>();
			set.add(dst);
			dstMap.put(v1, set);
		}
	}
	
	public void addSrc(Vertex<V> v1, Vertex<V> src) {
		if (srcMap.containsKey(v1)) {
			Set<Vertex<V>> set = srcMap.get(v1);
			set.add(src);
			srcMap.put(v1, set);
		} else {
			Set<Vertex<V>> set = new HashSet<Vertex<V>>();
			set.add(src);
			srcMap.put(v1, set);
		}
	}
	
	public Set<Vertex<V>> outVertexSet(Vertex<V> vertex) {
		Set<Vertex<V>> dst = dstMap.get(vertex);
		return dst;
	}
	
}

