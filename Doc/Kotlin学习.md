# ? 和 !! 的含义
在 Kotlin 中，? 和 !! 是用来处理可空类型的两种重要符号。它们分别表示不同的语义，用于处理变量或表达式可能为 null 的情况。
> 一句话说明就是，如果变量或表达式可能为 null，则使用 ? 来处理，否则使用 !! 来断言。
> 如果是 ？，当变量为 null 时，会返回 null，也不会调用该变量的方法，不会抛出异常。

**?（可空安全操作符）**

**含义：**

? 用于表示一个类型为可空的变量、属性或函数返回值。它标记了一个类型为它的非空版本加上了“可空性”（Nullable）。

例如，String? 表示可以是 String 类型的值，也可以是 null。

**作用：**
1. **类型声明**：在变量或参数声明时使用，如 var name: String? = null，表明 name 可以存储 null 值。
2. **安全调用（Safe Call Operator）**：当对一个可空变量进行链式调用时，可以使用 ?.，如 name?.length。如果 name 为 null，则整个表达式结果为 null，不会触发 NullPointerException。

**?:（Elvis 操作符）**

**含义**

用于提供一个默认值，当可空变量为 null 时使用。

例如，`val length = name ?: 0` 表示如果 name 不为 null，则 length 等于 name.length，否则 length 等于 0。


**!!（断言非空操作符）**

**含义：**

!! 是一个强制解引用操作符，用于告诉编译器：“我确信这个可空变量在当前上下文中一定不为 null，即使它是可空类型。” 

使用 !! 后，编译器允许你像对待非空变量一样访问其成员或方法。

**作用：**

当对一个可空变量使用 !! 时，如果该变量实际上为 null，程序会在运行时立即抛出 `KotlinNullPointerException`。


**使用场景：**

在非常确定变量在特定时刻绝不可能为 null，并且希望避免安全调用带来的额外检查和代码冗余时，可以使用 !!。这是一种强烈的断言，使用时需谨慎，因为它可能会引入运行时错误风险。

# 内部类

```kotlin
// 内部类不能访问外部类的成员变量，需要加上内部类修饰符inner才可以,下面是个例子记录以下。
inner class MyClickListener() : OnItemClickListener {
    override fun onItemClick(view: View, position: Int) {
        LogUtils.i("MyClickListener", "onItemClick: ")
        startActivity(position)
    }
}
```

## kotlin 实现静态的方式

@JvmField + @JvmStatic 注解
上面我们见识了 companion object 伴随对象，那么 kotlin 是不是真的无法实现 static 了，也不是，kotlin 还是提供了相关办法，这就是 @JvmField + @JvmStatic 注解，其意思是声明成员和方法使用 JVM 提供的特性

@JvmField - 修饰静态变量
@JvmStatic - 修饰静态方法
@JvmField 和 @JvmStatic 只能写在 object 修饰的类或者 companion object 里，写法虽然有些别扭，但是效果是真的是按 static 来实现的
我们把上面的代码修改下：


class BookKotlin {

    companion object {

        @JvmField
        var nameStatic: String = "BB"

        @JvmStatic
        fun speakStatic() {
        }
    }

    var name: String = "AA"

    fun speak() {}
}

# 状态栏与应用内容重叠

当系统状态栏与您的Android应用内容发生重叠时，这通常是由于布局配置不当或适配策略未正确实施导致的。为了解决这个问题，您可以采取以下几种方法：

1. 设置`fitsSystemWindows`属性
    
    在您的根布局（通常是`CoordnatorLayout`, `DrawerLayout`, 或者 `ConstraintLayout`等）中设置`android:fitsSystemWindows="true"`。此属性告诉系统您的布局应该考虑系统窗口（如状态栏、导航栏等）的存在，并在绘制时为这些区域留出空间。同时，为了防止内容被系统窗口裁剪，确保您的根布局或其直接子布局具有`android:clipToPadding="false"`（或省略，因为默认就是false）。