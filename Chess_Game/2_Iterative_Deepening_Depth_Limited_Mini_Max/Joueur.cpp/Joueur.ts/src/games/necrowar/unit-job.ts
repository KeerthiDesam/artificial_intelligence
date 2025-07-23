// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * Information about a unit's job/type.
 */
export class UnitJob extends GameObject {

    /**
     * The amount of damage this type does per attack.
     */
    public readonly damage!: number;

    /**
     * How much does this type cost in gold.
     */
    public readonly goldCost!: number;

    /**
     * The amount of starting health this type has.
     */
    public readonly health!: number;

    /**
     * How much does this type cost in mana.
     */
    public readonly manaCost!: number;

    /**
     * The number of moves this type can make per turn.
     */
    public readonly moves!: number;

    /**
     * How many of this type of unit can take up one tile.
     */
    public readonly perTile!: number;

    /**
     * Amount of tiles away this type has to be in order to be effective.
     */
    public readonly range!: number;

    /**
     * The type title. 'worker', 'zombie', 'ghoul', 'hound', 'abomination',
     * 'wraith' or 'horseman'.
     */
    public readonly title!: "worker" | "zombie" | "ghoul" | "hound" | "abomination" | "wraith" | "horseman";

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
