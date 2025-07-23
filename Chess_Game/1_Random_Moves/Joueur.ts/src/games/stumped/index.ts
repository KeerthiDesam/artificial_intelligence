// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { Beaver } from "./beaver";
import { GameObject } from "./game-object";
import { Job } from "./job";
import { Player } from "./player";
import { Spawner } from "./spawner";
import { Tile } from "./tile";

/**
 * This is a collection of all the classes that Stumped uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        Beaver,
        GameObject,
        Job,
        Player,
        Spawner,
        Tile,
    },
    gameVersion: "7de307cae4a9a163a9b3600cb20c4b376b9f9cc42f1b990852878fea0127eed3",
};
