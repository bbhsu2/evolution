package com.allgoodpeopleus.evolution;

public class GameOfLifeModel {
	private int GridWidth;
	private int GridHeight;
	private boolean[][] Cells;
	
	public final String Information = "1. Black = Alive; White = Dead\n2. <2 or >3 live neighbors -> death by under/over-population\n3.2-3 Neighbors -> live\n4.Dead cell with 3 neighbors becomes live.";
	public final String BlogUrl = "http://letsthinkabout.us/post/android-game-of-life";

	public GameOfLifeModel(int width, int height) {
		GridWidth = width;
		GridHeight = height;
		initializeAll();
	}

	public void initializeAll() {
		Cells = new boolean[GridWidth][GridHeight];
		setRandom();
	}

	public void setGlider() {
		if (GridWidth > 2 && GridHeight > 2) {
			resetCells();
			Cells[1][0] = true;
			Cells[2][1] = true;
			Cells[0][2] = true;
			Cells[1][2] = true;
			Cells[2][2] = true;
		}
	}

	public void setRandom() {
		if (GridWidth > 2 && GridHeight > 2) {
			resetCells();
			java.util.Random rnd = new java.util.Random();
			for (int i = 0; i < GridWidth; i++) {
				for (int j = 0; j < GridHeight; j++) {
					Cells[i][j] = rnd.nextDouble() > 0.5 ? true : false;
				}
			}
		}
	}
	
	public void setStarShip(){
		if (GridWidth > 20 && GridHeight > 20) {
			resetCells();
			Cells[4][3] = true;
			Cells[5][3] = true;
			Cells[6][3] = true;
			Cells[7][3] = true;
			Cells[3][4] = true;
			Cells[7][4] = true;
			Cells[7][5] = true;
			Cells[3][6] = true;
			Cells[6][6] = true;
			
			Cells[4][9] = true;
			Cells[5][9] = true;
			Cells[6][9] = true;
			Cells[7][9] = true;
			Cells[3][10] = true;
			Cells[7][10] = true;
			Cells[7][11] = true;
			Cells[3][12] = true;
			Cells[6][12] = true;
			
			Cells[4][15] = true;
			Cells[5][15] = true;
			Cells[6][15] = true;
			Cells[7][15] = true;
			Cells[3][16] = true;
			Cells[7][16] = true;
			Cells[7][17] = true;
			Cells[3][18] = true;
			Cells[6][18] = true;
			
			Cells[4][21] = true;
			Cells[5][21] = true;
			Cells[6][21] = true;
			Cells[7][21] = true;
			Cells[3][22] = true;
			Cells[7][22] = true;
			Cells[7][23] = true;
			Cells[3][24] = true;
			Cells[6][24] = true;
		}
	}

	public void setInfinite() {
        if (GridWidth > 7 && GridHeight > 7){
            resetCells();
            Cells[0][5] = true;
            Cells[2][4] = true;
            Cells[2][5] = true;
            Cells[4][1] = true;
            Cells[4][2] = true;
            Cells[4][3] = true;
            Cells[6][0] = true;
            Cells[6][1] = true;
            Cells[6][2] = true;
            Cells[7][1] = true;
        }
    }

	void resetCells() {
		for (int x = 0; x < GridWidth; x++) {
			for (int y = 0; y < GridHeight; y++) {
				Cells[x][y] = false;
			}
		}
	}

	public int getWidth() {
		return GridWidth;
	}

	public int getHeight() {
		return GridHeight;
	}

	public boolean isAlive(int x, int y) {
		if (x < 0 || x >= GridWidth) {
			return false;
		}
		if (y < 0 || y >= GridHeight) {
			return false;
		}
		return Cells[x][y];
	}

	public int getNumAlive() {
		int alive = 0;
		for (int x = 0; x < GridWidth; x++) {
			for (int y = 0; y < GridHeight; y++) {
				if (isAlive(x, y)) {
					alive++;
				}
			}
		}
		return alive;
	}

	private int getNumNeighbors(int x, int y) {
		int neighbors = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i == x && j == y) {
					// Don't reference yourself
					continue;
				}
				if (isAlive(i, j)) {
					neighbors++;
				}
			}
		}
		return neighbors;
	}

	private boolean willBeAlive(int x, int y) {
		boolean Alive = isAlive(x, y);
		int AliveNeighbors = getNumNeighbors(x, y);
		if (Alive) {
			if (AliveNeighbors < 2) {
				return false;
			}
			if (AliveNeighbors > 3) {
				return false;
			}
		} else {
			if (AliveNeighbors == 3) {
				return true;
			}
		}
		return Alive;
	}

	public void tick() {
		boolean[][] NewCells = new boolean[GridWidth][GridHeight];
		for (int x = 0; x < GridWidth; x++) {
			for (int y = 0; y < GridHeight; y++) {
				NewCells[x][y] = willBeAlive(x, y);
			}
		}
		for (int x = 0; x < GridWidth; x++) {
			for (int y = 0; y < GridHeight; y++) {
				Cells[x][y] = NewCells[x][y];
			}
		}
	}
}
