// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { Bomb } from "./bomb";
import { GameObject } from "./game-object";
import { Miner } from "./miner";
import { Tile } from "./tile";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A player in this game. Every AI controls one player.
 */
export class Player extends GameObject {

    /**
     * The Tile this Player's base is on.
     */
    public readonly baseTile!: Tile;

    /**
     * Every Bomb owned by this Player.
     */
    public readonly bombs!: Bomb[];

    /**
     * What type of client this is, e.g. 'Python', 'JavaScript', or some other
     * language. For potential data mining purposes.
     */
    public readonly clientType!: string;

    /**
     * The Tiles this Player's hoppers are on.
     */
    public readonly hopperTiles!: Tile[];

    /**
     * If the player lost the game or not.
     */
    public readonly lost!: boolean;

    /**
     * Every Miner owned by this Player.
     */
    public readonly miners!: Miner[];

    /**
     * The amount of money this Player currently has.
     */
    public readonly money!: number;

    /**
     * The name of the player.
     */
    public readonly name!: string;

    /**
     * This player's opponent in the game.
     */
    public readonly opponent!: Player;

    /**
     * The reason why the player lost the game.
     */
    public readonly reasonLost!: string;

    /**
     * The reason why the player won the game.
     */
    public readonly reasonWon!: string;

    /**
     * The amount of time (in ns) remaining for this AI to send commands.
     */
    public readonly timeRemaining!: number;

    /**
     * The amount of value (victory points) this Player has gained.
     */
    public readonly value!: number;

    /**
     * If the player won the game or not.
     */
    public readonly won!: boolean;

    /**
     * Spawns a Miner on this Player's base Tile.
     * @returns True if successfully spawned, false otherwise.
     */
    public async spawnMiner(): Promise<boolean> {
        return this.runOnServer("spawnMiner", {
        });
    }

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
