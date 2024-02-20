package state;

public class MapState {
    public static final int MAP_WIDTH = 100;
    public static final int MAP_HEIGHT = 100;
    public static final int TILE_SIZE = 32;
    public static final int TILE_SIZE_BITS = 5;
    public static final int TILE_SIZE_MASK = 31;


    public static final int MAP_WIDTH_TILES = MAP_WIDTH / TILE_SIZE;
    public static final int MAP_HEIGHT_TILES = MAP_HEIGHT / TILE_SIZE;
    public static final int MAP_WIDTH_TILES_HALF = MAP_WIDTH_TILES / 2;
    public static final int MAP_HEIGHT_TILES_HALF = MAP_HEIGHT_TILES / 2;

    public static final int MAP_SIZE = MAP_WIDTH * MAP_HEIGHT;
    public static final int MAP_SIZE_TILES = MAP_WIDTH_TILES * MAP_HEIGHT_TILES;

    public MapState() {

    }

    public static int getTileX(int x) {
        return x >> TILE_SIZE_BITS;
    }

    public static int getTileY(int y) {
        return y >> TILE_SIZE_BITS;
    }

    public static int getTileIndex(int x, int y) {
        return (y << TILE_SIZE_BITS) + x;
    }

    public static int getTileIndex(int x, int y, int width) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits, int tileSizeMask) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits, int tileSizeMask, int mapWidthTiles) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits, int tileSizeMask, int mapWidthTiles, int mapHeightTiles) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits, int tileSizeMask, int mapWidthTiles, int mapHeightTiles, int mapWidthTilesHalf) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits, int tileSizeMask, int mapWidthTiles, int mapHeightTiles, int mapWidthTilesHalf, int mapHeightTilesHalf) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits, int tileSizeMask, int mapWidthTiles, int mapHeightTiles, int mapWidthTilesHalf, int mapHeightTilesHalf, int mapSize) {
        return (y * width) + x;
    }

    public static int getTileIndex(int x, int y, int width, int height, int tileSize, int tileSizeBits, int tileSizeMask, int mapWidthTiles, int mapHeightTiles, int mapWidthTilesHalf, int mapHeightTilesHalf, int mapSize, int mapSizeTiles) {
        return (y * width) + x;
    }






}
