package lesson260305;

public class Example02 {

	Button b = new Button(new Lamp());

	Button b2 = new Button(new Switchable() {

		@Override
		public void turnOn() {
			// TODO Auto-generated method stub

		}

		@Override
		public void turnOff() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isOff() {
			// TODO Auto-generated method stub
			return false;
		}
	});

}

class Lamp implements Switchable {

	@Override
	public void turnOn() {

	}

	@Override
	public boolean isOff() {
		return false;
	}

	@Override
	public void turnOff() {
		// TODO Auto-generated method stub

	}
}

class Button {

	Switchable device;

	public Button(Switchable d) {
		device = d;
	}


	void poll() {
		if (device.isOff()) {
			device.turnOn();
		} else {
			device.turnOff();
		}
	}
}
