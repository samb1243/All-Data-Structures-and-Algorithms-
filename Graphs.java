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

/*
 * Graphs.java
 *
 * A small collection of graph data structures and algorithms implemented
 * in a compact style for educational/demo purposes. The code uses raw
 * collections (pre-generic style) so it compiles on older Java versions.
 *
 * The file contains:
 * - Graph: a simple adjacency-list graph for unweighted graphs
 * - Graph.WeightedGraph: weighted adjacency-list graph with Prim and Dijkstra
 * - Demonstration `main` that exercises basic functionality
 */

public class Graphs {

	// Simple adjacency-list graph implementation (vertices are String labels)
	public static class Graph {
		// adjacency map: vertex -> list of neighbor vertex labels
		private final Map adj = new HashMap(); // key: String -> value: List of neighbors
		// whether this graph is directed
		private final boolean directed;

		// Constructor: set directedness
		public Graph(boolean directed) {
			this.directed = directed;
		}

		// Nested weighted-graph class supporting Prim's and Dijkstra's algorithms
		public static class WeightedGraph {
			// adjacency map for weighted graph: vertex -> list of Edge objects
			private final Map adj = new HashMap(); // key: String -> List of Edge
			private final boolean directed;

			// Simple container for an edge (destination label and weight)
			public static class Edge {
				public String to;    // destination vertex label
				public int weight;   // edge weight
				public Edge(String to, int weight) { this.to = to; this.weight = weight; }
				public String toString() { return to + "(" + weight + ")"; }
			}

			// Helper wrapper used when adding edges to a priority queue (from, to, weight)
			private static class EdgeWrapper {
				public String from;
				public String to;
				public int weight;
				public EdgeWrapper(String f, String t, int w) { from = f; to = t; weight = w; }
			}

			// Construct a weighted graph (directed or undirected)
			public WeightedGraph(boolean directed) { this.directed = directed; }

			// Ensure the vertex exists in the adjacency map (create empty neighbor list)
			public void addVertex(String v) { if (!adj.containsKey(v)) adj.put(v, new ArrayList()); }

			// Add a weighted edge u->v of weight w. For undirected graphs, also add v->u.
			public void addEdge(String u, String v, int w) {
				addVertex(u); // create u if missing
				addVertex(v); // create v if missing
				List neighborsU = (List) adj.get(u); // raw cast to List
				neighborsU.add(new Edge(v, w)); // append edge object
				if (!directed) {
					List neighborsV = (List) adj.get(v);
					neighborsV.add(new Edge(u, w)); // add reverse edge for undirected graph
				}
			}

			// Build a readable adjacency listing for debugging
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

			// Prim's algorithm for Minimum Spanning Tree (MST).
			// Returns a List of strings describing edges in the MST: "u - v : w".
			public List prim(String start) {
				List mst = new ArrayList(); // result list
				if (!adj.containsKey(start)) return mst; // no start vertex
				Set visited = new HashSet(); // vertices already in MST

				// Priority queue that orders EdgeWrapper by weight (smallest first)
				PriorityQueue pq = new PriorityQueue(new Comparator() {
					public int compare(Object a, Object b) {
						EdgeWrapper ea = (EdgeWrapper) a;
						EdgeWrapper eb = (EdgeWrapper) b;
						return ea.weight - eb.weight; // numeric comparison
					}
				});

				// Seed the PQ with edges adjacent to the start vertex
				visited.add(start);
				List startNbrs = (List) adj.get(start);
				Iterator it0 = startNbrs.iterator();
				while (it0.hasNext()) {
					Edge e = (Edge) it0.next();
					pq.add(new EdgeWrapper(start, e.to, e.weight));
				}

				// Process edges in increasing weight order
				while (!pq.isEmpty()) {
					EdgeWrapper ew = (EdgeWrapper) pq.poll(); // cheapest available edge
					if (visited.contains(ew.to)) continue; // ignore edges to already visited
					mst.add(ew.from + " - " + ew.to + " : " + ew.weight); // accept edge
					visited.add(ew.to); // add vertex to MST
					// add all outgoing edges from the newly added vertex
					List nbrs = (List) adj.get(ew.to);
					Iterator it = nbrs.iterator();
					while (it.hasNext()) {
						Edge ne = (Edge) it.next();
						if (!visited.contains(ne.to)) pq.add(new EdgeWrapper(ew.to, ne.to, ne.weight));
					}
				}

				return mst; // return constructed MST
			}

