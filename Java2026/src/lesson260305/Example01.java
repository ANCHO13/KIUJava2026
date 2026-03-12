package lesson260305;

public class Example01 {

	public static void main(String[] args) {

		A a = new A();
		B b = new B();
		C c = new C();

		Object o = a;

		a = b;

		I i = a;
		
		i.m();

	}

}

interface I {
	void m();
}

class A implements I {

	@Override
	public void m() {

	}
}

class B extends A {
}

class C extends B {
}
