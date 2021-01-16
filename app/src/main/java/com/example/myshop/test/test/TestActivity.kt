package com.example.myshop.test.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin.Test.IoncliItem
import com.example.kotlin.Test.KoListAdapter
import com.example.myshop.R
import com.example.myshop.ui.Test.User
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    //写全局必须初始化  下面这个没初始化
    //    var m_a:Int   可以做一个延迟初始化
    //  ： 后面是类型 类型后面?   ?代表对应的变量/类没有初始化可能为空

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initView()
        test()
        if1() //基本语法，类，接口，继承，内部类
        main()//数组
        when1() //TODO 选择语句
        getStringLength(Int)//TODO 类型检测和类型转换

        testExt()//TODO 扩展函数
    }

    //TODO 扩展函数
    fun testExt() {
        var user = User("小明", 20)
    }

    //TODO 基本语法，类，接口，继承，内部类
    fun if1() {
        //判断
        // 传统用法
//        var a = 7
//        var b = 3
//        var max = a
//        if (a < b) max = b
//        print("max"+max+"a"+a+"b"+b)
//         Log.e("TAG","max"+max+"a"+a+"b"+b);

        // 使用 else
//        var max: Int
//            if (a > b) {
//            max = a
//        } else {
//            max = b
//        }

        // 作为表达式
//        val max = if (a > b) a else b
        //我们也可以把IF表达式的结果赋值给一个变量。
//        val max = if (a > b) {
//            print("Choose a")
//            a
//        } else {
//            print("Choose b")
//            b
//        }
//        print(max)
//        Log.e("TAG","max"+max);

        //if
        var x = 0
        if (x > 0) {
            println("x 大于 0")
        } else if (x == 0) {
            println("x 等于 0")
        } else {
            println("x 小于 0")
        }

        var a = 1
        var b = 2
        val c = if (a >= b) a else b
        println("c 的值为 $c")

        //使用 in 运算符来检测某个数字是否在指定区间内，区间格式为 x..y ：
        val x1 = 5
        val y = 9
        if (x1 in 1..8) {
            println("x1 在区间内")
        }
    }

    fun test() {
        //可变参数
        var name = "hello world"
        // print(name)
        Log.e("TAG", "onCreate: " + name)
        Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show()

        //lambda(匿名函数)
        val sum: (Int, Int) -> Int = { a, b -> a + b }
        // int a;   int b;     a+b=1+2
//        print(sum(1,2)) //输出3

        //外部类
        Green("" + sum(1, 2)).grn()

        //var   定义一个可变的变量
        var c: Int

        //可以根据值反推回去是什么类型    弱类型语言(可以不用给他类型)
        var b = 1.0

        //字符串的脚本语言用法
        //  $ 表示一个变量名或者变量值
        //  $varName 表示变量值
        //  ${varName.fun()} 表示变量的方法返回值
        //TODO   第一种方式
        var a = 1  //可变参数
        //这种写法  字符串在内存里面单独存储  开辟了一个新的存储空间 存储方式和常量是一样的
        //模板中的简单名称  字符串模板
        val s1 = "a is $a"

        //TODO   第二种方式
        a = 2
        // 模板中的任意表达式：
        val s2 = "${s1.replace("is", "was")}, but now is $a"
//        print(s2)
        Log.e("TAG", s2)

        //TODO 类型后面加?表示可为空
        var age: String? = "23"
        //TODO !!.  抛出空指针异常
        val ages = age!!.toInt()
        // TODO ?.  不做处理返回 null
        val ages1 = age?.toInt()
        //age为空返回-1
        val ages2 = age?.toInt() ?: -1

        /**
         * 参数可以为空,不用传参数的值
         * 声明一个字符串变量
         * 加了一个 ? 表示这个值可以出现为空的情况
         * TODO 第一种
         * 调用的时候在后面加上   !!  抛出空指针异常
         * TODO 第二种
         * ?. 后面不做处理     返回null
         * TODO 第三种
         * ?:-1    没有值的情况下、为空返回-1
         */

        //TODO 区间    类型的判断
        //循环1-4     1..4   .. 区间---递增的
        for (i in 1..4) print(i) // 输出“1234”
        //TODO 步长
        // 使用 step 指定步长
        //如果每次输出不是++    是+2/+3..    i每一次执行加多少
        for (i in 1..4 step 2) print(i) // 输出“13”
        //TODO 倒着输出  downTo  4 3 2 1     step  步长  一次-2
        for (i in 4 downTo 1 step 2) print(i) // 输出“42”
        // 使用 until 函数排除结束元素
        //TODO 排除元素  until  不能包含10   包前不包后
        for (i in 1 until 10) {   // i in [1, 10) 排除了 10
            println(i)
        }

        //TODO 位操作符
//        shl(bits) – 左移位 (Java’s <<)
//        shr(bits) – 右移位 (Java’s >>)
//        ushr(bits) – 无符号右移位 (Java’s >>>)
//        and(bits) – 与
//        or(bits) – 或
//        xor(bits) – 异或
//        inv() – 反向


        //TODO forEach
        var arr = arrayOf(1, 2, 3, 4)
        arr.forEach {
            var e = it
            print(e)
        }
    }

    //TODO 类型检测和类型转换     Any 任意类型(任何)
    // ? 返回值可能为空
    fun getStringLength(obj: Any): Int? {
        // is   表示是一个字符串就执行这个   不是就执行其他的
        if (obj is String) {
            // 做过类型判断以后，obj会被系统自动转换为String类型
            return obj.length
        }

        // !is  不是这个类型就执行这句话
        //在这里还有一种方法，与Java中instanceof不同，使用!is
        // if (obj !is String){
        //   // XXX
        // }

        // 这里的obj仍然是Any类型的引用
        return null

    }

    //外部类
    class Green(var name: String) {
        fun grn() {
            Log.e("TAG", "grn:$name")// 成员函数
        }
    }

    //列表
    private fun initView() {
        //网格布局
        recycle.layoutManager = GridLayoutManager(this, 2)
        //val 不可变参数
        val list = arrayListOf<String>()
        //循环从0开始到6 $=+
        for (i in 0..6) {
            list.add("爱好$i")
        }

        //设置适配器
        val kAdapter = KoListAdapter(list, this)
        //绑定适配器
        recycle.adapter = kAdapter
//        barAdapter!!.setOnItemClickListener(object : InternetBarAdapter.OnItemClickListener {
//                   override fun onItemClick(view: View, position: Int) {
//                       barAdapter!!.cleckAll(position)
//                       LgqLogutil.e("ITEM"+position)
//                   }
//               })

        //点击条目进行吐丝
        kAdapter.setOncli(object : IoncliItem {
            override fun Onclipos(pos: Int) {
                Toast.makeText(this@TestActivity, list.get(pos), Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * 数组
     * 可变参数会在函数体中，自动转变为数组类型
     * 数组类型不能作为参数，直接传递给可变参
     * 在数组类型前面添加 * ，可以传递给可变参数
     */
    fun main() {
        foo("1", "2", "3")
    }

    fun foo(vararg args: String) {
        bar1(*args)
        if2(args)
    }

    fun bar1(vararg args: String) {
        println(args.contentToString())
    }

    fun if2(args: Array<out String>) {
        bar1(*args)

        //赋值
        //[1,2,3]
        val a = arrayOf(1, 2, 3)

        //赋值
        //[0,2,4]
        val b = Array(3, { i -> (i * 2) })

        //读取数组内容
        println(a[0])    // 输出结果：1
        println(b[1])    // 输出结果：2

        val x: IntArray = intArrayOf(1, 2, 3)
        x[0] = x[1] + x[2]
    }

    //TODO 选择语句
    fun when1() {
        //switch    ==  when
        //条件必须是一个常量
        var x: Int
        x = 4
        when (x) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            else -> { // 注意这个块
                print("x 不是 1 ，也不是 2")
            }
        }

        //在 when 中，else 同 switch 的 default。
        // 如果其他分支都不满足条件将会求值 else 分支。
        when (x) {
            0, 1 -> print("x == 0 or x == 1")
            else -> print("otherwise")
        }

        //我们也可以检测一个值在（in）或者不在（!in）一个区间或者集合中
        when (x) {
            in 1..10 -> print("x 在该区间范围内")
//            in validNumbers -> print("x is valid")
            //加 ! 是非
            !in 10..20 -> print("x is outside the range")
            else -> print("none of the above")
        }
    }

    fun when2(args: Array<String>) {
        val items = setOf("apple", "banana", "kiwi")
        when {
            "orange" in items -> println("juicy")
            "apple" in items -> println("apple is fine too")
        }
    }

    //字符串模板
    //字符串可以包含模板表达式 ，即一些小段代码，会求值并把结果合并到字符串中。 模板表达式以美元符（$）开头，由一个简单的名字构成:
    fun main2(args: Array<String>) {
        val i = 10
        val s = "i = $i" // 求值结果为 "i = 10"
        println(s)
    }

    //用花括号扩起来的任意表达式:
    fun main3(args: Array<String>) {
        val s = "runoob"
        val str = "$s.length is ${s.length}" // 求值结果为 "runoob.length is 6"
        println(str)
    }

    //原生字符串和转义字符串内部都支持模板。 如果你需要在原生字符串中表示字面值 $ 字符（它不支持反斜杠转义）
    fun main4(args: Array<String>) {
        val price = """
    ${'$'}9.99
    """
        println(price)  // 求值结果为 $9.99
    }
}