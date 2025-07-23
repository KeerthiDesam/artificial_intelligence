// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { GameObject } from "./game-object";
import { Player } from "./player";

/**
 * This is a collection of all the classes that Jungle uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        GameObject,
        Player,
    },
    gameVersion: "28f5663518c163e31771d87c52277b0c3f74033d97f89c1a234de5e6a15f6390",
};
