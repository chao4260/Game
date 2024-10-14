# Game

## FR
Ceci est un jeu de dés avec des questions à répondre sur un map de 12 cases.
Le résultat du dés détermine quel case tu arrives et quel catégorie de question tu devra répondre.
Une bonne réponse va te permettre de gagner un gold.
Une mauvaise réponse va te mettre dans le penalty box jusqu'à que tu lances un nombre impair.
Le but est de répondre aux questions et de gagner des golds jusqu'à atteindre le nombre de gold gagnant.
Il y a 4 catégories de questions: Pop, Science, Sports et Rock.


## EN
This is a dice game with questions to answer on a map with 12 places.
The result of the dice determines which place you reach and which category of question you have to answer.
A correct answer earns you a gold. 
A wrong answer puts you in the penalty box until you roll an odd number.
The aim is to answer the questions and earn golds until someone reaches the winning number of golds.
There are 4 categories of questions: Pop, Science, Sports and Rock.

## Anomalies détectées
- Le nombre de joueurs peut dépasser le nombre de place dans la méthode `add()`
- `howManyPlayers()` est appelé après d'avoir ajouté un joueur donc le joueur 0 n'existe pas
- `add()` ne limite pas le nombre de joueur, on peut dépasser la nombre maximum de joueur
- le joueur une fois entrée dans le penaltybox ne resort plus
- typo : `Answer was corrent!!!!`
- le premier joueur qui répond correctement à la première question gagne car `didPlayerWin()` retourne true si le joueur n'a pas 6 golds
- `wasCorrectlyAnswered()` contient du code mort car il gère `isGettingOutOfPenaltyBox=false` alors que une personne qui est dans le penalty box n'a pas de question à répondre
- `isGettingOutOfPenaltyBox` n'est pas nécessaire car on sait si le joueur est dans le penalty box ou pas grâce à la table `inPenaltyBox`. Si il n'est pas sorti, il n'a pas de question à répondre. Si il est sorti, il répond à la question comme les autres joueurs.
- `wrongAnswer()` return toujours true, résultat non nécessaire
- le jeu est bloqué quand un joueur est entrée dans le penalty box car il n'a pas de question à répondre mais il faut soit un wrongAnswer() soit wasCorrectlyAnswered() pour continuer le jeu