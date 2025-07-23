// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { GameObject } from "./game-object";
import { Job } from "./job";
import { Player } from "./player";
import { Structure } from "./structure";
import { Tile } from "./tile";
import { Unit } from "./unit";

/**
 * This is a collection of all the classes that Catastrophe uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        GameObject,
        Job,
        Player,
        Structure,
        Tile,
        Unit,
    },
    gameVersion: "ede84ab86376b00287c09558f05e8f2a61b92109d93aad9ebd3379ff4215fb53",
};
