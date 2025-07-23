// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";
import { Job } from "./job";
import { Player } from "./player";
import { Tile } from "./tile";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A unit in the game. May be a manager, intern, or physicist.
 */
export class Unit extends GameObject {

    /**
     * Whether or not this Unit has performed its action this turn.
     */
    public readonly acted!: boolean;

    /**
     * The amount of blueium carried by this unit. (0 to job carry capacity -
     * other carried items).
     */
    public readonly blueium!: number;

    /**
     * The amount of blueium ore carried by this unit. (0 to job carry capacity
     * - other carried items).
     */
    public readonly blueiumOre!: number;

    /**
     * The remaining health of a unit.
     */
    public readonly health!: number;

    /**
     * The Job this Unit has.
     */
    public readonly job!: Job;

    /**
     * The number of moves this unit has left this turn.
     */
    public readonly moves!: number;

    /**
     * The Player that owns and can control this Unit.
     */
    public readonly owner!: Player | undefined;

    /**
     * The amount of redium carried by this unit. (0 to job carry capacity -
     * other carried items).
     */
    public readonly redium!: number;

    /**
     * The amount of redium ore carried by this unit. (0 to job carry capacity -
     * other carried items).
     */
    public readonly rediumOre!: number;

    /**
     * Duration of stun immunity. (0 to timeImmune).
     */
    public readonly stunImmune!: number;

    /**
     * Duration the unit is stunned. (0 to the game constant stunTime).
     */
    public readonly stunTime!: number;

    /**
     * The Tile this Unit is on.
     */
    public readonly tile!: Tile | undefined;

    /**
     * Makes the unit do something to a machine or unit adjacent to its tile.
     * Interns sabotage, physicists work. Interns stun physicist, physicist
     * stuns manager, manager stuns intern.
     * @param tile The tile the unit acts on.
     * @returns True if successfully acted, false otherwise.
     */
    public async act(tile: Tile): Promise<boolean> {
        return this.runOnServer("act", {
            tile,
        });
    }

    /**
     * Attacks a unit on an adjacent tile.
     * @param tile The Tile to attack.
     * @returns True if successfully attacked, false otherwise.
     */
    public async attack(tile: Tile): Promise<boolean> {
        return this.runOnServer("attack", {
            tile,
        });
    }

    /**
     * Drops materials at the units feet or adjacent tile.
     * @param tile The tile the materials will be dropped on.
     * @param amount The number of materials to dropped. Amounts <= 0 will drop
     * all the materials.
     * @param material The material the unit will drop. 'redium', 'blueium',
     * 'redium ore', or 'blueium ore'.
     * @returns True if successfully deposited, false otherwise.
     */
    public async drop(
        tile: Tile,
        amount: number,
        material: "redium ore" | "redium" | "blueium" | "blueium ore",
    ): Promise<boolean> {
        return this.runOnServer("drop", {
            tile,
            amount,
            material,
        });
    }

    /**
     * Moves this Unit from its current Tile to an adjacent Tile.
     * @param tile The Tile this Unit should move to.
     * @returns True if it moved, false otherwise.
     */
    public async move(tile: Tile): Promise<boolean> {
        return this.runOnServer("move", {
            tile,
        });
    }

    /**
     * Picks up material at the units feet or adjacent tile.
     * @param tile The tile the materials will be picked up from.
     * @param amount The amount of materials to pick up. Amounts <= 0 will pick
     * up all the materials that the unit can.
     * @param material The material the unit will pick up. 'redium', 'blueium',
     * 'redium ore', or 'blueium ore'.
     * @returns True if successfully deposited, false otherwise.
     */
    public async pickup(
        tile: Tile,
        amount: number,
        material: "redium ore" | "redium" | "blueium" | "blueium ore",
    ): Promise<boolean> {
        return this.runOnServer("pickup", {
            tile,
            amount,
            material,
        });
    }

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
