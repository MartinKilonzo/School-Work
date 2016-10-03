# search.py
# ---------
# Licensing Information: Please do not distribute or publish solutions to this
# project. You are free to use and extend these projects for educational
# purposes. The Pacman AI projects were developed at UC Berkeley, primarily by
# John DeNero (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# For more info, see http://inst.eecs.berkeley.edu/~cs188/sp09/pacman.html

"""
In search.py, you will implement generic search algorithms which are called
by Pacman agents (in searchAgents.py).
"""

import util


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples,
        (successor, action, stepCost), where 'successor' is a
        successor to the current state, 'action' is the action
        required to get there, and 'stepCost' is the incremental
        cost of expanding to that successor
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.  The sequence must
        be composed of legal moves
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other
    maze, the sequence of moves will be incorrect, so only use this for tinyMaze
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]


def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first [p 85].

    Your search algorithm needs to return a list of actions that reaches
    the goal.  Make sure to implement a graph search algorithm [Fig. 3.7].

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())
    """
    "*** YOUR CODE HERE ***"

    """
    Implementation of recursive DFS, where the path is returned as a stack, if
    one exists. Nodes are marked as visited using a 2D array representation of
    the problem graph (nodeHistory), where a node's x and y coordinates map to
    the 2D array.
    """
    from util import Stack, dynamicMatrix

    # Constants for readability
    COORDS = 0
    DIRECTION = 1
    X = 0
    Y = 1

    global goalStateFound, graph, path
    goalStateFound = False
    graph = dynamicMatrix()
    path = Stack()

    def DFS(n):
        global goalStateFound
        node = n[COORDS]
        graph.insert(node[COORDS], node[DIRECTION], True)  # mark n
        if problem.isGoalState(node):  # if n is goalState,
            goalStateFound = True   # set goalStateFound as true
            path.push(n[DIRECTION])  # add the goal state to the path
        else:
            for (i, successor) in enumerate(problem.getSuccessors(node)):  # for all successors of n
                if graph.get(successor[COORDS][X], successor[COORDS][Y]) == None:
                    DFS(successor)  # dfs(m)
                    if goalStateFound:  # if goalStateFound,
                        path.push(n[DIRECTION])  # push n to stack
                        return  # return

    DFS((problem.getStartState(), 'None', 0))
    path.pop()  # Remove the start instruction from the stack
    path.list.reverse()  # Reverse the stack as instructions are read in incremental index order
    return path.list


def breadthFirstSearch(problem):
    "Search the shallowest nodes in the search tree first. [p 81]"
    "*** YOUR CODE HERE ***"
    """
    Implements BFS by traversing the graph in "level-order" using a Queue,
    returning a path to the goal if one exists.
    """
    from util import Queue

    # Constants for readability
    STATE = 0
    DIRECTION = 1

    # A list containing the states already encountered
    nodeHistory = []
    # A list used to store the nodes to explore
    nodeQueue = Queue()

    # Start by enqueueing the start, along with an empty path
    nodeQueue.push(((problem.getStartState(), 'None', 0), []))

    # Iterate through the queue
    while not nodeQueue.isEmpty():
        # Get the parent node, and the path to it
        node, path = nodeQueue.pop()
        # Seperate the parent state for successor query
        parent = node[STATE]
        # For each child of the parent state,
        for i, child in enumerate(problem.getSuccessors(parent)):
            # Check to see if the child has been explored
            if not child[STATE] in nodeHistory:
                # If no,
                # Check to see if the goal state has been reached
                if problem.isGoalState(child[STATE]):
                    path = path + [child[DIRECTION]]
                    return path
                # Otherwise,
                else:
                    # Mark the node as explored
                    nodeQueue.push((child, path + [child[DIRECTION]]))
                    # And enqueue the child
                    nodeHistory.append(child[STATE])

    # Return an empty list if no path can be found
    return []



def uniformCostSearch(problem):
    "Search the node of least total cost first. "
    "*** YOUR CODE HERE ***"
    """
    Implements UCS using BFS and a min-piority queue to prioritize expanding the
    cheaper nodes first. Returns a path if one exists, or an empty list if one
    cannot be found.
    """
    from util import PriorityQueue

    # Constants for readability
    STATE = 0
    DIRECTION = 1

    # A list containing the states already encountered
    nodeHistory = []
    # A list used to store the nodes to explore
    nodeQueue = PriorityQueue()

    # Start by enqueueing the start, along with an empty path
    # and its cost of 0
    nodeQueue.push(((problem.getStartState(), 'None'), []), 0)

    # Iterate through the queue
    while not nodeQueue.isEmpty():
        # Get the parent node, and the path to it
        node, path = nodeQueue.pop()
        # Seperate the parent state for successor query
        parent = node[STATE]
        # For each child of the parent state,
        for i, child in enumerate(problem.getSuccessors(parent)):
            # Check to see if the child has been explored
            if not child[STATE] in nodeHistory:
                # If no,
                # Check to see if the goal state has been reached
                if problem.isGoalState(child[STATE]):
                    path = path + [child[DIRECTION]]
                    return path
                # Otherwise,
                else:
                    # Mark the node as explored
                    actions = path + [child[DIRECTION]]
                    # As our min-priority queue prioritizes min-cost paths,
                    # An identical state that may exist in the queue with a
                    # higher path cost will always be explored after a
                    # cheaper one
                    nodeQueue.push((child, actions), problem.getCostOfActions(actions))
                    # And enqueue the child
                    nodeHistory.append(child[STATE])

    # Return an empty list if no path can be found
    return []



def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def aStarSearch(problem, heuristic=nullHeuristic):
    "Search the node that has the lowest combined cost and heuristic first."
    "*** YOUR CODE HERE ***"
    """
    Implements A* using a min-piority queue to prioritize expanding the
    cheaper nodes first. The cost of each node is the sum of its cost from the
    start and its distance (based on the provided heuristic) from the goal
    state. Returns a path where one can be found, or an empty list if one
    cannot be found.
    """
    from util import PriorityQueue

    # Constants for readability
    STATE = 0
    DIRECTION = 1

    # A list containing the states already encountered
    nodeHistory = []
    # A list used to store the nodes to explore
    nodeQueue = PriorityQueue()

    # Start by enqueueing the start, along with an empty path
    # and its cost of 0
    nodeQueue.push(((problem.getStartState(), 'None'), []), 0)

    # Iterate through the queue
    while not nodeQueue.isEmpty():
        # Get the parent node, and the path to it
        node, path = nodeQueue.pop()
        # Seperate the parent state for successor query
        parent = node[STATE]
        # For each child of the parent state,
        for i, child in enumerate(problem.getSuccessors(parent)):
            # Check to see if the child has been explored
            if not child[STATE] in nodeHistory:
                # If no,
                # Check to see if the goal state has been reached
                if problem.isGoalState(child[STATE]):
                    path = path + [child[DIRECTION]]
                    return path
                # Otherwise,
                else:
                    # Mark the node as explored
                    actions = path + [child[DIRECTION]]
                    # As our min-priority queue prioritizes min-cost paths,
                    # An identical state that may exist in the queue with a
                    # higher path cost will always be explored after a
                    # cheaper one
                    nodeQueue.push((child, actions), problem.getCostOfActions(actions) + heuristic(child[STATE], problem))
                    # And enqueue the child
                    nodeHistory.append(child[STATE])

    # Return an empty list if no path can be found
    return []


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
