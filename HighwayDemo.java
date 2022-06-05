package demo19025;
import java.util.ArrayList;
import base.Highway;
import base.Truck;

class HighwayDemo extends Highway {

	public HighwayDemo() {
		super();
	}

	@Override
	public synchronized boolean hasCapacity() {	//Writing the hasCapacity function which return true if it has capacity else false.
		if(truck_list.size() < getCapacity())
		{
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean add(Truck truck) {	//Adding a truck if it has capacity.
		if(hasCapacity())
		{
			truck_list.add(truck);
			return true;
		}
		return false;
	}

	@Override
	public synchronized void remove(Truck truck) {	//Removing the truck from the list.
		truck_list.remove(truck);
	}
	ArrayList<Truck> truck_list=new ArrayList<Truck>();

}
