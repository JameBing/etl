/**
 * Created by Administrator on 2016-11-25.
 */
public class TestC {

    static int score1=65;
    static int score2=47;

    //计算总分
    public static int count()
    {
        return score1+score2;
    }

    public static void main(String[] args) {
        System.out.println("总分为："+count());
    }

}
