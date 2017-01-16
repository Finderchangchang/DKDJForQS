package liuliu.qs.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/3.
 */

public class Contact {

    // 用于装载从联系人数据库中读取到的数据。
    // 结构化数据，便于数据操作和访问。

    public String id;
    public String name;
    public String sort_key_primary;
    public String phoneNumbers;
public String title;
    //获得一个联系人名字的首字符。
    //比如一个人的名字叫“安卓”，那么这个人联系人的首字符是：A。
    public String firstLetterOfName() {
        String s = sort_key_primary.charAt(0) + "";
        return s.toUpperCase();
    }


//    public String getPhoneNumbers() {
//        String phones = " ";
//        for (int i = 0; i < phoneNumbers.size(); i++) {
//            phones += "号码" + i + ":" + phoneNumbers.get(i);
//        }
//
//        return phones;
//    }

}
