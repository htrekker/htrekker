package tools.skiplist;

import java.util.ArrayList;
import java.util.List;

public class Skiplist {
	private SkiplistNode root;
	private static final int MAX_LEVEL = 32;
	private int currentMaxLevel = 0;

	public int[] levels = new int[MAX_LEVEL + 1];
	private int size = 0;

	public Skiplist() {
		root = new SkiplistNode(32, -1);
	}

	public boolean search(int target) {
		SkiplistNode cur = root;
		for (int i = currentMaxLevel - 1; i >= 0; i--) {
			// move along the level
			while (cur.next[i] != null && cur.next[i].value < target)
				cur = cur.next[i];
			// move down the node
			if (cur.next[i] != null && cur.next[i].value == target)
				return true;
		}
		return false;
	}

	public void findPath(SkiplistNode[] path, int num, int level) {
		SkiplistNode cur = root;
		for (int i = Math.max(level, currentMaxLevel) - 1; i >= 0; i--) {
			while (cur.next[i] != null && cur.next[i].value < num)
				cur = cur.next[i];
			path[i] = cur;
		}
	}

	public void findPath(SkiplistNode[] path, int num) {
		findPath(path, num, 0);
	}

	public void add(int num) {
		if (num == root.value)
			return;
		int level = SkiplistHelper.getLevel();
		SkiplistNode[] path = new SkiplistNode[Math.max(level, currentMaxLevel)];
		levels[level]++;
		size++;
		findPath(path, num, level);
		/*
		 * insert
		 */
		SkiplistNode newNode = new SkiplistNode(level, num);
		for (int i = 0; i < level; i++) {
			newNode.next[i] = path[i].next[i];
			path[i].next[i] = newNode;
		}
		this.currentMaxLevel = Math.max(currentMaxLevel, level);
	}

	public boolean erase(int num) {
		SkiplistNode[] path = new SkiplistNode[currentMaxLevel];
		findPath(path, num, 0);

		SkiplistNode eraseable = path[0].next[0];
		if (eraseable == null || eraseable.value != num)
			return false;
		else {
			int level = eraseable.level();
			size--;
			levels[level]--;
			for (int i = 0; i < level; i++)
				path[i].next[i] = eraseable.next[i];
			return true;
		}
	}

	public List<Integer> findRange(int begin, int end) {
		List<Integer> list = new ArrayList<>();

		SkiplistNode cur = root;
		for(int i = currentMaxLevel - 1; i >= 0; i--) {
			while(cur.next[i] != null && cur.next[i].value < begin)
				cur = cur.next[i];
			if(cur.next[i] != null && cur.next[i].value == begin) {
				cur = cur.next[i];
				break;
			}
		}
		while(cur != null && cur.value <= end) {
			list.add(cur.value);
			cur = cur.next[0];
		}

		return list;
	}

	public void display() {
		System.out.println(currentMaxLevel);
		for (int i = currentMaxLevel; i >= 0 ; i--) {
			SkiplistNode cur = root.next[i];
			System.out.append("level [" + String.format("%2d", i) + "] : root");
			while (cur != null) {
				System.out.append("->" + cur.value);
				cur = cur.next[i];
			}
			System.out.append('\n');
			System.out.flush();
		}

	}

	public int getSize() {
		return this.size;
	}

	public int getMaxLevel() {
		return this.currentMaxLevel;
	}
}
