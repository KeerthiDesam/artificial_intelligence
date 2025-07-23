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
 * Information about a unit's job.
 */
export class Job extends GameObject {

    /**
     * How many combined resources a unit with this Job can hold at once.
     */
    public readonly carryLimit!: number;

    /**
     * The amount of damage this Job does per attack.
     */
    public readonly damage!: number;

    /**
     * The amount of starting health this Job has.
     */
    public readonly health!: number;

    /**
     * The number of moves this Job can make per turn.
     */
    public readonly moves!: number;

    /**
     * The Job title. 'intern', 'manager', or 'physicist'.
     */
    public readonly title!: "intern" | "manager" | "physicist";

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
