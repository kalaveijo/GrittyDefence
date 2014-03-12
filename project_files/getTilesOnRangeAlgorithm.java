

public ArrayList<Tile> getTilesOnRange(int startX, int startY, int range, Map map){
	int currentX = startX;
	int currentY = startY;
	
	ArrayList<Tile> selectedTiles = new ArrayList<Tile>();
	
	// miten monta kierrosta halutaan
	for(int i = 1; i < range + 1; i++){
	
	currentX = currentX - i;
	selectedTiles.add(map.getTile(currentX, currentY));
	for(int e = 0; e < i; e++){
		currentX = currentX + 1;
		currentY = currentY -1;
		//nullcheck here
		selectedTiles.add(map.getTile(currentX, currentY));
	}
	for(e = 0; e < i; e++){
		currentX = currentX + 1;
		currentY = currentY +1;
		//nullcheck here
		selectedTiles.add(map.getTile(currentX, currentY));
	}
	for(e = 0; e < i; e++){
		currentX = currentX - 1;
		currentY = currentY + 1;
		//nullcheck here
		selectedTiles.add(map.getTile(currentX, currentY));
	}
	
	for(e = 0; e < i - 1; e++){
		currentX = currentX - 1;
		currentY = currentY - 1;
		//nullcheck here
		selectedTiles.add(map.getTile(currentX, currentY));
	}
	
	// tarttee vielä yhden kierroksen ylävasempaan
	
	currentY = currentY - 1;
	}
	return selectedTiles;
}