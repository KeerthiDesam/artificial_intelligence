// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";
import { Player } from "./player";
import { Tower } from "./tower";
import { Unit } from "./unit";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A Tile in the game that makes up the 2D map grid.
 */
export class Tile extends GameObject {

    /**
     * Gets the valid directions that tiles can be in, "North", "East", "South",
     * or "West"
     * @returns [ "North", "East", "South", "West" ]
     */
    public static directions: ["North", "East", "South", "West"]
                            = ["North", "East", "South", "West"];

    /**
     * The amount of corpses on this tile.
     */
    public readonly corpses!: number;

    /**
     * Whether or not the tile is a castle tile.
     */
    public readonly isCastle!: boolean;

    /**
     * Whether or not the tile is considered to be a gold mine or not.
     */
    public readonly isGoldMine!: boolean;

    /**
     * Whether or not the tile is considered grass or not (Workers can walk on
     * grass).
     */
    public readonly isGrass!: boolean;

    /**
     * Whether or not the tile is considered to be the island gold mine or not.
     */
    public readonly isIslandGoldMine!: boolean;

    /**
     * Whether or not the tile is considered a path or not (Units can walk on
     * paths).
     */
    public readonly isPath!: boolean;

    /**
     * Whether or not the tile is considered a river or not.
     */
    public readonly isRiver!: boolean;

    /**
     * Whether or not the tile is considered a tower or not.
     */
    public readonly isTower!: boolean;

    /**
     * Whether or not the tile is the unit spawn.
     */
    public readonly isUnitSpawn!: boolean;

    /**
     * Whether or not the tile can be moved on by workers.
     */
    public readonly isWall!: boolean;

    /**
     * Whether or not the tile is the worker spawn.
     */
    public readonly isWorkerSpawn!: boolean;

    /**
     * The amount of Ghouls on this tile.
     */
    public readonly numGhouls!: number;

    /**
     * The amount of Hounds on this tile.
     */
    public readonly numHounds!: number;

    /**
     * The amount of Zombies on this tile.
     */
    public readonly numZombies!: number;

    /**
     * Which player owns this tile, only applies to grass tiles for workers,
     * NULL otherwise.
     */
    public readonly owner!: Player | undefined;

    /**
     * The Tile to the 'East' of this one (x+1, y). Null if out of bounds of the
     * map.
     */
    public readonly tileEast!: Tile | undefined;

    /**
     * The Tile to the 'North' of this one (x, y-1). Null if out of bounds of
     * the map.
     */
    public readonly tileNorth!: Tile | undefined;

    /**
     * The Tile to the 'South' of this one (x, y+1). Null if out of bounds of
     * the map.
     */
    public readonly tileSouth!: Tile | undefined;

    /**
     * The Tile to the 'West' of this one (x-1, y). Null if out of bounds of the
     * map.
     */
    public readonly tileWest!: Tile | undefined;

    /**
     * The Tower on this Tile if present, otherwise null.
     */
    public readonly tower!: Tower | undefined;

    /**
     * The Unit on this Tile if present, otherwise null.
     */
    public readonly unit!: Unit | undefined;

    /**
     * The x (horizontal) position of this Tile.
     */
    public readonly x!: number;

    /**
     * The y (vertical) position of this Tile.
     */
    public readonly y!: number;

    /**
     * Resurrect the corpses on this tile into Zombies.
     * @param num Number of zombies to resurrect.
     * @returns True if successful res, false otherwise.
     */
    public async res(num: number): Promise<boolean> {
        return this.runOnServer("res", {
            num,
        });
    }

    /**
     * Spawns a fighting unit on the correct tile.
     * @param title The title of the desired unit type.
     * @returns True if successfully spawned, false otherwise.
     */
    public async spawnUnit(title: string): Promise<boolean> {
        return this.runOnServer("spawnUnit", {
            title,
        });
    }

    /**
     * Spawns a worker on the correct tile.
     * @returns True if successfully spawned, false otherwise.
     */
    public async spawnWorker(): Promise<boolean> {
        return this.runOnServer("spawnWorker", {
        });
    }

    /**
     * Gets the neighbors of this Tile.
     *
     * @returns The neighboring (adjacent) Tiles to this tile.
     */
    public getNeighbors(): Tile[] {
        const neighbors = [];

        if (this.tileNorth) {
            neighbors.push(this.tileNorth);
        }

        if (this.tileEast) {
            neighbors.push(this.tileEast);
        }

        if (this.tileSouth) {
            neighbors.push(this.tileSouth);
        }

        if (this.tileWest) {
            neighbors.push(this.tileWest);
        }

        return neighbors;
    }

    /**
     * Checks if a Tile is pathable to units
     * @returns True if pathable, false otherwise
     */
    public isPathable(): boolean {
        // <<-- Creer-Merge: is-pathable-builtin -->>
        return false; // DEVELOPER ADD LOGIC HERE
        // <<-- /Creer-Merge: is-pathable-builtin -->>
    }

    /**
     * Checks if this Tile has a specific neighboring Tile
     * @returns true if the tile is a neighbor of this Tile, false otherwise
     */
    public hasNeighbor(tile: Tile | undefined): boolean {
        return Boolean(tile && (
            this.tileNorth === tile ||
            this.tileEast === tile ||
            this.tileSouth === tile ||
            this.tileEast === tile),
        );
    }
    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
