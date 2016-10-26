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

        # Otherwise If the next state has food, prefer it
        x, y = newPos
        if oldFood[x][y]:
            return 2000
        tax = 0
        # Apply a tax on positions that have fewer legal options (corners,
        # trap-states)
        safeMoves = len(newGhostStates) - len(successorGameState.getLegalActions()) + 1
        if safeMoves <= 0:
            tax = 5 * safeMoves
        # return (1 - tax) / min([manhattanDistance(newPos, food) for food in
        # oldFood])

        # Return the reciprical of the average distance to food, with the tax
        averageDistance = 0
        numFood = 0
        for food in oldFood:
            numFood = numFood + 1
            averageDistance += manhattanDistance(newPos, food)
        averageDistance /= numFood
        return (1 - tax) / averageDistance


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
        def alpha(state, depth):
            # If it is a terminal stete, return the utility
            depth = depth + 1
            if depth == self.depth or state.isWin() or state.isLose():
                return self.evaluationFunction(state)
            else:
                # Generate sucessor scores for the next players
                alphaScore = -9999
                for action in state.getLegalActions():
                    if action != Directions.STOP:
                        # Generate the score for the next player
                        successorScore = beta(
                            state.generateSuccessor(0, action), depth, 1)
                        # Keep the maximum of the scores generated by the next
                        # players
                        if alphaScore < successorScore:
                            alphaScore = successorScore

                return alphaScore

        def beta(state, depth, player):
            # If it is a terminal stete, return the utility
            if state.isWin() or state.isLose():
                return self.evaluationFunction(state)
            else:
                # Generate sucessor states for the next players
                betaScore = 9999
                for action in state.getLegalActions(player):
                    if action != Directions.STOP:
                        # Generate the score for the next player
                        if player == state.getNumAgents() - 1:
                            successorScore = alpha(
                                state.generateSuccessor(player, action), depth)
                        else:
                            successorScore = beta(state.generateSuccessor(
                                player, action), depth, player + 1)
                        # Keep the minimum of the scores generated by the next
                        # players
                        if betaScore > successorScore:
                            betaScore = successorScore

                return betaScore

        tempActions = []
        scores = []

        # For each admissible action (not STOP), generate a score
        for action in gameState.getLegalActions():
            if action != Directions.STOP:
                tempActions.append(action)
                scores.append(alpha(gameState.generateSuccessor(0, action), 0))

        # Find the highest score
        bestScore = max(scores)
        # And get the indices of all that match it
        bestIndices = [index for index in range(len(scores)) if scores[
            index] == bestScore]
        # Pick randomly among the best
        chosenIndex = random.choice(bestIndices)
        # Return the action that accompanied the chosen maximum score
        return tempActions[chosenIndex]


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
      Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        def alpha(state, depth, alpha_beta):
            a, b = alpha_beta
            # If it is a terminal stete, return the utility
            depth = depth + 1
            if depth == self.depth or state.isWin() or state.isLose():
                return self.evaluationFunction(state)
            else:
                # Generate sucessor scores for the next players
                maxScore = -9999
                for action in state.getLegalActions():
                    if action != Directions.STOP:
                        # Generate the score for the next player
                        successorScore = beta(state.generateSuccessor(
                            0, action), depth, 1, (a, b))
                        # Keep the maximum of the scores generated by the next
                        # players
                        if maxScore < successorScore:
                            maxScore = successorScore
                        # "Prune" the remainder of the sucessor scores if the beta score is less than or equal to any sucessor score
                        if b <= maxScore:
                            return maxScore
                        if a < maxScore:
                            a = maxScore

                return maxScore

        def beta(state, depth, player, alpha_beta):
            a, b = alpha_beta
            # If it is a terminal stete, return the utility
            if state.isWin() or state.isLose():
                return self.evaluationFunction(state)
            else:
                # Generate sucessor states for the next players
                minScore = 9999
                for action in state.getLegalActions(player):
                    if action != Directions.STOP:
                        # Generate the score for the next player
                        if player == state.getNumAgents() - 1:
                            successorScore = alpha(state.generateSuccessor(
                                player, action), depth, (a, b))
                        else:
                            successorScore = beta(state.generateSuccessor(
                                player, action), depth, player + 1, (a, b))
                        # Keep the minimum of the scores generated by the next
                        # players
                        if minScore > successorScore:
                            minScore = successorScore
                        # "Prune" the remainder of the sucessor scores if the alpha score is greater than or equal to any sucessor score
                        if a >= minScore:
                            return minScore
                        if b > minScore:
                            b = minScore

                return minScore

        tempActions = []
        scores = []

        # For each admissible action (not STOP), generate a score
        for action in gameState.getLegalActions():
            if action != Directions.STOP:
                tempActions.append(action)
                scores.append(
                    alpha(gameState.generateSuccessor(0, action), 0, (-9999, 9999)))

        # Find the highest score
        bestScore = max(scores)
        # And get the indices of all that match it
        bestIndices = [index for index in range(len(scores)) if scores[
            index] == bestScore]
        # Pick randomly among the best
        chosenIndex = random.choice(bestIndices)
        # Return the action that accompanied the chosen maximum score
        return tempActions[chosenIndex]


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