			// Dijkstra's algorithm: return a Map of shortest distances from `start`.
			// Distances are Integer values; unreachable nodes will remain Integer.MAX_VALUE.
			public Map dijkstra(String start) {
				Map dist = new HashMap(); // tentative distances
				Set visited = new HashSet(); // finalized vertices
				// initialize distances to "infinity"
				Iterator itKeys = adj.keySet().iterator();
				while (itKeys.hasNext()) { String k = (String) itKeys.next(); dist.put(k, Integer.valueOf(Integer.MAX_VALUE)); }
				if (!adj.containsKey(start)) return dist; // empty if start missing

				// Helper node used by the priority queue to compare nodes by distance
				class NodeDist { String node; int dist; NodeDist(String n,int d){node=n; this.dist=d;} }
				PriorityQueue pq = new PriorityQueue(new Comparator() {
					public int compare(Object a, Object b) { return ((NodeDist)a).dist - ((NodeDist)b).dist; }
				});

				// distance to start is zero; push into PQ
				dist.put(start, Integer.valueOf(0));
				pq.add(new NodeDist(start, 0));

				// Main Dijkstra loop
				while (!pq.isEmpty()) {
					NodeDist nd = (NodeDist) pq.poll();
					String u = nd.node;
					if (visited.contains(u)) continue; // skip stale entries
					visited.add(u); // finalize u
					List nbrs = (List) adj.get(u);
					Iterator it = nbrs.iterator();
					while (it.hasNext()) {
						Edge e = (Edge) it.next();
						if (visited.contains(e.to)) continue; // neighbor already finalized
						int alt = ((Integer)dist.get(u)).intValue() + e.weight; // distance via u
						int cur = ((Integer)dist.get(e.to)).intValue();
						if (alt < cur) {
							dist.put(e.to, Integer.valueOf(alt)); // improve distance
							pq.add(new NodeDist(e.to, alt)); // push candidate
						}
					}
				}
				return dist; // map with shortest distances
			}

			// Dijkstra path: returns the shortest path (list of vertex labels) from start to goal.
			public List dijkstraPath(String start, String goal) {
				List path = new ArrayList();
				if (!adj.containsKey(start) || !adj.containsKey(goal)) return path;
				Map dist = new HashMap();
				Map parent = new HashMap(); // predecessor map to reconstruct path
				Set visited = new HashSet();
				Iterator itKeys = adj.keySet().iterator();
				while (itKeys.hasNext()) { String k = (String) itKeys.next(); dist.put(k, Integer.valueOf(Integer.MAX_VALUE)); }

				class NodeDist2 { String node; int dist; NodeDist2(String n,int d){node=n; this.dist=d;} }
				PriorityQueue pq = new PriorityQueue(new Comparator() {
					public int compare(Object a, Object b) { return ((NodeDist2)a).dist - ((NodeDist2)b).dist; }
				});

				dist.put(start, Integer.valueOf(0));
				parent.put(start, null); // start has no predecessor
				pq.add(new NodeDist2(start, 0));
				boolean found = false;

				// Process until PQ empty or goal finalized
				while (!pq.isEmpty()) {
					NodeDist2 nd = (NodeDist2) pq.poll();
					String u = nd.node;
					if (visited.contains(u)) continue; // stale entry
					visited.add(u);
					if (u.equals(goal)) { found = true; break; } // reached goal
					List nbrs = (List) adj.get(u);
					Iterator it = nbrs.iterator();
					while (it.hasNext()) {
						Edge e = (Edge) it.next();
						if (visited.contains(e.to)) continue;
						int alt = ((Integer)dist.get(u)).intValue() + e.weight;
						int cur = ((Integer)dist.get(e.to)).intValue();
						if (alt < cur) {
							dist.put(e.to, Integer.valueOf(alt));
							parent.put(e.to, u); // record predecessor for path
							pq.add(new NodeDist2(e.to, alt));
						}
					}
				}

				if (!found) return path; // no path to goal
				// Reconstruct path by walking predecessors from goal -> start
				String at = goal;
				while (at != null) { path.add(at); at = (String) parent.get(at); }
				Collections.reverse(path); // reverse to start->goal
				return path;
			}
		}

		// --- Unweighted Graph methods (instances of Graph) ---

		// Add a vertex label to the adjacency map if missing
		public void addVertex(String v) {
			if (!adj.containsKey(v)) adj.put(v, new ArrayList());
		}

