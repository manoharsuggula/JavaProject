package demo19025;

import base.*;
import java.util.ArrayList;

public class NetworkDemo extends Network {
    ArrayList<Hub> hub_list = new ArrayList<Hub>();
    ArrayList<Highway> highway_list = new ArrayList<Highway>();
    ArrayList<Truck> truck_list = new ArrayList<Truck>();

    public NetworkDemo()
    {
        super();
    }

    @Override
    public void add(Hub hub) {  //Adding a hub to the list.
        hub_list.add(hub);
    }
    
    @Override
    public void add(Highway hwy) {  //Adding a highway to the list.
        highway_list.add(hwy);
    }

    @Override
    public void add(Truck truck) {  //Adding a truck to the list.
        truck_list.add(truck);
    }

    // start the simulation
	// derived class calls start on each of the Hubs and Trucks
    @Override
    public void start() {   //Calling the start function of each hub and truck.
        for(Hub h : hub_list)
        {
            h.start();
        }
        for(Truck t : truck_list)
        {
            t.start();
        }
    }

    @Override
    // Calls draw on each hub, highway, and truck
	// passing in display
	public void redisplay(Display disp) {  //Calling the draw method of each hub, truck, highway. 
        for(Hub h : hub_list)
        {
            h.draw(disp);
        }
        for(Truck t : truck_list)
        {
            t.draw(disp);
        }
        for(Highway hw : highway_list)
        {
            hw.draw(disp);
        }
    }

    @Override
    protected Hub findNearestHubForLoc(Location loc) {  //A method which returns the nearest hub to a given location.
        ArrayList<Integer> distances = new ArrayList<Integer>();    //I am storing the distances of all the hubs to the location in a list and checking which is min.
        for(Hub h : hub_list)
        {
            int d=loc.distSqrd(h.getLoc());
            distances.add(d);
        }
        int min=distances.get(0);
        Hub nearestHub=hub_list.get(0);
        for(int i=1;i<distances.size();i++)
        {
            if(distances.get(i) < min)
            {
                min=distances.get(i);
                nearestHub=hub_list.get(i);
            }
        }
        return nearestHub;
    }
}