public class test{
  
  //Main
  public static void main(String[] args){
    if(args.length!=0){
      test0(args[0]);
	}
	else{}
  }
  
  //test0
  public static void test0(String path){
    // field f = null;
	// f = new field(15,15);
	// f.load(path);
	// f.print();
	// vec v = f.getStart();
	// System.out.println(v.getX() + " " + v.getY());
	
	// field f = new field(15,15);
	// f.load(path)
	// f.print();
	
	lvl l = new lvl();
	l.load(path);
	l.printC();
	l.printF();
	l.printStart();
	l.printGoal();
  }
  
}