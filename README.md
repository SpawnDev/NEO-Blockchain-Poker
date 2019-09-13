# NEO-Blockchain-Poker
Concept for P2P poker game relying soleily on NEO blockchain.

## Summary
Idea behind this is to create poker table that is ran by decentralized blockchain, without trusting 3rd party and their RNG algo.

## Explanation
Each player defines secret password (salt in this case) before starting the game. At this time, cards deck is in unshuffled order, A-K.

When the game starts (which could be scheduled by using blockchain height as time reference), server waits for 2 new blocks to be opened in order to prevent even NEO nodes from knowing the hash beforehand. 

![Before game starts](https://github.com/SpawnDev/NEO-Blockchain-Poker/blob/master/img/NewGame.png)

Latest hash, along with user passwords are used for random seed for Fisher–Yates shuffle algorithm. Thus, to minimize bias, hash itself  exceeds the number of permutations by at several orders of magnitude.

![Players dealt cards](https://github.com/SpawnDev/NEO-Blockchain-Poker/blob/master/img/GameStarted.png)

Now, that the game has started, each player is given their hand, and community cards can be turned without additional player interaction. Each of stages (Flop, Turn and River) can additionaly be secured against attacking the server by additionally shuffling the cards with new block hash after all players have checked.

![End Game](https://github.com/SpawnDev/NEO-Blockchain-Poker/blob/master/img/River.png)

## Dependencies
Addresses of nodes from which new block info is gathered are hardcoded in software. To make it more robust, it is contacting 7 official, public and well-known nodes, and chooses 3 of them that have concensus about latest block.

When small number of players are in game (Heads-up, for example), it could be possible to attack opposing players directly in order to acquire their seeds. If at least one seed is not known, whole game is safe from attacker.
