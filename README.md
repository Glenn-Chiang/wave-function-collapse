# wave-function-collapse
A desktop application that procedurally generates tilemaps from input tilesets using the wave function collapse algorithm.
https://github.com/user-attachments/assets/d7e0a8f8-3c3e-417a-83d3-50e43905f38d

## Algorithm

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
All tilesets present in the tilesets directory will be loaded by the application. In order to add a new tileset, you can simply create a new directory with the tileset's name under the `assets/tilesets` directory, then create a `tiles` directory and add the corresponding tile images to it. The `rules.json` file is then used to define the `weight` and `edges` for each tile.

### rules.json
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
