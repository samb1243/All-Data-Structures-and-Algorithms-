import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Comparator;
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

		// Weighted graph supporting Prim's algorithm (MST)
		public static class WeightedGraph {
			private final Map adj = new HashMap(); // key: String -> List of Edge
			private final boolean directed;

			public static class Edge {
				public String to;
				public int weight;
				public Edge(String to, int weight) { this.to = to; this.weight = weight; }
				public String toString() { return to + "(" + weight + ")"; }
			}

			private static class EdgeWrapper {
				public String from;
				public String to;
				public int weight;
				public EdgeWrapper(String f, String t, int w) { from = f; to = t; weight = w; }
			}

			public WeightedGraph(boolean directed) { this.directed = directed; }

			public void addVertex(String v) { if (!adj.containsKey(v)) adj.put(v, new ArrayList()); }

			public void addEdge(String u, String v, int w) {
				addVertex(u);
				addVertex(v);
				List neighborsU = (List) adj.get(u);
				neighborsU.add(new Edge(v, w));
				if (!directed) {
					List neighborsV = (List) adj.get(v);
					neighborsV.add(new Edge(u, w));
				}
			}

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

			// Prim's algorithm for MST. Returns list of edges as strings "u - v : w" in the MST.
			public List prim(String start) {
				List mst = new ArrayList();
				if (!adj.containsKey(start)) return mst;
				Set visited = new HashSet();
				PriorityQueue pq = new PriorityQueue(new Comparator() {
					public int compare(Object a, Object b) {
						EdgeWrapper ea = (EdgeWrapper) a;
						EdgeWrapper eb = (EdgeWrapper) b;
						return ea.weight - eb.weight;
					}
				});

				visited.add(start);
				List startNbrs = (List) adj.get(start);
				Iterator it0 = startNbrs.iterator();
				while (it0.hasNext()) {
					Edge e = (Edge) it0.next();
					pq.add(new EdgeWrapper(start, e.to, e.weight));
				}

				while (!pq.isEmpty()) {
					EdgeWrapper ew = (EdgeWrapper) pq.poll();
					if (visited.contains(ew.to)) continue;
					mst.add(ew.from + " - " + ew.to + " : " + ew.weight);
					visited.add(ew.to);
					List nbrs = (List) adj.get(ew.to);
					Iterator it = nbrs.iterator();
					while (it.hasNext()) {
						Edge ne = (Edge) it.next();
						if (!visited.contains(ne.to)) pq.add(new EdgeWrapper(ew.to, ne.to, ne.weight));
					}
				}

					return mst;
				}

				// Dijkstra: return map of shortest distances from start to every reachable node
				public Map dijkstra(String start) {
					Map dist = new HashMap();
					Set visited = new HashSet();
					Iterator itKeys = adj.keySet().iterator();
					while (itKeys.hasNext()) { String k = (String) itKeys.next(); dist.put(k, Integer.valueOf(Integer.MAX_VALUE)); }
					if (!adj.containsKey(start)) return dist;

					class NodeDist { String node; int dist; NodeDist(String n,int d){node=n; this.dist=d;} }
					PriorityQueue pq = new PriorityQueue(new Comparator() {
						public int compare(Object a, Object b) { return ((NodeDist)a).dist - ((NodeDist)b).dist; }
					});

					dist.put(start, Integer.valueOf(0));
					pq.add(new NodeDist(start, 0));

					while (!pq.isEmpty()) {
						NodeDist nd = (NodeDist) pq.poll();
						String u = nd.node;
						if (visited.contains(u)) continue;
						visited.add(u);
						List nbrs = (List) adj.get(u);
						Iterator it = nbrs.iterator();
						while (it.hasNext()) {
							Edge e = (Edge) it.next();
							if (visited.contains(e.to)) continue;
							int alt = ((Integer)dist.get(u)).intValue() + e.weight;
							int cur = ((Integer)dist.get(e.to)).intValue();
							if (alt < cur) {
								dist.put(e.to, Integer.valueOf(alt));
								pq.add(new NodeDist(e.to, alt));
							}
						}
					}
					return dist;
				}

				// Dijkstra path from start to goal. Returns list of nodes in path (empty if no path)
				public List dijkstraPath(String start, String goal) {
					List path = new ArrayList();
					if (!adj.containsKey(start) || !adj.containsKey(goal)) return path;
					Map dist = new HashMap();
					Map parent = new HashMap();
					Set visited = new HashSet();
					Iterator itKeys = adj.keySet().iterator();
					while (itKeys.hasNext()) { String k = (String) itKeys.next(); dist.put(k, Integer.valueOf(Integer.MAX_VALUE)); }

					class NodeDist2 { String node; int dist; NodeDist2(String n,int d){node=n; this.dist=d;} }
					PriorityQueue pq = new PriorityQueue(new Comparator() {
						public int compare(Object a, Object b) { return ((NodeDist2)a).dist - ((NodeDist2)b).dist; }
					});

					dist.put(start, Integer.valueOf(0));
					parent.put(start, null);
					pq.add(new NodeDist2(start, 0));
					boolean found = false;

					while (!pq.isEmpty()) {
						NodeDist2 nd = (NodeDist2) pq.poll();
						String u = nd.node;
						if (visited.contains(u)) continue;
						visited.add(u);
						if (u.equals(goal)) { found = true; break; }
						List nbrs = (List) adj.get(u);
						Iterator it = nbrs.iterator();
						while (it.hasNext()) {
							Edge e = (Edge) it.next();
							if (visited.contains(e.to)) continue;
							int alt = ((Integer)dist.get(u)).intValue() + e.weight;
							int cur = ((Integer)dist.get(e.to)).intValue();
							if (alt < cur) {
								dist.put(e.to, Integer.valueOf(alt));
								parent.put(e.to, u);
								pq.add(new NodeDist2(e.to, alt));
							}
						}
					}

					if (!found) return path;
					String at = goal;
					while (at != null) { path.add(at); at = (String) parent.get(at); }
					Collections.reverse(path);
					return path;
				}
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

		// Demo for WeightedGraph and Prim's MST
		Graph.WeightedGraph wg = new Graph.WeightedGraph(false);
		wg.addEdge("A", "B", 4);
		wg.addEdge("A", "C", 1);
		wg.addEdge("C", "B", 2);
		wg.addEdge("B", "D", 5);
		wg.addEdge("C", "D", 8);
		wg.addEdge("C", "E", 10);
		wg.addEdge("D", "E", 2);

		System.out.println("Weighted adjacency list:");
		System.out.println(wg);
		System.out.println("Prim MST from A: " + wg.prim("A"));
		System.out.println("Dijkstra distances from A: " + wg.dijkstra("A"));
		System.out.println("Dijkstra A -> E: " + wg.dijkstraPath("A", "E"));
	}

}
