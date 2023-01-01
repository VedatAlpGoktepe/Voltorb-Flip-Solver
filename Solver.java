import java.util.ArrayList;

public class Solver {
	
	static int height = 5;
	static int width = 5;
	static ArrayList<int[][]> gridList = new ArrayList<int[][]>();

	public static void main(String[] args) {
		int grid[][] = {{-1,-1,-1,-1,-1},
						{-1,-1,-1,-1,-1},
						{-1,-1,-1,-1,-1},
						{-1,-1,-1,-1,-1},
						{-1,-1,-1,-1,-1}};

						// Easy grid reset (copy paste)
						// {-1,-1,-1,-1,-1},
						// {-1,-1,-1,-1,-1},
						// {-1,-1,-1,-1,-1},
						// {-1,-1,-1,-1,-1},
						// {-1,-1,-1,-1,-1}

		// Set sum of line and number of voltorbs for each row
		int hNum[] = {9,1,5,3,6};
		int hVolt[] = {1,4,2,3,0};

		// Set sum of line and number of voltorbs for each column
		int wNum[] = {7,5,4,4,4};
		int wVolt[] = {2,1,3,2,2};

		//Solves and prints out all grid solution possibilities
		solve(grid, hNum, hVolt, wNum, wVolt);

		double percentages[][] = new double[5][5];

		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				int posZ = 0;
				for(int gridPos = 0; gridPos < gridList.size(); gridPos++) {
					int curGrid[][] = gridList.get(gridPos);

					if(curGrid[i][j] == 0) {posZ++;}
				}
				System.out.println(posZ + " " + gridList.size());
				percentages[i][j] = (((double)1) - (((double)posZ)/((double)gridList.size())))*100;
			}
		}

		print_percentages(percentages);
	}

	public static int check(int grid[][], int h, int w, int[] hNum, int[] hVolt, int[] wNum, int[] wVolt) {
		int total = 0;
		int numZ = 0;
		for(int i = 0; i < width; i++) {
			if(grid[h][i] != -1) {total += grid[h][i];}
			if(grid[h][i] == 0) {numZ++;}
		}
		if(total > hNum[h] || (hNum[h]-total) > (4-w)*3 || numZ > hVolt[h] || (hVolt[h]-numZ) > (4-w)) {return 0;}

		total = 0;
		numZ = 0;
		for(int i = 0; i < height; i++) {
			if(grid[i][w] != -1) {total += grid[i][w];}
			if(grid[i][w] == 0) {numZ++;}
		}
		if(total > wNum[w] || (wNum[w]-total) > (4-h)*3 || numZ > wVolt[w] || (wVolt[w]-numZ) > (4-h)) {return 0;}

		return 1;
	}

	public static int[] find_empty(int grid[][]) {
		int vals[] = {-1, -1};

		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(grid[i][j] == -1) {
					vals[0] = i;
					vals[1] = j;
					break;
				}
			}
			if(vals[0] != -1) {
				break;
			}
		}

		return vals;
	}
	
	public static void print_voltorb(int grid[][]) {
		System.out.println("The Voltorb Solution is (possibly):");
		for (int j=0; j<5; j++)
		{
			for (int i=0; i<5;i++)
			{
				System.out.print(grid[j][i] + " ");
			}
			System.out.println();
		}
	}

	public static void solve(int grid[][], int hNum[], int hVolt[], int wNum[], int wVolt[]) {
		int h, w;
		int[] vals = find_empty(grid);
		if(vals[0] != -1) {
			h = vals[0];
			w = vals[1];
			for(int i = 0; i < 4; i++) {
	
				grid[h][w] = i;
	
				if(check(grid, h, w, hNum, hVolt, wNum, wVolt) == 1) {
	
					solve(grid, hNum, hVolt, wNum, wVolt);
					if(find_empty(grid)[0] == -1) {
						int solGrid[][] = new int[5][5];
						for(int y = 0; y < 5; y++) {
							for(int x = 0; x < 5; x++) {
								solGrid[y][x] = grid[y][x];
							}
						}
						gridList.add(solGrid);
					}
				}
	
				grid[h][w] = -1;
			}
		}
	}

	public static void print_percentages(double grid[][]) {
		System.out.println("Safety percentages for each tile:");
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				System.out.printf("%3.1f%% ", grid[i][j]);
			}
			System.out.println();
		}
	}
}
