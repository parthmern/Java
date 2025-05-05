package method;

public class LearnMethod {
    public static void main(String[] args) {
        greet();
        int ans = sum(1,2);
        System.out.println("ans is" + ans);
    }

    // static => without making class obj u can call func
    // non-static ( without static keyword in func defi ) => u need to make obj then u can call
    public static void greet(){
        System.out.println("hello world");
    }

    public static int sum(int a, int b){
        int res = a + b;
        System.out.println("total is =>"+ res);
        return res;
    }
}
