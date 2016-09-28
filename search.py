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
    Implementation of recursive DFS using a stack as the path
    """
    from util import Stack, dynamicMatrix

    global goalStateFound, graph, path
    goalStateFound = False
    graph = dynamicMatrix()
    path = Stack()

    def DFS(n):
        global goalStateFound
        node = n[0]
        graph.insert(node[0], node[1], True)  # mark n
        if problem.isGoalState(node):  # if n is goalState,
            goalStateFound = True   # set goalStateFound as true
            path.push(n[1])  # add the goal state to the path
        else:
            for (i, successor) in enumerate(problem.getSuccessors(node)):  # for all successors of n
                if graph.get(successor[0][0], successor[0][1]) == None:
                    DFS(successor)  # dfs(m)
                    if goalStateFound:  # if goalStateFound,
                        path.push(n[1])  # push n to stack
                        return  # return


    DFS((problem.getStartState(), 'None', 0))
    path.pop()  # Remove the start instruction from the stack
    path.list.reverse() # Reverse the stack as instructions are read in incremental index order
    return path.list


def breadthFirstSearch(problem):
    "Search the shallowest nodes in the search tree first. [p 81]"
    "*** YOUR CODE HERE ***"
    """
    Implements BFS by traversing the graph in level-order, using a Queue to flatten
    the tree and return a path to the goal, if one exists.
    """
    from util import Queue, dynamicMatrix

    def BFS(start):
        graph = dynamicMatrix()
        queue = Queue()
        nodeHistory = []
        path = []

        # Check to see if the start is the goal state
        if problem.isGoalState(start) == True:
            return path # If it is, return the empty path

        # Mark the start
        graph.insert(start[0][0], start[0][1], True)

        # Enqueue start
        s = {'node': start, 'parent': None}   # save the parent of n
        nodeHistory.append([s]) # enqueue start

        # Iterate through the queue to traverse the graph
        level = 0
        while level <= len(nodeHistory):
            # Iterate through each node at this level, i.e. siblings
            parents = nodeHistory[level]
            print 'level:', level
            for (i, parent) in enumerate(parents):
                # print 'parent:', parent
                children = []
                for (i, child) in enumerate(problem.getSuccessors(parent['node'][0])):
                    list1Index = child[0][0]
                    list2Index = child[0][1]
                    # Check to see if the child has been visited
                    if graph.get(list1Index, list2Index) == None:
                        # Check to see if the goal state has been reached
                        if problem.isGoalState(child[0]) == True:
                            print 'goalFound'
                            path.append(child[1])
                            pathNode = parent
                            # If it has, populate the path from this node to the start
                            while pathNode != None:
                                path.append(pathNode['node'][1])
                                pathNode = pathNode['parent']
                            return path

                        # Otherwise,
                        else:
                            # Mark the node as visited
                            graph.insert(list1Index, list2Index, True)
                            # Enqueue all siblings for processing
                            node = {'node': child, 'parent': parent}
                            children.append(node)
                nodeHistory.append(children)
            level = level + 1
    print 'Start BFS'
    p = BFS((problem.getStartState(), 'None', 0))
    p.pop()
    p.reverse()
    print 'path:', p
    return p



def uniformCostSearch(problem):
    "Search the node of least total cost first. "
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()


def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def aStarSearch(problem, heuristic=nullHeuristic):
    "Search the node that has the lowest combined cost and heuristic first."
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
