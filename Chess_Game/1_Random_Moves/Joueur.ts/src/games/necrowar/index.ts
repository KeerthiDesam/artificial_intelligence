// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { GameObject } from "./game-object";
import { Player } from "./player";
import { Tile } from "./tile";
import { Tower } from "./tower";
import { TowerJob } from "./tower-job";
import { Unit } from "./unit";
import { UnitJob } from "./unit-job";

/**
 * This is a collection of all the classes that Necrowar uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        GameObject,
        Player,
        Tile,
        Tower,
        TowerJob,
        Unit,
        UnitJob,
    },
    gameVersion: "935f0e64ba290cdce31688a40bd90d1eb5375f36aeebd67482238fc0da25ef86",
};