		// Add an unweighted edge u-v. For undirected graphs, add both directions.
		public void addEdge(String u, String v) {
			addVertex(u);
			addVertex(v);
			List neighborsU = (List) adj.get(u);
			neighborsU.add(v); // append neighbor label
			if (!directed) {
				List neighborsV = (List) adj.get(v);
				neighborsV.add(u); // add reverse neighbor for undirected
			}
		}

		// Check presence of a vertex label
		public boolean hasVertex(String v) {
			return adj.containsKey(v);
		}

		// Check presence of an edge u->v
		public boolean hasEdge(String u, String v) {
			if (!adj.containsKey(u)) return false;
			List neighborsU = (List) adj.get(u);
			return neighborsU.contains(v);
		}

		// Breadth-first traversal from `start` returning visitation order
		public List bfs(String start) {
			List order = new ArrayList(); // visitation order
			if (!adj.containsKey(start)) return order; // empty if start missing
			Queue q = new LinkedList(); // queue for BFS
			Set seen = new HashSet(); // visited set
			q.add(start); // enqueue start
			seen.add(start);
			while (!q.isEmpty()) {
				String cur = (String) q.remove(); // dequeue
				order.add(cur); // record node
				List nbrs = (List) adj.get(cur); // neighbors
				Iterator it = nbrs.iterator();
				while (it.hasNext()) {
					String nbr = (String) it.next();
					if (!seen.contains(nbr)) {
						seen.add(nbr); // mark seen
						q.add(nbr); // enqueue neighbor
					}
				}
			}
			return order;
		}

		// Recursive DFS wrapper: returns visitation order starting from `start`
		public List dfs(String start) {
			List order = new ArrayList();
			if (!adj.containsKey(start)) return order;
			Set seen = new HashSet();
			dfsHelper(start, seen, order);
			return order;
		}

		// Recursive DFS helper: visit node, then recursively visit unseen neighbors
		private void dfsHelper(String node, Set seen, List order) {
			seen.add(node); // mark current node
			order.add(node); // record visitation
			List nbrs = (List) adj.get(node);
			Iterator it = nbrs.iterator();
			while (it.hasNext()) {
				String nbr = (String) it.next();
				if (!seen.contains(nbr)) dfsHelper(nbr, seen, order); // recurse
			}
		}

		// Iterative DFS using an explicit stack (LIFO). Returns visitation order.
		public List dfsIterative(String start) {
			List order = new ArrayList();
			if (!adj.containsKey(start)) return order;
			Set seen = new HashSet();
			Stack stack = new Stack();
			stack.push(start); // push start on stack
			while (!stack.isEmpty()) {
				String cur = (String) stack.pop(); // pop top
				if (seen.contains(cur)) continue; // skip if already visited
				seen.add(cur); // mark visited
				order.add(cur); // record visitation
				List nbrs = (List) adj.get(cur);
				// push neighbors in reverse order so natural order is preserved
				for (int i = nbrs.size() - 1; i >= 0; i--) {
					String nbr = (String) nbrs.get(i);
					if (!seen.contains(nbr)) stack.push(nbr);
				}
			}
			return order;
		}

		// Breadth-first search that returns the shortest path (unweighted) from start to goal.
		public List breadthFirstSearch(String start, String goal) {
			List path = new ArrayList();
			if (!adj.containsKey(start) || !adj.containsKey(goal)) return path;
			Queue q = new LinkedList();
			Map parent = new HashMap(); // to reconstruct path
			Set seen = new HashSet();
			q.add(start);
			seen.add(start);
			parent.put(start, null); // start has no parent
			boolean found = false;
			while (!q.isEmpty()) {
				String cur = (String) q.remove();
				if (cur.equals(goal)) { found = true; break; } // reached goal
				List nbrs = (List) adj.get(cur);
				Iterator it = nbrs.iterator();
				while (it.hasNext()) {
					String nbr = (String) it.next();
					if (!seen.contains(nbr)) {
						seen.add(nbr);
						parent.put(nbr, cur); // record predecessor
						q.add(nbr);
					}
				}
			}
			if (!found) return path; // no path found
			String at = goal;
			while (at != null) {
				path.add(at); // reconstruct path backwards
				at = (String) parent.get(at);
			}
			Collections.reverse(path); // reverse to get start -> goal order
			return path;
		}

		// Human-readable adjacency output
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
		Graph g = new Graph(false); // create undirected unweighted graph
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
