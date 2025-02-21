package com.helloz.app.utils

import android.util.Xml
import com.helloz.app.model.MenuModel
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

/**
 * @author: zzfan
 * @date: 2024年4月15日
 * @description: XML解析工具类, 用于解析菜单列表，获取跳转的Activity和标题
 */
class XmlPullParserUtils {

    // 获取菜单列表
    companion object {
        private val TAG: String = "XmlPullParserUtils"

        @JvmStatic
        @Throws(IOException::class, XmlPullParserException::class)
        fun getMenuList(xml: InputStream?): List<MenuModel> {
            val menuList: MutableList<MenuModel> = ArrayList<MenuModel>()
            var menuModel: MenuModel? = null
            val pullParser = Xml.newPullParser()
            try {
                //为PULL解析器设置要解析的XML数据
                pullParser.setInput(xml, "UTF-8")
                var event = pullParser.eventType
                while (event != XmlPullParser.END_DOCUMENT) {
                    // when 等价于 switch
                    when (event) {
                        XmlPullParser.START_TAG -> {
                            when (pullParser.name) {
                                MenuUtils.ITEM -> { menuModel = MenuModel("", null) }
                                MenuUtils.TITLE -> { menuModel?.title = pullParser.nextText() }
                                MenuUtils.JUMP_TO_WHERE -> {
                                    menuModel?.jumpActivity = Class.forName(pullParser.nextText())
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            if ("item" == pullParser.name && menuModel != null) {
                                menuList.add(menuModel)
                                menuModel = null
                            }
                        }
                    }
                    event = pullParser.next()
                }
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            }
            LogUtils.i(TAG, menuList.toString())
            return menuList
        }
    }
}