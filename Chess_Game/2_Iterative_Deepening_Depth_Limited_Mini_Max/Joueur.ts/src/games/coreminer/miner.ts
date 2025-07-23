// DO NOT MODIFY THIS FILE
// Never try to directly create an instance of this class, or modify it.
// Instead, you should only be reading its variables and calling its functions.

/* tslint:disable */
// This is code written by a computer, it might be funky.
// (though we will try to make it readable to humans)

import { GameObject } from "./game-object";
import { Player } from "./player";
import { Tile } from "./tile";
import { Upgrade } from "./upgrade";

// <<-- Creer-Merge: imports -->>
// any additional imports you want can be placed here safely between creer runs
// <<-- /Creer-Merge: imports -->>

/**
 * A Miner in the game.
 */
export class Miner extends GameObject {

    /**
     * The number of bombs being carried by this Miner.
     */
    public readonly bombs!: number;

    /**
     * The number of building materials carried by this Miner.
     */
    public readonly buildingMaterials!: number;

    /**
     * The Upgrade this Miner is on.
     */
    public readonly currentUpgrade!: Upgrade;

    /**
     * The amount of dirt carried by this Miner.
     */
    public readonly dirt!: number;

    /**
     * The remaining health of this Miner.
     */
    public readonly health!: number;

    /**
     * The remaining mining power this Miner has this turn.
     */
    public readonly miningPower!: number;

    /**
     * The number of moves this Miner has left this turn.
     */
    public readonly moves!: number;

    /**
     * The amount of ore carried by this Miner.
     */
    public readonly ore!: number;

    /**
     * The Player that owns and can control this Miner.
     */
    public readonly owner!: Player;

    /**
     * The Tile this Miner is on.
     */
    public readonly tile!: Tile | undefined;

    /**
     * The upgrade level of this Miner. Starts at 0.
     */
    public readonly upgradeLevel!: number;

    /**
     * Builds a support, shield, or ladder on Miner's Tile, or an adjacent Tile.
     * @param tile The Tile to build on.
     * @param type The structure to build (support, ladder, or shield).
     * @returns True if successfully built, False otherwise.
     */
    public async build(
        tile: Tile,
        type: "support" | "ladder" | "shield",
    ): Promise<boolean> {
        return this.runOnServer("build", {
            tile,
            type,
        });
    }

    /**
     * Purchase a resource from the Player's base or hopper.
     * @param resource The type of resource to buy.
     * @param amount The amount of resource to buy. Amounts <= 0 will buy all of
     * that material Player can.
     * @returns True if successfully purchased, false otherwise.
     */
    public async buy(
        resource: "dirt" | "ore" | "bomb" | "buildingMaterials",
        amount: number,
    ): Promise<boolean> {
        return this.runOnServer("buy", {
            resource,
            amount,
        });
    }

    /**
     * Dumps materials from cargo to an adjacent Tile. If the Tile is a base or
     * a hopper Tile, materials are sold instead of placed.
     * @param tile The Tile the materials will be dumped on.
     * @param material The material the Miner will drop. 'dirt', 'ore', or
     * 'bomb'.
     * @param amount The number of materials to drop. Amounts <= 0 will drop all
     * of the material.
     * @returns True if successfully dumped materials, false otherwise.
     */
    public async dump(
        tile: Tile,
        material: "dirt" | "ore" | "bomb",
        amount: number,
    ): Promise<boolean> {
        return this.runOnServer("dump", {
            tile,
            material,
            amount,
        });
    }

    /**
     * Mines the Tile the Miner is on or an adjacent Tile.
     * @param tile The Tile the materials will be mined from.
     * @param amount The amount of material to mine up. Amounts <= 0 will mine
     * all the materials that the Miner can.
     * @returns True if successfully mined, false otherwise.
     */
    public async mine(tile: Tile, amount: number): Promise<boolean> {
        return this.runOnServer("mine", {
            tile,
            amount,
        });
    }

    /**
     * Moves this Miner from its current Tile to an adjacent Tile.
     * @param tile The Tile this Miner should move to.
     * @returns True if it moved, false otherwise.
     */
    public async move(tile: Tile): Promise<boolean> {
        return this.runOnServer("move", {
            tile,
        });
    }

    /**
     * Transfers a resource from the one Miner to another.
     * @param miner The Miner to transfer materials to.
     * @param resource The type of resource to transfer.
     * @param amount The amount of resource to transfer. Amounts <= 0 will
     * transfer all the of the material.
     * @returns True if successfully transferred, false otherwise.
     */
    public async transfer(
        miner: Miner,
        resource: "dirt" | "ore" | "bomb" | "buildingMaterials",
        amount: number,
    ): Promise<boolean> {
        return this.runOnServer("transfer", {
            miner,
            resource,
            amount,
        });
    }

    /**
     * Upgrade this Miner by installing an upgrade module.
     * @returns True if successfully upgraded, False otherwise.
     */
    public async upgrade(): Promise<boolean> {
        return this.runOnServer("upgrade", {
        });
    }

    // <<-- Creer-Merge: functions -->>
    // any additional functions you want to add to this class can be preserved here
    // <<-- /Creer-Merge: functions -->>
}
