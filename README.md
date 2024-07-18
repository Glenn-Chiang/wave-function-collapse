# wave-function-collapse
A desktop application that procedurally generates tilemaps from input tilesets using the wave function collapse algorithm.

https://github.com/user-attachments/assets/d7e0a8f8-3c3e-417a-83d3-50e43905f38d

## Download

## Algorithm
Once a tileset is selected, each cell in the grid is initialized as a superposition of multiple states as it has equal potential to be any one of the tiles at the same time. The algorithm begins by randomly selecting a cell and *collapsing* it into a single tile. To collapse a cell means to randomly select one of its remaining possible tile states and reduce its states to include only that selected tile. After this cell is collapsed into a tile, the states of the neighboring cells will also be reduced according to the adjacency rules of that tile. Next, the algorithm finds the cell with the least entropy - that is, the cell with the least number of states - and collapses it, which in turn reduces the states of its neighbors. This process then propagates throughout the grid until all cells have been collapsed to form a complete tilemap.

## Usage
The repository contains a number of sample tilesets placed in directories under the `assets/tilesets` directory, where each tileset directory consists of a `tiles` directory and a `rules.json` file.  

Example directory structure:
```
assets
|__ tilesets
  |__ overworld
    |__ tiles
      |__ sand.png
      |__ grass.png
      |__ water.png
    |__ rules.json
```
All tilesets present in the tilesets directory will be loaded by the application. In order to add a new tileset to the application, you can simply create a new directory with the tileset's name under the `assets/tilesets` directory, then create a `tiles` directory and add the corresponding tile images to it. The `rules.json` file is then used to define the `weight` and `edges` for each tile.

### rules.json
The `rules.json` file sets the `weight` and `edges` for each tile. Note that the tile name should match the name of its corresponding image file.  
Example:
```
{
  "empty": {
    "weight": 20,
    "edges": ["000", "000", "000", "000"]
  },
  "wall": {
    "weight": 10,
    "edges": ["111", "111", "111", "111"]
  },
  "corridor": {
    "weight": 1,
    "edges": ["101", "111", "101", "111"]
  },
  "corner": {
    "weight": 1,
    "edges": ["110", "011", "111", "111"]
  },
}
```
The `weight` of a tile determines the probability that the algorithm will select this tile among all other valid tiles for a given position on the grid.  

The `edges` array gives a string label for each of the 4 edges of a tile in the sequence UP, RIGHT, DOWN, LEFT, which are used by the algorithm to determine which tiles are allowed to be adjacent to each other in each direction. A tile is allowed to be adjacent to another tile in a given direction if their opposite edges have labels that are the reverse of each other. In the above example, "wall" can be adjacent to the left or right of "corridor" as the left edge of "wall" is equal to the reverse of the right edge of "corridor" and the right edge of "wall" is equal to the reverse of the left edge of "corridor".   

For each tile listed, the program will generate all distinct configurations that can be formed by rotating that tile. In the above example, "corridor" can be rotated to form two distinct configurations of edges: `["101", "111", "101", "111"]` and `["111", "101", "111", "101"]`. On the other hand, "corner" can be rotated to form four distinct configurations: `["110", "011", "111", "111"]`, `["111", "110", "011", "111"]`, `["111", "111", "110", "011"]` and `["011", "111", "111", "110"]`. These additional configurations will automatically be generated by the program, thus it is unnecessary to manually add multiple images for each way a tile can be rotated.