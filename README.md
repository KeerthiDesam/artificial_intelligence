# artificial_intelligence
This repository consists of projects about Artificial Intelligence

Overview:
This repository contains my solutions to a series of puzzle-based assignments for the Pengu Game and Chess game, developed as part of the CS5400 – Artificial Intelligence course.

Pengu Puzzle AI Solver
Pengu is a sliding puzzle game where a penguin must collect fish on an ice board while avoiding hazards. Each assignment implemented a different search strategy to solve increasingly complex goals.

Pengu Assignment Overview
1. Puzzle Assignment #1 – Game Simulation
-Implemented the base game logic for Pengu’s movement across an icy grid.
-Simulated turns, sliding mechanics, and collision with hazards/walls.
-Developed a custom game state representation including terrain, hazards, and fish.

2. Puzzle Assignment #2 – Breadth-First Search (BFS)
-Applied BFS to find a path that achieves at least 8 points.
-Developed a transition function and game tree traversal to explore states layer-by-layer.
-Output included move sequences, final score, and the resulting board.

3. Puzzle Assignment #3 – Iterative Deepening Depth-First Search (ID-DFS)
-Implemented depth-limited DFS with iterative increases in depth.
-Found optimal move sequences to score at least 16 points.
-Avoided memory-intensive deep traversal while maintaining completeness.

4. Puzzle Assignment #4 – Greedy Best-First Search (GBFS)
-Integrated a heuristic function to guide search toward the goal.
-Designed priority-based node exploration using estimated distance to remaining fish.
-Achieved scores of 20+ efficiently using informed search.

5. Puzzle Assignment #5 – A Graph Search (AGS)**
-Combined path cost and heuristics in A* to guide search to collect all fish without dying.
-Tuned f(s) = cost(s) + h(s) function to explore shortest and safest winning paths.
-Demonstrated the most optimized and goal-directed search implementation.


Chess Game:
Designed an AI player for Chess using iterative-deepening minimax algorithms, incorporating advanced techniques such as Alpha-Beta pruning, Quiescent search,and additional features like transposition tables, move ordering, history tables, or killer moves.
