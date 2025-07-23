// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";
import { Player } from "./player";
import { Tile } from "./tile";
import { TowerJob } from "./tower-job";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A tower in the game. Used to combat enemy waves.
 */
export class Tower extends GameObject {

    /**
     * Whether this tower has attacked this turn or not.
     */
    public readonly attacked!: boolean;

    /**
     * How many turns are left before it can fire again.
     */
    public readonly cooldown!: number;

    /**
     * How much remaining health this tower has.
     */
    public readonly health!: number;

    /**
     * What type of tower this is (it's job).
     */
    public readonly job!: TowerJob;

    /**
     * The player that built / owns this tower.
     */
    public readonly owner!: Player | undefined;

    /**
     * The Tile this Tower is on.
     */
    public readonly tile!: Tile;

    /**
     * Attacks an enemy unit on an tile within it's range.
     * @param tile The Tile to attack.
     * @returns True if successfully attacked, false otherwise.
     */
    public async attack(tile: Tile): Promise<boolean> {
        return this.runOnServer("attack", {
            tile,
        });
    }

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
