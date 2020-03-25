package tools.skiplist;

public class SkiplistNode {
	int value;
	SkiplistNode[] next;

	public SkiplistNode(int level, int value) {
		next = new SkiplistNode[level];
		this.value = value;
	}

	public int level() {
		return next.length;
	}
}
