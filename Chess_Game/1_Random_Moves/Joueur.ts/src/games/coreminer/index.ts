// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { Bomb } from "./bomb";
import { GameObject } from "./game-object";
import { Miner } from "./miner";
import { Player } from "./player";
import { Tile } from "./tile";
import { Upgrade } from "./upgrade";

/**
 * This is a collection of all the classes that Coreminer uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        Bomb,
        GameObject,
        Miner,
        Player,
        Tile,
        Upgrade,
    },
    gameVersion: "b559778acd8e4c689b8d028ca6cc154714ce79c39b09cd6d171b50faf88ef747",
};
