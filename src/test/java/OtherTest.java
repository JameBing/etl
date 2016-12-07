import java.util.Date;

/**
 * Created by Administrator on 2016-11-15.
 */
public class OtherTest {


    public static void main(String[] args) {
/*        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a","1");
        jsonObject.put("b","2");
        jsonObject.put("c","3");
        System.out.println(jsonObject.size());
        System.out.println(jsonObject.get("c"));
        System.out.println(jsonObject.get(2));
        System.out.println(System.currentTimeMillis());*/
        //int a = 'a'+'b';
        //System.out.println(a);
        //int a = (int)1.9;
        //float b = 3.4f;
        //int a = 3;

/*        int a = 10;
        a=++a;
        int b = --a;*/
//        int b = 5;
//        ++b;
//        System.out.println(b);

/*
        try {
            InputStreamReader reader1 = new InputStreamReader(System.in);
            BufferedReader reader2 = new BufferedReader(reader1);
            System.out.println("第一个数：");
            String onenumber = reader2.readLine();
            System.out.println("第二个数：");
            String twonumber = reader2.readLine();

            float a = Integer.parseInt(onenumber);
            float b = Float.parseFloat(twonumber);

            if (a==b){
                System.out.println("=");
            }else if(a>b)
            {
                System.out.println("a大于b");
            }else if(a<b)
            {
                System.out.println("a小于b");
            }

        }catch (Exception e)
        {
            e.getStackTrace();
        }
*/


/*        int num = 6;
        switch (num)
        {
            case 1:
                System.out.print("1");
                break;
            case 2:
                System.out.print("2");
                break;
            case 3:
                System.out.print("3");
                break;
            case 4:
                System.out.print("4");
                break;
            default:
                System.out.print("other");
        }*/

/*        for(int i=0;i<10;i++)
        {
            System.out.println("liuxin"+i);
        }*/


/*      int i = 0;
        while (i<10)
        {
            System.out.println("hello"+i);
            i++;
        }
*/

/*        int lay = 4;
        for (int i=0;i<=lay;i++)
        {
            //打印星号、
            for (int j=1;j<=i;j++)
            {
                System.out.print("*");
            }
            System.out.println();
        }*/

/*
        for (int i = 1;  i <= 9; i++) {
            for (int n = 1; n <= i; n++) {
                System.out.print(i + " x " + n + " = " + i * n + " ");
            }
            System.out.println();
        }
*/


//        OrderInfo oInfo = new OrderInfo("张三","123","1233");
//        char today='日';
//
//
//        switch(today)
//        {
//            case '一':
//            case '三':
//            case '五':
//                System.out.println("吃包子");
//                break;
//            case '二':
//            case '四':
//            case '六':
//                System.out.println("吃油条");
//                break;
//            case '日':
//                System.out.println("主席套餐");
//                break;
//            default:
//                System.out.println("no eat!");
//        }

//        // 外层循环控制行数
//        for (int i = 1;i>3;i++) {
//
//            // 内层循环控制每行的*号数
//            // 内层循环变量的最大值和外层循环变量的值相等
//            //for (int j = 1;j>i;j++) {
//                System.out.print("*");
//            //}
//
//            // 每打印完一行后进行换行
//           // System.out.println();
//        }

/*
        // 变量保存成绩
        int score = 53;

        // 变量保存加分次数
        int count = 0;


        //打印输出加分前成绩
        System.out.println(score);


        // 只要成绩小于60，就循环执行加分操作，并统计加分次数

        if(score<60)
        {
            for(int i=0;score<60;i++)
            {
                score++;
                count++;
            }
        }

        //打印输出加分后成绩，以及加分次数

        System.out.println(score);
        System.out.println(score-53);


*/

/*        int score = 0;
        int count = 0;

        try {
//            InputStreamReader iStreamReader = new InputStreamReader(System.in);
//            BufferedReader bReader = new BufferedReader(iStreamReader);
            Scanner input = new Scanner(System.in);
            System.out.println("请输入成绩：");
            score = input.nextInt();
            System.out.println("计算前:" + score);
            while (score < 60) {
                score++;
                count++;
            }
            System.out.println("计算后:" + score);
            System.out.println("一共加了:" + count);
        }catch (Exception e)
        {
            e.getStackTrace();
        }
        */

/*

        //班级数
        int classNumber = 3;
        //学生数
        int studentNumber = 4;
        //成绩总和
        double sum = 0;
        //输入平均分
        int avg = 0;

        InputStreamReader stram = new InputStreamReader(System.in);
        System.out.println();

*/


        //定义 一个字符串[名字为 Str ] 数组

/*        String [] str = new String[5];
        str[0] = "b";
        str[1] = "a";
        str[2] = "d";
        str[3] = "c";
        str[4] = "e";
        Arrays.sort(str);
        System.out.println(Arrays.toString(str));*/
       /* for (int i=0;i<str.length;i++)
        {
            System.out.println("数字中排序后的值为："+str[i]);
        }
        System.out.println("数组的第二个成绩为:"+str[2]);*/

        // 定义一个整型数组，保存成绩信息
/*
        int[] scores = { 89, 72, 64, 58, 93 };

        // 对Arrays类对数组进行排序
        Arrays.sort(scores);

        // 使用foreach遍历输出数组中的元素
        for (int score:scores) {
            System.out.println(score);
        }
*/

        //* 功能：输出学生年龄的最大值
        //* 定义一个无参的方法，返回值为年龄的最大值


/*        OtherTest o = new OtherTest();
        int ab = o.jisuanmax();
        System.out.println("最大年龄的学生为："+ab);*/

        // 创建对象，对象名为hello
        OtherTest hello = new OtherTest();

        // 调用方法，传入两门课程的成绩
/*        double a = hello.calcAvg(-94, 81);
        System.out.println("平均成绩为："+a);*/

        //将考试成绩排序并输出，返回成绩的个数
/*
        int [] aa = {15,22,88,66,44,55,99};
        int nus = hello.calNumber(aa);
        System.out.println("数组的长度为："+nus);



    }

    public int calNumber (int [] agrs){
        Arrays.sort(agrs);
        for (int i = 0; i < agrs.length ; i++) {
            System.out.print(agrs[i] + " ");
        }
        int num = agrs.length;
        return num;
    }*/


/*    public double calcAvg(double one,double two){
        double avg = 0;
        if(one>=0 && two>=0){
            avg   = (one+two)/2;
        }else {
            System.out.println("成绩非法");
        }
        return avg;
    }*/

/*    public int jisuanmax()
    {
        int [] age = new int[5];
        Scanner input = new Scanner(System.in);
        for (int i = 1; i <= age.length ; i++) {
            System.out.println("请输入第"+i+"个学生的年龄:");
            int num = input.nextInt();
            age[i-1] = num;
         }
        Arrays.sort(age);
        int num = age[4];
        return num;
    }

    public int getMaxAge() {
        int[] ages={18 ,23 ,21 ,19 ,25 ,29 ,17};
        int max=ages[0];
        for(int i=1;i<ages.length;i++){
            if(max<ages[i]){
                max=ages[i];
            }
        }
        return max;
    }*/



    /*
        1、 定义一个带参带返回值的方法，通过参数传入数组的长度，返回值为赋值后的数组
        2、 创建指定长度的整型数组
        3、 使用 for 循环遍历数组，通过 Math.random( ) 生成随机并给数组成员赋值
        4、 使用 return 返回赋值后的数组
    */
/*
        OtherTest t = new OtherTest();
        double [] ab =t.reArr(5);
        for (int i = 0; i < ab.length; i++) {
            System.out.println("结果为："+ab[i]);
        }
        System.out.println(ab.length);*/

/*        int[] i = {1, -23, 110, 3, 444, 4, 9};
        OtherTest o = new OtherTest();
        o.topThree(i);
    }*/

/*    public void aa(int[] a) {

        int[] b;
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            b = new int[10];
            if (a[i] >= 0) {
                b[i] = a[i];
                for (int j = 0; j < b.length-1; j++) {
                    int num = b[j];
                    System.out.println("前三名的成绩为："+num);
                }
            } else {
                System.out.println("成绩无效." + a[i]);
            }
        }

    }*/


/*    public void topThree(int[] a) {
        Arrays.sort(a);
        int[] j = new int[3];
        int k = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] >= 0 && a[i] <= 500) {
                j[k] = a[i];
                k++;
            }
            if (k == 3) break;
        }
        System.out.println("考试成绩前三为：" + Arrays.toString(j));
    }*/

/*    public double[] reArr (int lengths)
    {
        double [] aa = new double[lengths];
        int [] bb = new int[5];
        for (int i = 0; i < aa.length; i++) {
            aa[i]=(int)(Math.random()*100);
        }
        return aa;
    }*/
        long a = new Date().getTime();
        System.out.println(a);


        System.out.println(1480493960);
    }

}
