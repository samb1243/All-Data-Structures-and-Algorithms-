import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graphs {

	public static class Graph {
		private final Map adj = new HashMap(); // key: String -> value: List of neighbors
		private final boolean directed;

		public Graph(boolean directed) {
			this.directed = directed;
		}

		public void addVertex(String v) {
			if (!adj.containsKey(v)) adj.put(v, new ArrayList());
		}

		public void addEdge(String u, String v) {
			addVertex(u);
			addVertex(v);
			List neighborsU = (List) adj.get(u);
			neighborsU.add(v);
			if (!directed) {
				List neighborsV = (List) adj.get(v);
				neighborsV.add(u);
			}
		}

		public boolean hasVertex(String v) {
			return adj.containsKey(v);
		}

		public boolean hasEdge(String u, String v) {
			if (!adj.containsKey(u)) return false;
			List neighborsU = (List) adj.get(u);
			return neighborsU.contains(v);
		}

		public List bfs(String start) {
			List order = new ArrayList();
			if (!adj.containsKey(start)) return order;
			Queue q = new LinkedList();
			Set seen = new HashSet();
			q.add(start);
			seen.add(start);
			while (!q.isEmpty()) {
				String cur = (String) q.remove();
				order.add(cur);
				List nbrs = (List) adj.get(cur);
				Iterator it = nbrs.iterator();
				while (it.hasNext()) {
					String nbr = (String) it.next();
					if (!seen.contains(nbr)) {
						seen.add(nbr);
						q.add(nbr);
					}
				}
			}
			return order;
		}

		public List dfs(String start) {
			List order = new ArrayList();
			if (!adj.containsKey(start)) return order;
			Set seen = new HashSet();
			dfsHelper(start, seen, order);
			return order;
		}

		private void dfsHelper(String node, Set seen, List order) {
			seen.add(node);
			order.add(node);
			List nbrs = (List) adj.get(node);
			Iterator it = nbrs.iterator();
			while (it.hasNext()) {
				String nbr = (String) it.next();
				if (!seen.contains(nbr)) dfsHelper(nbr, seen, order);
			}
		}

		// Iterative DFS using a stack. Returns visitation order.
		public List dfsIterative(String start) {
			List order = new ArrayList();
			if (!adj.containsKey(start)) return order;
			Set seen = new HashSet();
			Stack stack = new Stack();
			stack.push(start);
			while (!stack.isEmpty()) {
				String cur = (String) stack.pop();
				if (seen.contains(cur)) continue;
				seen.add(cur);
				order.add(cur);
				List nbrs = (List) adj.get(cur);
				// push neighbors in reverse to visit in natural order
				for (int i = nbrs.size() - 1; i >= 0; i--) {
					String nbr = (String) nbrs.get(i);
					if (!seen.contains(nbr)) stack.push(nbr);
				}
			}
			return order;
		}

		// Breadth-first search (shortest path in unweighted graph). Returns empty list if no path.
		public List breadthFirstSearch(String start, String goal) {
			List path = new ArrayList();
			if (!adj.containsKey(start) || !adj.containsKey(goal)) return path;
			Queue q = new LinkedList();
			Map parent = new HashMap();
			Set seen = new HashSet();
			q.add(start);
			seen.add(start);
			parent.put(start, null);
			boolean found = false;
			while (!q.isEmpty()) {
				String cur = (String) q.remove();
				if (cur.equals(goal)) { found = true; break; }
				List nbrs = (List) adj.get(cur);
				Iterator it = nbrs.iterator();
				while (it.hasNext()) {
					String nbr = (String) it.next();
					if (!seen.contains(nbr)) {
						seen.add(nbr);
						parent.put(nbr, cur);
						q.add(nbr);
					}
				}
			}
			if (!found) return path;
			String at = goal;
			while (at != null) {
				path.add(at);
				at = (String) parent.get(at);
			}
			Collections.reverse(path);
			return path;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			Iterator it = adj.keySet().iterator();
			while (it.hasNext()) {
				String k = (String) it.next();
				List nbrs = (List) adj.get(k);
				sb.append(k).append(" -> ").append(nbrs).append('\n');
			}
			return sb.toString();
		}
	}

	// Small demo to show usage. Run this class to try the Graph.
	public static void main(String[] args) {
		Graph g = new Graph(false);
		g.addEdge("A", "B");
		g.addEdge("A", "C");
		g.addEdge("B", "D");
		g.addEdge("C", "E");
		g.addEdge("E", "D");

		System.out.println("Adjacency list:");
		System.out.println(g);

		System.out.println("BFS from A: " + g.bfs("A"));
		System.out.println("DFS from A: " + g.dfs("A"));
		System.out.println("Shortest path A -> D: " + g.breadthFirstSearch("A", "D"));
	}

}
