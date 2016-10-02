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
    Implements BFS by traversing the graph in level-order, using a Queue, and
    return a path to the goal, if one exists. Nodes are marked as visited using
    a 2D array representation of the problem graph (nodeHistory), where a node's x and y
    coordinates map to the 2D array.
    """
    from util import dynamicMatrix

    # Constants for readability
    COORDS = 0
    DIRECTION = 1
    X = 0
    Y = 1

    def BFS(start):
        # A 2D array representation of the visited nodes in the graph
        nodeHistory = dynamicMatrix()
        # A list used to store the nodes visited. It is also referenced by a
        # node's parent key to determine that node's parent.
        nodeQueue = []
        # The path containing the nodes from the start (inclusive) to a goal
        # (inclusive) in reverse order
        path = []

        # Check to see if the start is the goal state
        if problem.isGoalState(start) == True:
            return path  # If it is, return the empty path

        # Mark the start
        nodeHistory.insert(start[X][Y], start[X][Y], True)

        # Enqueue start
        s = {'node': start, 'parent': 0}   # save the parent of n
        nodeQueue.append(None)  # enqueue None as the parent for the start node
        nodeQueue.append(s)  # enqueue start

        # Iterate through the queue to traverse the graph
        iParent = 1
        while iParent < len(nodeQueue):
            # Get the parent node
            parent = nodeQueue[iParent]
            # For each child of the parent,
            for (i, child) in enumerate(problem.getSuccessors(parent['node'][COORDS])):
                list1Index = child[COORDS][X]
                list2Index = child[COORDS][Y]
                # Check to see if the child has been visited
                if nodeHistory.get(list1Index, list2Index) == None:
                    # If not,
                    # Check to see if the goal state has been reached
                    if problem.isGoalState(child[COORDS]) == True:
                        path.append(child[DIRECTION])
                        pathNode = nodeQueue[iParent]
                        # If it has, populate the path from this node to the
                        # start
                        while pathNode != None:
                            path.append(pathNode['node'][DIRECTION])
                            pathNode = nodeQueue[pathNode['parent']]
                        return path

                    # Otherwise,
                    else:
                        # Mark the node as visited
                        nodeHistory.insert(list1Index, list2Index, True)
                        # Store the parent
                        node = {'node': child, 'parent': iParent}
                        # And enqueue the child
                        nodeQueue.append(node)
            iParent = iParent + 1

    p = BFS((problem.getStartState(), 'None', 0))
    p.pop()  # Remove the start instruction from the stack
    p.reverse()  # Reverse the stack as instructions are read in incremental index order
    return p


def uniformCostSearch(problem):
    "Search the node of least total cost first. "
    "*** YOUR CODE HERE ***"
    """
    Implements BFS using a min-piority queue to prioritize expanding the
    cheaper nodes first. Nodes are marked as visited using a 2D array
    representation of the problem graph (nodeHistory), where a node's x and y
    coordinates map to the 2D array.
    """
    from util import PriorityQueue, dynamicMatrix

    # Constants for readability
    COORDS = 0
    DIRECTION = 1
    COST = 2
    X = 0
    Y = 1

    def UCBFS(start):
        # A 2D array representation of the visited nodes in the graph
        nodeHistory = dynamicMatrix()
        # A min-piority queue used to store the nodes visited.
        nodeQueue = PriorityQueue()
        # A list referenced by a node's parent key to determine that node's
        # parent
        parents = []
        # The path containing the nodes from the start (inclusive) to a goal
        # (inclusive) in reverse order
        path = []

        # Check to see if the start is the goal state
        if problem.isGoalState(start) == True:
            return path  # If it is, return the empty path

        # Mark the start
        nodeHistory.insert(start[X][Y], start[X][Y], True)

        # Enqueue start
        s = {'node': start, 'parent': 0}   # save the parent of n
        parents.append(None)  # enqueue None as the parent for the start node
        nodeQueue.push(s, start[COST])  # enqueue start
        # Iterate through the queue to traverse the graph
        iParent = 1
        while nodeQueue.isEmpty() != True:
            # Get the parent node
            parent = nodeQueue.pop()
            parents.append(parent)
            # For each child of the parent,
            for (i, child) in enumerate(problem.getSuccessors(parent['node'][COORDS])):
                pathCost = parent['node'][COST] + child[COST]
                child = (child[COORDS], child[DIRECTION], pathCost)
                # If not,
                # Check to see if the goal state has been reached
                if problem.isGoalState(child[COORDS]) == True:
                    path.append(child[DIRECTION])
                    pathNode = parents[iParent]
                    # If it has, populate the path from this node to the
                    # start
                    while pathNode != None:
                        path.append(pathNode['node'][DIRECTION])
                        pathNode = parents[pathNode['parent']]
                    return path

                # Otherwise,
                list1Index = child[COORDS][X]
                list2Index = child[COORDS][Y]
                # Check to see if the child has been visited
                if nodeHistory.get(list1Index, list2Index) == None:
                    # If the child has not been visited
                    # Mark the node as visited
                    nodeHistory.insert(list1Index, list2Index, True)
                    # Store the parent
                    newNode = {'node': child, 'parent': iParent}
                    # And enqueue the child
                    nodeQueue.push(newNode, child[COST])
                # If the child has been visited, update its reference in the
                # nodeQueue if its current cost is lower
                else:
                    for (i, node) in enumerate(nodeQueue.heap):
                        node = node[DIRECTION]['node']
                        nodeCoords = node[COORDS]
                        nodeCost = node[COST]

                        childCoords = child[COORDS]
                        childCost = child[COST]
                        if childCoords[X] == nodeCoords[X] and childCoords[Y] == nodeCoords[Y] and childCost < nodeCost:
                            newNode = {'node': child, 'parent': iParent}
                            # print 'recosting:', nodeQueue.heap[i], 'to: ', newNode
                            nodeQueue.push(newNode, child[COST])

            iParent = iParent + 1

    p = UCBFS((problem.getStartState(), 'None', 0))
    p.pop()  # Remove the start instruction from the stack
    p.reverse()  # Reverse the stack as instructions are read in incremental index order
    return p


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
    Implements BFS using a min-piority queue to prioritize expanding the
    cheaper nodes first. Nodes are marked as visited using a 2D array
    representation of the problem graph (nodeHistory), where a node's x and y
    coordinates map to the 2D array.
    """
    from util import PriorityQueue, dynamicMatrix

    # Constants for readability
    COORDS = 0
    DIRECTION = 1
    COST = 2
    X = 0
    Y = 1

    def UCBFS(start):
        # A 2D array representation of the visited nodes in the graph
        nodeHistory = dynamicMatrix()
        # A min-piority queue used to store the nodes visited.
        nodeQueue = PriorityQueue()
        # A list referenced by a node's parent key to determine that node's
        # parent
        parents = []
        # The path containing the nodes from the start (inclusive) to a goal
        # (inclusive) in reverse order
        path = []

        # Check to see if the start is the goal state
        if problem.isGoalState(start) == True:
            return path  # If it is, return the empty path

        # Mark the start
        nodeHistory.insert(start[X][Y], start[X][Y], True)

        # Enqueue start
        s = {'node': start, 'parent': 0}   # save the parent of n
        parents.append(None)  # enqueue None as the parent for the start node
        nodeQueue.push(s, start[COST])  # enqueue start
        # Iterate through the queue to traverse the graph
        iParent = 1
        while nodeQueue.isEmpty() != True:
            # Get the parent node
            parent = nodeQueue.pop()
            parents.append(parent)
            # For each child of the parent,
            for (i, child) in enumerate(problem.getSuccessors(parent['node'][COORDS])):
                pathCost = parent['node'][COST] + child[COST] + heuristic(child[COORDS], problem)
                child = (child[COORDS], child[DIRECTION], pathCost)
                # If not,
                # Check to see if the goal state has been reached
                if problem.isGoalState(child[COORDS]) == True:
                    path.append(child[DIRECTION])
                    pathNode = parents[iParent]
                    # If it has, populate the path from this node to the
                    # start
                    while pathNode != None:
                        path.append(pathNode['node'][DIRECTION])
                        pathNode = parents[pathNode['parent']]
                    return path

                # Otherwise,
                list1Index = child[COORDS][X]
                list2Index = child[COORDS][Y]
                # Check to see if the child has been visited
                if nodeHistory.get(list1Index, list2Index) == None:
                    # If the child has not been visited
                    # Mark the node as visited
                    nodeHistory.insert(list1Index, list2Index, True)
                    # Store the parent
                    newNode = {'node': child, 'parent': iParent}
                    # And enqueue the child
                    nodeQueue.push(newNode, child[COST])
                # If the child has been visited, update its reference in the
                # nodeQueue if its current cost is lower
                else:
                    for (i, node) in enumerate(nodeQueue.heap):
                        node = node[DIRECTION]['node']
                        nodeCoords = node[COORDS]
                        nodeCost = node[COST]

                        childCoords = child[COORDS]
                        childCost = child[COST] # child[Cost] == pathCost
                        if childCoords[X] == nodeCoords[X] and childCoords[Y] == nodeCoords[Y] and childCost < nodeCost:
                            newNode = {'node': child, 'parent': iParent}
                            # print 'recosting:', nodeQueue.heap[i], 'to: ', newNode
                            nodeQueue.push(newNode, child[COST])

            iParent = iParent + 1

    p = UCBFS((problem.getStartState(), 'None', 0))
    p.pop()  # Remove the start instruction from the stack
    p.reverse()  # Reverse the stack as instructions are read in incremental index order
    return p


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
