package cs21as08;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class p8 {	
	private static int[] right = {1, 3, 5, 7, 9, 11, 13, 15};
	private static int[] left = {4, 5, 6, 7, 12, 13, 14, 15};
	private static int[] top = {8, 9, 10, 11, 12, 13, 14, 15};
	private static int[] bottom = {2, 3, 6, 7, 10, 11, 14, 15};
	
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		sb.append(stdin.nextLine());
		int size = sb.toString().length();
		for (int i = 0; i < size - 1; i++) {
			sb.append(stdin.nextLine());
		}
		int[] parent = BFS(sb.toString().toCharArray());
		int x = size * size - 1;
		sb = new StringBuilder();
		while (x != 0) {
			x = parent[x];
			sb.insert(0,"(" + x / size + ", " + x % size + ")\n");		
		}
		sb.append("(" + (size - 1) + ", " + (size - 1) + ")\n");
		System.out.println(sb.toString());
	}
	
	private static int[] BFS(char[] graph) {
		LinkedList<Integer> q = new LinkedList<>();
		String[] color = new String[graph.length];
		int[] distance = new int[graph.length];
		int[] parent = new int[graph.length];
		int[] graph2 = new int[graph.length];
		int size = (int) Math.sqrt(graph.length);
		for (int i = 0; i < graph.length; i++) {
			if (Integer.valueOf(graph[i]) >= 87) {
				graph2[i] = Integer.valueOf(graph[i]) - 87;
			} else {
				graph2[i] = Character.getNumericValue(graph[i]);
			}
			color[i] = null;
			distance[i] = Integer.MAX_VALUE;
			parent[i] = -1;
		}
		color[0] = "gray";
		distance[0] = 0;
		parent[0] = 0;
		q.add(0);
		while(!q.isEmpty()) {
			int t = q.getFirst();
			int pos = checkPos(t, size);
			if (pos != 0 && pos != 1 && pos != -1) { // top is available
				if (!IntStream.of(top).anyMatch(x -> x == graph2[t]) && !"black".equals(color[t-size])) {
					color[t - size] = "gray";
					q.add(t - size);
					distance[t - size] = distance[t] + 1;
					parent[t - size] = t;
				}
			}
			if (pos !=  2 && pos != 1 && pos != 3) {
				if (!IntStream.of(right).anyMatch(x -> x == graph2[t]) && !"black".equals(color[t+1])) {
					color[t + 1] = "gray";
					q.add(t + 1);
					distance[t + 1] = distance[t] + 1;
					parent[t + 1] = t;
				}
			}
			if (pos != 3 && pos != 4 && pos != 5) {
				if (!IntStream.of(bottom).anyMatch(x -> x == graph2[t]) && !"black".equals(color[t+size])) {
					color[t + size] = "gray";
					q.add(t + size);
					distance[t + size] = distance[t] + 1;
					parent[t + size] = t;
				}
			}
			if (pos != -1 && pos != 5 && pos != 7) {
				if (!IntStream.of(left).anyMatch(x -> x == graph2[t]) && !"black".equals(color[t-1])) {
					color[t - 1] = "gray";
					q.add(t - 1);
					distance[t - 1] = distance[t] + 1;
					parent[t - 1] = t;
				}
			}
			color[t] = "black";
			q.remove();
		}	
		return parent;
	}
		
	private static int checkPos(int x, int size) {
		if (x == 0) return -1; // top left
		if (x != 0 && x < size) return 0; // top
		if (x == size - 1) return 1; // top right
		if (x != size - 1 && x != (size * size) - 1 && (x + 1) % size == 0) return 2; // right
		if (x == (size * size) - 1) return 3; // bottom right
		if (x < (size * size) - 1 && x > (size * size) - size) return 4; // bottom
		if (x == (size * size) - size) return 5; // bottom left
		if (x != (size * size) - size && x != 0 && x % size == 0) return 7; // left
		return 8;
	}
}
