// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";
import { Tile } from "./tile";
import { Unit } from "./unit";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A player in this game. Every AI controls one player.
 */
export class Player extends GameObject {

    /**
     * What type of client this is, e.g. 'Python', 'JavaScript', or some other
     * language. For potential data mining purposes.
     */
    public readonly clientType!: string;

    /**
     * Every generator Tile owned by this Player. (listed from the outer edges
     * inward, from top to bottom).
     */
    public readonly generatorTiles!: Tile[];

    /**
     * The amount of heat this Player has.
     */
    public readonly heat!: number;

    /**
     * The time left till a intern spawns. (0 to spawnTime).
     */
    public readonly internSpawn!: number;

    /**
     * If the player lost the game or not.
     */
    public readonly lost!: boolean;

    /**
     * The time left till a manager spawns. (0 to spawnTime).
     */
    public readonly managerSpawn!: number;

    /**
     * The name of the player.
     */
    public readonly name!: string;

    /**
     * This player's opponent in the game.
     */
    public readonly opponent!: Player;

    /**
     * The time left till a physicist spawns. (0 to spawnTime).
     */
    public readonly physicistSpawn!: number;

    /**
     * The amount of pressure this Player has.
     */
    public readonly pressure!: number;

    /**
     * The reason why the player lost the game.
     */
    public readonly reasonLost!: string;

    /**
     * The reason why the player won the game.
     */
    public readonly reasonWon!: string;

    /**
     * All the tiles this Player's units can spawn on. (listed from the outer
     * edges inward, from top to bottom).
     */
    public readonly spawnTiles!: Tile[];

    /**
     * The amount of time (in ns) remaining for this AI to send commands.
     */
    public readonly timeRemaining!: number;

    /**
     * Every Unit owned by this Player.
     */
    public readonly units!: Unit[];

    /**
     * If the player won the game or not.
     */
    public readonly won!: boolean;

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
