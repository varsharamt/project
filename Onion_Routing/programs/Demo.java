class Demo {
	int value = 5;


	public void display() {
		int value = 7;
	}
	public void run() {
		System.out.println(value);
	}
	public static void main(String[] args)  {
		new Demo().display();

		new Demo().run();
	}
	
}
