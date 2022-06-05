package demo19025;

import base.*;

public class FactoryDemo extends Factory {

	@Override
	public Network createNetwork(){	//Creating a NetworkDemo object and returning it.
		return new NetworkDemo();
	}
	@Override
	public Highway createHighway() {	//Creating a HighwayDemo object and returning it.
		return new HighwayDemo();
	}

	@Override
	public Hub createHub(Location loc) {	//Creating a HubDemo object and returning it.
		return new HubDemo(loc);
	}

	@Override
	public Truck createTruck() {	//Creating a TruckDemo object and returning it.
		
		return new TruckDemo();
	}

}
