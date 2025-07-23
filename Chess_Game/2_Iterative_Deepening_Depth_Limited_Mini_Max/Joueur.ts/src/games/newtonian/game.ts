// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { BaseGame } from "../../joueur/base-game";
import { GameObject } from "./game-object";
import { Job } from "./job";
import { Machine } from "./machine";
import { Player } from "./player";
import { Tile } from "./tile";
import { Unit } from "./unit";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * Combine elements and be the first scientists to create fusion.
 */
export class Game extends BaseGame {

    /**
     * The player whose turn it is currently. That player can send commands.
     * Other players cannot.
     */
    public readonly currentPlayer!: Player;

    /**
     * The current turn number, starting at 0 for the first player's turn.
     */
    public readonly currentTurn!: number;

    /**
     * A mapping of every game object's ID to the actual game object. Primarily
     * used by the server and client to easily refer to the game objects via ID.
     */
    public readonly gameObjects!: { [id: string]: GameObject | undefined };

    /**
     * The maximum number of interns a player can have.
     */
    public readonly internCap!: number;

    /**
     * A list of all jobs. The first element is intern, second is physicists,
     * and third is manager.
     */
    public readonly jobs!: Job[];

    /**
     * Every Machine in the game.
     */
    public readonly machines!: Machine[];

    /**
     * The maximum number of managers a player can have.
     */
    public readonly managerCap!: number;

    /**
     * The number of Tiles in the map along the y (vertical) axis.
     */
    public readonly mapHeight!: number;

    /**
     * The number of Tiles in the map along the x (horizontal) axis.
     */
    public readonly mapWidth!: number;

    /**
     * The number of materials that spawn per spawn cycle.
     */
    public readonly materialSpawn!: number;

    /**
     * The maximum number of turns before the game will automatically end.
     */
    public readonly maxTurns!: number;

    /**
     * The maximum number of physicists a player can have.
     */
    public readonly physicistCap!: number;

    /**
     * List of all the players in the game.
     */
    public readonly players!: Player[];

    /**
     * The amount of victory points added when a refined ore is consumed by the
     * generator.
     */
    public readonly refinedValue!: number;

    /**
     * The percent of max HP regained when a unit end their turn on a tile owned
     * by their player.
     */
    public readonly regenerateRate!: number;

    /**
     * A unique identifier for the game instance that is being played.
     */
    public readonly session!: string;

    /**
     * The amount of turns it takes a unit to spawn.
     */
    public readonly spawnTime!: number;

    /**
     * The amount of turns a unit cannot do anything when stunned.
     */
    public readonly stunTime!: number;

    /**
     * All the tiles in the map, stored in Row-major order. Use `x + y *
     * mapWidth` to access the correct index.
     */
    public readonly tiles!: Tile[];

    /**
     * The amount of time (in nano-seconds) added after each player performs a
     * turn.
     */
    public readonly timeAddedPerTurn!: number;

    /**
     * The number turns a unit is immune to being stunned.
     */
    public readonly timeImmune!: number;

    /**
     * Every Unit in the game.
     */
    public readonly units!: Unit[];

    /**
     * The amount of combined heat and pressure that you need to win.
     */
    public readonly victoryAmount!: number;

    /**
     * Gets the Tile at a specified (x, y) position
     * @param x an integer between 0 and the mapWidth
     * @param y an integer between 0 and the mapHeight
     * @returns the Tile at (x, y) or null if out of bounds
     */
    public getTileAt(x: number, y: number): Tile | undefined {
        if (x < 0 || y < 0 || x >= this.mapWidth || y >= this.mapHeight) {
            // out of bounds
            return undefined;
        }

        return this.tiles[x + y * this.mapWidth];
    }
    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
