// DO NOT MODIFY THIS FILE

import { IGameNamespace } from "../../joueur/interfaces";
import { AI } from "./ai";
import { Game } from "./game";

import { Body } from "./body";
import { GameObject } from "./game-object";
import { Job } from "./job";
import { Player } from "./player";
import { Projectile } from "./projectile";
import { Unit } from "./unit";

/**
 * This is a collection of all the classes that Stardash uses to work.
 * @hidden
 */
export const namespace: IGameNamespace = {
    AI,
    Game,
    GameObjectClasses: {
        Body,
        GameObject,
        Job,
        Player,
        Projectile,
        Unit,
    },
    gameVersion: "0fa378e83ac567ebdf3e9805d3f130023f936e2740acda173d238b37f2b5d541",
};
