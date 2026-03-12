package lesson260306;

public class FactoryMethod {

	public static void main(String[] args) throws InterruptedException {

		while (true) {
			Thread.sleep(1000);
			M m = B.create();
			m.m();
		}

	}

}

class A {
	M m = B.create();
}

class B implements M {
	static M create() {
		if (Math.random() < 0.5) {
			return new B();
		} else {
			return new M() {
				@Override
				public void m() {
					System.out.println("good bye");
				}
			};
		}
	}

	@Override
	public
	void m() {
		System.out.println("hello");
	}

}