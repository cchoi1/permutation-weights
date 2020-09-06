import java.util.ArrayList;

public class PermutationWeights {
	static ArrayList<Integer> entered = new ArrayList<Integer>();
	public ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>();

	public static ArrayList<Integer> addelements(int value) {
		ArrayList<Integer> enter = new ArrayList<Integer>();
		for (int i = 1; i <= value; i++) {
			enter.add(i);
		}
		return enter;
	}

	/* Constructs all permutations of the numbers 1,..., num */
	public static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> num) {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		res.add(new ArrayList<Integer>());
		for (int number = 0; number < num.size(); number++) {
			ArrayList<ArrayList<Integer>> curr = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> nestedL : res) {
				for (int j = 0; j < nestedL.size() + 1; j++) {
					nestedL.add(j, num.get(number));
					ArrayList<Integer> temp = new ArrayList(nestedL);
					curr.add(temp);
					nestedL.remove(j);
				}
			}
			res = new ArrayList<ArrayList<Integer>>(curr);
		}
		return res;
	}

	/* Computes the number of descents in a permutation. */
	public static int descent(ArrayList<Integer> input) {
		int output = 0;
		for (int i = 0; i < input.size() - 1; i++) {
			if (input.get(i) > input.get(i + 1)) {
				output++;
			}
		}
		return output;
	}

	/* Recursively splits a permutation according to the definition for the weight of a permutation. */
	public static ArrayList<ArrayList<Integer>> split(ArrayList<Integer> input) {
		input.add(100000000);
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> middle = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> value = new ArrayList<ArrayList<Integer>>();
		int smallest = Integer.MAX_VALUE;
		int position = 0;
		if (input.size() == 1) {
			if (left.size() != 0) {
				value.add(left);
			}
			if (input.size() != 0) {
				value.add(input);
			}
			if (right.size() != 0) {
				value.add(right);
			}
			return value;
		} else {
			// Find the smallest element and split the array accordingly.
			for (int i = 0; i < input.size(); i++) {
				if (input.get(i) < smallest) {
					smallest = input.get(i);
					position = i;
				}
			}
			// Create left set.
			for (int j = 0; j < position; j++) {
				left.add(input.get(j));
			}
			// Create middle set.
			middle.add(input.get(position));
			// Create right set.
			for (int k = position + 1; k < input.size(); k++) {
				right.add(input.get(k));
			}
			if (left.size() != 0) {
				value.addAll(splitLeft(left));
			}
			if (middle.size() != 0) {
				value.add(middle);
			}
			if (right.size() != 0) {
				value.add(right);
			}
			return value;
		}
	}

	public static boolean isInOrder(ArrayList<Integer> input) {
		for (int i = 0; i < input.size() - 1; i++) {
			if (input.get(i) > input.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	public static ArrayList<ArrayList<Integer>> splitLeft(ArrayList<Integer> input) {
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> value = new ArrayList<ArrayList<Integer>>();
		int largest = Integer.MIN_VALUE;
		int position = 0;
		if (input.size() == 1 || isInOrder(input)) {
			value.add(input);
			return value;
		}
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i) > largest) {
				largest = input.get(i);
				position = i;
			}
		}
		for (int i = 0; i <= position; i++) {
			left.add(input.get(i));
		}
		for (int j = position + 1; j < input.size(); j++) {
			right.add(input.get(j));
		}
		if (left.size() != 0) {
			value.add(left);
		}
		input = right;
		value.addAll(splitLeft(input));
		return value;
	}

	public static int weight(ArrayList<Integer> input) {
		int sum = 0;
		if (isInOrder(input) || input.size() == 1) {
			return 0;
		}
		for (ArrayList<Integer> z : split(input)) {
			sum = sum + weight(z) + descent(z);
		}
		return sum;
	}

	public static int finalweight(ArrayList<ArrayList<Integer>> input) {
		int sum = 0;
		for (ArrayList<Integer> x : input) {
			sum = sum + weight(x) + descent(x);
		}
		return sum;
	}

	public static void printRow(int[] row) {
		for (int i = row.length - 1; i >= 0; i--) {
			System.out.print(row[i]);
			System.out.print("\t");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		String output = "";
		
		/* Specify the length of the permutation here. */
		int sizeofset = 7;
		
		int[][] store = new int[15][25];
		ArrayList<Integer> x = new ArrayList<Integer>();
		x.add(2);
		x.add(9);
		x.add(8);
		x.add(3);
		x.add(4);
		x.add(1);
		x.add(5);
		x.add(7);
		x.add(6);
		ArrayList<ArrayList<Integer>> y = permute(addelements(sizeofset));
		ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		System.out.println(y);
		try {
			for (ArrayList<Integer> z : y) {
				store[descent(z)][finalweight(split(z))]++;
				System.out.println(z);
				System.out.print(descent(z));
				System.out.println(finalweight(split(z)));
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
		for (int[] row : store) {
			printRow(row);
		}
	}
}