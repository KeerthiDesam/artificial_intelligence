// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { GameObject } from "./game-object";
import { Job } from "./job";
import { Machine } from "./machine";
import { Player } from "./player";
import { Tile } from "./tile";
import { Unit } from "./unit";

/**
 * This is a collection of all the classes that Newtonian uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        GameObject,
        Job,
        Machine,
        Player,
        Tile,
        Unit,
    },
    gameVersion: "7c19f909ee5faa0ac3faf4e989032b5a37ba94aeb5d6ae7654a15a2bb1401bbe",
};
