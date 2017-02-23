# multiAgents.py
# --------------
# Licensing Information: Please do not distribute or publish solutions to this
# project. You are free to use and extend these projects for educational
# purposes. The Pacman AI projects were developed at UC Berkeley, primarily by
# John DeNero (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# For more info, see http://inst.eecs.berkeley.edu/~cs188/sp09/pacman.html

from util import manhattanDistance
from game import Directions
import random
import util

from game import Agent


class ReflexAgent(Agent):
    """
      A reflex agent chooses an action at each choice point by examining
      its alternatives via a state evaluation function.

      The code below is provided as a guide.  You are welcome to change
      it in any way you see fit, so long as you don't touch our method
      headers.
    """

    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {North, South, West, East, Stop}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(
            gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[
            index] == bestScore]
        # Pick randomly among the best
        chosenIndex = random.choice(bestIndices)

        "Add more of your code here if you want to"
        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (oldFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        oldFood = currentGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [
            ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        safetyZone = 1
        # Check for ghosts, then food
        # If pacman is close to a ghost, avoid this position at all costs:
        for ghost in newGhostStates:
            ghostDistance = manhattanDistance(newPos, ghost.getPosition())
            if ghostDistance <= safetyZone:
                # Only if the ghost is not scared:
                if ghost.scaredTimer == 0:
                    return -9999
                elif ghost.scaredTimer > safetyZone + 1:
                    return 9999

        # Disincentivise staying in the same spot
        if currentGameState.getPacmanPosition() == newPos:
            return -9999

        # Otherwise If the next state has food, prefer it
        x, y = newPos
        if oldFood[x][y]:
            return 2000
        tax = 0
        # Apply a tax on positions that have fewer legal options (corners,
        # trap-states)
        safeMoves = len(newGhostStates) - \
            len(successorGameState.getLegalActions()) + 1
        if safeMoves <= 0:
            tax = 5 * safeMoves
        # return (1 - tax) / min([manhattanDistance(newPos, food) for food in
        # oldFood])

        # Return the reciprical of the average distance to food, with the tax
        averageDistance = 0
        numFood = 0
        oldFoodList = oldFood.asList()
        for food in oldFoodList:
            numFood = numFood + 1
            averageDistance += manhattanDistance(newPos, food)
        averageDistance /= numFood
        return (1.0 - tax) / averageDistance


def scoreEvaluationFunction(currentGameState):
    """
      This default evaluation function just returns the score of the state.
      The score is the same one displayed in the Pacman GUI.

      This evaluation function is meant for use with adversarial search agents
      (not reflex agents).
    """
    return currentGameState.getScore()


class MultiAgentSearchAgent(Agent):
    """
      This class provides some common elements to all of your
      multi-agent searchers.  Any methods defined here will be available
      to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

      You *do not* need to make any changes here, but you can if you want to
      add functionality to all your adversarial search agents.  Please do not
      remove anything, however.

      Note: this is an abstract class: one that should not be instantiated.  It's
      only partially specified, and designed to be extended.  Agent (game.py)
      is another abstract class.
    """

    def __init__(self, evalFn='scoreEvaluationFunction', depth='2'):
        self.index = 0  # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)


class MinimaxAgent(MultiAgentSearchAgent):
    """
      Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action from the current gameState using self.depth
          and self.evaluationFunction.

          Here are some method calls that might be useful when implementing minimax.

          gameState.getLegalActions(agentIndex):
            Returns a list of legal actions for an agent
            agentIndex=0 means Pacman, ghosts are >= 1

          Directions.STOP:
            The stop direction, which is always legal

          gameState.generateSuccessor(agentIndex, action):
            Returns the successor game state after an agent takes an action

          gameState.getNumAgents():
            Returns the total number of agents in the game
        """
        "*** YOUR CODE HERE ***"
        def alpha(gameState, depth):
            # If it is at maximum depth treat it as terminal and return the
            # utility
            if depth == self.depth:
                return (None, self.evaluationFunction(gameState))

                # If it is a terminal state, return the utility
            actions = gameState.getLegalActions()
            if len(actions) == 0:
                return (None, self.evaluationFunction(gameState))

            # Generate sucessor scores for the next players
            alphaScore = -9999
            alphaAction = None

            for action in actions:
                # Generate the score for the next player
                successorScore = beta(
                    gameState.generateSuccessor(0, action), depth, 1)[1]
                # Keep the maximum of the scores generated by the next players
                if alphaScore < successorScore:
                    alphaScore = successorScore
                    alphaAction = action

            return (alphaAction, alphaScore)

        def beta(gameState, depth, player):
            # If it is a terminal state, return the utility
            actions = gameState.getLegalActions(player)
            if len(actions) == 0:
                return (None, self.evaluationFunction(gameState))

            betaScore = 9999
            betaAction = None

            # Generate sucessor scores for the next players
            for action in actions:
                # Generate the score for the next player
                if (player == gameState.getNumAgents() - 1):
                    successorScore = alpha(gameState.generateSuccessor(
                        player, action), depth + 1)[1]
                else:
                    successorScore = beta(gameState.generateSuccessor(
                        player, action), depth,  player + 1)[1]
                # Keep the minimum of the scores generated by the next players
                if betaScore > successorScore:
                    betaScore = successorScore
                    betaAction = action

            return (betaAction, betaScore)

        action, score = alpha(gameState, 0)
        return action


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
      Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        def alpha(gameState, depth, alpha_beta):
                # If it is at maximum depth treat it as terminal and return the
                # utility
            if depth == self.depth:
                return (None, self.evaluationFunction(gameState))

                # If it is a terminal state, return the utility
            actions = gameState.getLegalActions()
            if len(actions) == 0:
                return (None, self.evaluationFunction(gameState))

            # Generate sucessor scores for the next players
            alphaScore = -9999
            alphaAction = None
            a, b = alpha_beta

            for action in actions:
                # If alpha is greater than beta, then we can skip the rest of
                # the subtree since alpha would be worse off choosing this
                # path, and would never do it
                if a > b:
                    return alphaAction, alphaScore
                # Generate the score for the next player
                successorScore = beta(
                    gameState.generateSuccessor(0, action), depth, 1, (a, b))[1]
                # Keep the maximum of the scores generated by the next
                # players
                if alphaScore < successorScore:
                    alphaScore = successorScore
                    alphaAction = action
                # if b <= alphaScore:
                #     return (action, alphaScore)
                if a < alphaScore:
                    a = alphaScore

            return (alphaAction, alphaScore)

        def beta(gameState, depth, player, alpha_beta):
            # If it is a terminal state, return the utility
            actions = gameState.getLegalActions(player)
            if len(actions) == 0:
                return (None, self.evaluationFunction(gameState))

            betaScore = 9999
            betaAction = None
            a, b = alpha_beta

            # Generate sucessor scores for the next players
            for action in actions:
                # a/b pruning:
                # If alpha is greater than beta, then we can skip the rest of
                # the subtree since alpha would be worse off choosing this
                # path, and would never do it
                if a > b:
                    return betaAction, betaScore
                # Generate the score for the next player
                if (player == gameState.getNumAgents() - 1):
                    successorScore = alpha(gameState.generateSuccessor(
                        player, action), depth + 1, (a, b))[1]
                else:
                    successorScore = beta(gameState.generateSuccessor(
                        player, action), depth,  player + 1, (a, b))[1]
                # Keep the minimum of the scores generated by the next
                # players
                if betaScore > successorScore:
                    betaScore = successorScore
                    betaAction = action
                # if a >= betaScore:
                #     return (betaAction, betaScore)
                if b > betaScore:
                    b = betaScore

            return (betaAction, betaScore)

        action, score = alpha(gameState, 0, (-9999, 9999))
        return action


class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
          Returns the expectimax action using self.depth and self.evaluationFunction

          All ghosts should be modeled as choosing uniformly at random from their
          legal moves.
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()


def betterEvaluationFunction(currentGameState):
    """
      Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
      evaluation function (question 5).

      DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

# Abbreviation
better = betterEvaluationFunction


class ContestAgent(MultiAgentSearchAgent):
    """
      Your agent for the mini-contest
    """

    def getAction(self, gameState):
        """
          Returns an action.  You can use any method you want and search to any depth you want.
          Just remember that the mini-contest is timed, so you have to trade off speed and computation.

          Ghosts don't behave randomly anymore, but they aren't perfect either -- they'll usually
          just make a beeline straight towards Pacman (or away from him if they're scared!)
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()
