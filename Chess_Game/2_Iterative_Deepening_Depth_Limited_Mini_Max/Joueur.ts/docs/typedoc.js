module.exports = {
    "name": "TypeScript Client Docs",
    "tsconfig": "./tsconfig.json",
    "out": "./output",
    "mode": "modules",
    "module": "esnext",
    "exclude": "src/joueur/*.ts",
    "excludeExternals": true,
    "excludePrivate": true,
    "externalPattern": "*",
    "ignoreCompilerErrors": true
};
