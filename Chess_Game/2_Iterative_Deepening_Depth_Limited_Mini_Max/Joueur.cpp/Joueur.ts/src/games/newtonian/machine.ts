// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";
import { Tile } from "./tile";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A machine in the game. Used to refine ore.
 */
export class Machine extends GameObject {

    /**
     * What type of ore the machine takes it. Also determines the type of
     * material it outputs. (redium or blueium).
     */
    public readonly oreType!: "redium" | "blueium";

    /**
     * The amount of ore that needs to be inputted into the machine for it to be
     * worked.
     */
    public readonly refineInput!: number;

    /**
     * The amount of refined ore that is returned after the machine has been
     * fully worked.
     */
    public readonly refineOutput!: number;

    /**
     * The number of times this machine needs to be worked to refine ore.
     */
    public readonly refineTime!: number;

    /**
     * The Tile this Machine is on.
     */
    public readonly tile!: Tile;

    /**
     * Tracks how many times this machine has been worked. (0 to refineTime).
     */
    public readonly worked!: number;

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
