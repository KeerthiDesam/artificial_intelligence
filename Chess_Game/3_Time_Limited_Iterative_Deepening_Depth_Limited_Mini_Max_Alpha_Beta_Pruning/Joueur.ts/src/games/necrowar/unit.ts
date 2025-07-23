// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";
import { Player } from "./player";
import { Tile } from "./tile";
import { UnitJob } from "./unit-job";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A unit in the game. May be a worker, zombie, ghoul, hound, abomination,
 * wraith or horseman.
 */
export class Unit extends GameObject {

    /**
     * Whether or not this Unit has performed its action this turn (attack or
     * build).
     */
    public readonly acted!: boolean;

    /**
     * The remaining health of a unit.
     */
    public readonly health!: number;

    /**
     * The type of unit this is.
     */
    public readonly job!: UnitJob;

    /**
     * The number of moves this unit has left this turn.
     */
    public readonly moves!: number;

    /**
     * The Player that owns and can control this Unit.
     */
    public readonly owner!: Player | undefined;

    /**
     * The Tile this Unit is on.
     */
    public readonly tile!: Tile | undefined;

    /**
     * Attacks an enemy tower on an adjacent tile.
     * @param tile The Tile to attack.
     * @returns True if successfully attacked, false otherwise.
     */
    public async attack(tile: Tile): Promise<boolean> {
        return this.runOnServer("attack", {
            tile,
        });
    }

    /**
     * Unit, if it is a worker, builds a tower on the tile it is on, only
     * workers can do this.
     * @param title The tower type to build, as a string.
     * @returns True if successfully built, false otherwise.
     */
    public async build(title: string): Promise<boolean> {
        return this.runOnServer("build", {
            title,
        });
    }

    /**
     * Stops adjacent to a river tile and begins fishing for mana.
     * @param tile The tile the unit will stand on as it fishes.
     * @returns True if successfully began fishing for mana, false otherwise.
     */
    public async fish(tile: Tile): Promise<boolean> {
        return this.runOnServer("fish", {
            tile,
        });
    }

    /**
     * Enters a mine and is put to work gathering resources.
     * @param tile The tile the mine is located on.
     * @returns True if successfully entered mine and began mining, false
     * otherwise.
     */
    public async mine(tile: Tile): Promise<boolean> {
        return this.runOnServer("mine", {
            tile,
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

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
