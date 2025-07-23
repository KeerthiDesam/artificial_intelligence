// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { BaseGame } from "../../joueur/base-game";
import { Bomb } from "./bomb";
import { GameObject } from "./game-object";
import { Miner } from "./miner";
import { Player } from "./player";
import { Tile } from "./tile";
import { Upgrade } from "./upgrade";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * Mine resources to obtain more value than your opponent.
 */
export class Game extends BaseGame {

    /**
     * The monetary price of a bomb when bought or sold.
     */
    public readonly bombPrice!: number;

    /**
     * The amount of cargo space taken up by a Bomb.
     */
    public readonly bombSize!: number;

    /**
     * Every Bomb in the game.
     */
    public readonly bombs!: Bomb[];

    /**
     * The monetary price of building materials when bought.
     */
    public readonly buildingMaterialPrice!: number;

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
     * The monetary price of dirt when bought or sold.
     */
    public readonly dirtPrice!: number;

    /**
     * The amount of damage taken per Tile fallen.
     */
    public readonly fallDamage!: number;

    /**
     * The amount of extra damage taken for falling while carrying a large
     * amount of cargo.
     */
    public readonly fallWeightDamage!: number;

    /**
     * A mapping of every game object's ID to the actual game object. Primarily
     * used by the server and client to easily refer to the game objects via ID.
     */
    public readonly gameObjects!: { [id: string]: GameObject | undefined };

    /**
     * The amount of building material required to build a ladder.
     */
    public readonly ladderCost!: number;

    /**
     * The amount of mining power needed to remove a ladder from a Tile.
     */
    public readonly ladderHealth!: number;

    /**
     * The amount deemed as a large amount of cargo.
     */
    public readonly largeCargoSize!: number;

    /**
     * The amount deemed as a large amount of material.
     */
    public readonly largeMaterialSize!: number;

    /**
     * The number of Tiles in the map along the y (vertical) axis.
     */
    public readonly mapHeight!: number;

    /**
     * The number of Tiles in the map along the x (horizontal) axis.
     */
    public readonly mapWidth!: number;

    /**
     * The maximum amount of shielding possible on a Tile.
     */
    public readonly maxShielding!: number;

    /**
     * The maximum number of turns before the game will automatically end.
     */
    public readonly maxTurns!: number;

    /**
     * The highest upgrade level allowed on a Miner.
     */
    public readonly maxUpgradeLevel!: number;

    /**
     * Every Miner in the game.
     */
    public readonly miners!: Miner[];

    /**
     * The amount of money awarded when ore is dumped in the base and sold.
     */
    public readonly orePrice!: number;

    /**
     * The amount of value awarded when ore is dumped in the base and sold.
     */
    public readonly oreValue!: number;

    /**
     * List of all the players in the game.
     */
    public readonly players!: Player[];

    /**
     * A unique identifier for the game instance that is being played.
     */
    public readonly session!: string;

    /**
     * The amount of building material required to shield a Tile.
     */
    public readonly shieldCost!: number;

    /**
     * The amount of mining power needed to remove one unit of shielding off a
     * Tile.
     */
    public readonly shieldHealth!: number;

    /**
     * The monetary price of spawning a Miner.
     */
    public readonly spawnPrice!: number;

    /**
     * The amount of damage taken when suffocating inside a filled Tile.
     */
    public readonly suffocationDamage!: number;

    /**
     * The amount of extra damage taken for suffocating under a large amount of
     * material.
     */
    public readonly suffocationWeightDamage!: number;

    /**
     * The amount of building material required to build a support.
     */
    public readonly supportCost!: number;

    /**
     * The amount of mining power needed to remove a support from a Tile.
     */
    public readonly supportHealth!: number;

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
     * The cost to upgrade a Miner.
     */
    public readonly upgradePrice!: number;

    /**
     * Every Upgrade for a Miner in the game.
     */
    public readonly upgrades!: Upgrade[];

    /**
     * The amount of victory points (value) required to win.
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
