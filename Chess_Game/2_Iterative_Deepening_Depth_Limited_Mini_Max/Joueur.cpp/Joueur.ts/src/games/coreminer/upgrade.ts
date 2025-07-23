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
 * Information about a Miner's Upgrade module.
 */
export class Upgrade extends GameObject {

    /**
     * The amount of cargo capacity this Upgrade has.
     */
    public readonly cargoCapacity!: number;

    /**
     * The maximum amount of health this Upgrade has.
     */
    public readonly health!: number;

    /**
     * The amount of mining power this Upgrade has per turn.
     */
    public readonly miningPower!: number;

    /**
     * The number of moves this Upgrade can make per turn.
     */
    public readonly moves!: number;

    /**
     * The Upgrade title.
     */
    public readonly title!: string;

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
