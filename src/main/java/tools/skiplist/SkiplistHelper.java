package tools.skiplist;

import java.util.Random;

public class SkiplistHelper {
	public static Random rand = new Random(System.currentTimeMillis());

	public static int getIdealLevel(int[] levels, int size) {
		int i = 1;
		int target = 0;
		int min = 0;
		while (i < 32) {
			int t = (size >> i) - levels[i];
			if(t < min) {
				target = i;
				min = t;
			}
			i++;
		}
		return target;
	}

	/**
	 * The default level generator use a O(1) flip-coin algorithm.
	 * The effect of the this function is similar with {@link #getLeveL(float)}
	 * when the input p=0.5
	 * @return the generated level between 0, maxlevel.
	 */
	public static int getLevel() {
		int flips = rand.nextInt();
		int mask = 1, level = 0;
		while (level++ < 32 && (flips & mask) != 0)
			flips >>= 1;

		return level;
	}

	/**
	 * This method is used to generate level by the given p.
	 * The default choice is the another one.
	 * @return the generated level between 0 and maxlevel by the given p.
	 */
	public static int getLeveL(float p) {
		int level = 0;
		while(rand.nextFloat() < p && level < 32);
		level++;

		return level;
	}
}
