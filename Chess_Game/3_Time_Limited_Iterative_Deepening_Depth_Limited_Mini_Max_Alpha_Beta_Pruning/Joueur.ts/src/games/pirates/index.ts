// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { GameObject } from "./game-object";
import { Player } from "./player";
import { Port } from "./port";
import { Tile } from "./tile";
import { Unit } from "./unit";

/**
 * This is a collection of all the classes that Pirates uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        GameObject,
        Player,
        Port,
        Tile,
        Unit,
    },
    gameVersion: "d51fca49d06cb7164f9dbf9c3515ab0f9b5a17113a5946bddcc75aaba125967f",
};
