package Pregel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Read {

	public static Set<Integer> vertices = new HashSet<Integer>();
	public static Map<Integer, Set<Integer>> dstMap = new HashMap<Integer, Set<Integer>>();
	public static Map<Integer, Set<Integer>> srcMap = new HashMap<Integer, Set<Integer>>();
	
	public static void readFile(String pathname) throws IOException {
		File file = new File(pathname);
		InputStream in = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader bReader = new BufferedReader(reader);
		String line = bReader.readLine();
		while (line != null) {
			if (!line.contains("#")) {
				String[] tokens = line.split("\t");
				int start = Integer.parseInt(tokens[0]);
				int end = Integer.parseInt(tokens[1]);
				vertices.add(start);
				vertices.add(end);
				if (!dstMap.containsKey(start))	dstMap.put(start, new HashSet<Integer>());
				if (!dstMap.containsKey(end))	dstMap.put(end, new HashSet<Integer>());
				if (!srcMap.containsKey(start))	srcMap.put(start, new HashSet<Integer>());
				if (!srcMap.containsKey(end))	srcMap.put(end, new HashSet<Integer>());
				
				Set<Integer> dst = dstMap.get(start);
				dst.add(end);
				dstMap.put(start, dst);
				
				Set<Integer> src = srcMap.get(end);
				src.add(start);
				srcMap.put(end, src);
			}
			line = bReader.readLine();
		}
		bReader.close();
	}
	
}
