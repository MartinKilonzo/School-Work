import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Solve {

    public static void main (String[] args) throws InterruptedException, GraphException {
	Node u, v;
	DrawMap display;
	int delay = 100;


	if (args.length != 1 && args.length != 2)
	    System.out.println("Usage: java Solve inputFile");
	else {
	    if (args.length == 2) delay = Integer.parseInt(args[1]);
	    display = new DrawMap(args[0]);
	    try {
		Map streetMap = new Map(args[0]);
		BufferedReader in = new BufferedReader(
					new InputStreamReader(System.in));
		System.out.println("Press a key to continue");
		//String line = in.readLine();
		
		Iterator solution = streetMap.findPath();

		if (solution != null) {
		    if (solution.hasNext()) u = (Node)solution.next();
		    else return;
		    while (solution.hasNext()) {
			v = (Node)solution.next();
			Thread.sleep(delay);
			display.drawEdge(u,v);
			u = v;
		    }
		}
		else {
		    System.out.println("No solution was found");
		    System.out.println("");
		}

		in = new BufferedReader(
					new InputStreamReader(System.in));
		System.out.println("Press a key to finish");
	        String line = in.readLine();

	    }
	    catch (MapException e) {
		System.out.println("Error reading input file");
	    }
	    catch (IOException in) {
		System.out.println("Error reading from keyboard");
	    }
	    //catch (Exception ex) {
		//System.out.println(ex.getMessage());
	   // }

	    display.dispose();
	    System.exit(0);
	}
    }
}
