package demo19025;
import java.util.*;
import base.Highway;
import base.Hub;
import base.Location;
import base.Truck;
import base.Network;

class HubDemo extends Hub 
{
	public HubDemo(Location loc)	//Constructor.
	{
		super(loc);
		hubs.add(this);
	}

	@Override
	public synchronized boolean add(Truck truck)	//Adding a truck by checking the capacity.
	{
		if(truck_list.size() < super.getCapacity())
		{
			truck_list.add(truck);
			return true;
		}	
		return false;
	}

	@Override
	public synchronized void remove(Truck truck)	//Removing the truck.
	{
		truck_list.remove(truck);
	}

	@Override
	public synchronized Highway getNextHighway(Hub from, Hub dest)	//A function getNextHighway(), which return a highway, which enroutes to the destination.
	//Here I am using a random function which generates a highway from its list randomly.
	//I have also done the dfs algorithm, but by using it I am getting some trucks stuck in some hubs and not moving. So, I have finalised the random algo.
	{
		// for(Highway highway : from.getHighways())
		// {
		// 	if(isReachable(highway.getEnd(),dest))
		// 	{
		// 		return highway;
		// 	}
		// }
		// return null;
		Random rand = new Random();
		int i = rand.nextInt(from.getHighways().size());
		return from.getHighways().get(i);
	}

	// private synchronized static boolean isReachable(Hub s, Hub d)
	// {
	// 	boolean[] visited = new boolean[hubs.size()];
	// 	ArrayList<Hub> queue = new ArrayList<>();
	// 	visited[hubs.indexOf(s)] = true;
	// 	queue.add(s);
	// 	while(queue.size() != 0)
	// 	{
	// 		s = queue.get(0);
    //         queue.remove(0);
	// 		Hub n;
	// 		for(int i=0;i<s.getHighways().size();i++)
	// 		{
	// 			n = s.getHighways().get(i).getEnd();
	// 			if(n.equals(d))
	// 			{
	// 				return true;
	// 			}
	// 			if(!visited[hubs.indexOf(n)])
	// 			{
	// 				visited[hubs.indexOf(n)] = true;
	// 				queue.add(n);
	// 			}
	// 		}
	// 	}
	// 	return false;
	// }


	@Override
	protected synchronized void processQ(int deltaT)	//A method which processes all the trucks in the hub by getting the next highway and sending the truck to the highway by checking the capacity.
	{
		for(Truck t : truck_list)
		{
			Hub dest = NetworkDemo.getNearestHub(t.getDest());
			Highway hwy = getNextHighway(this,dest);
			if(hwy!=null)
			{
				if(hwy.hasCapacity())
				{
					hwy.add(t);
					t.enter(hwy);
					truck_list.remove(t);
				}
			}
		}
	}
	private ArrayList<Truck> truck_list = new ArrayList<>();
	static ArrayList<Hub> hubs = new ArrayList<>();
}
