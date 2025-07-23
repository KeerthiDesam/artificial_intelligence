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
 * Information about a tower's job/type.
 */
export class TowerJob extends GameObject {

    /**
     * Whether this tower type hits all of the units on a tile (true) or one at
     * a time (false).
     */
    public readonly allUnits!: boolean;

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
     * The number of tiles this type can attack from.
     */
    public readonly range!: number;

    /**
     * The type title. 'arrow', 'aoe', 'ballista', 'cleansing', or 'castle'.
     */
    public readonly title!: "arrow" | "aoe" | "ballista" | "cleansing" | "castle";

    /**
     * How many turns have to take place between this type's attacks.
     */
    public readonly turnsBetweenAttacks!: number;

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
