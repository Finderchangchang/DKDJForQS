package liuliu.qs.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import liuliu.qs.R;
import liuliu.qs.base.BaseActivity;
import liuliu.qs.method.CommonAdapter;
import liuliu.qs.method.CommonViewHolder;
import liuliu.qs.model.Contact;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/3.
 */

public class PhoneNumberActivity extends BaseActivity {
    private List<Contact> contactses;
    private List<Contact> resultList;
    private ListView lv;
    private CommonAdapter<Contact> adapter;
    private EditText pl_et_search;
    ProgressBar total_pb;
    TitleBar title_bar;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_phonenumber_list);
        contactses = new ArrayList<>();
        title_bar = (TitleBar) findViewById(R.id.title_bar);
        title_bar.setLeftClick(() -> finish());
        total_pb = (ProgressBar) findViewById(R.id.total_pb);
        lv = (ListView) findViewById(R.id.phonenumber_lv);
        resultList = new ArrayList<>();
        pl_et_search = (EditText) findViewById(R.id.pl_et_search);
        Observable.create(subscriber -> {
            lv.setVisibility(View.GONE);
            total_pb.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse("content://com.android.contacts/contacts");
            ContentResolver reslover = this.getContentResolver();

            // 在这里我们给query传递进去一个SORT_KEY_PRIMARY。
            // 告诉ContentResolver获得的结果安装联系人名字的首字母有序排列。
            Cursor cursor = reslover.query(uri, null, null, null,
                    android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY);
            while (cursor.moveToNext()) {
                // 联系人ID
                String id = cursor.getString(cursor
                        .getColumnIndex(android.provider.ContactsContract.Contacts._ID));

                // Sort Key，读取的联系人按照姓名从 A->Z 排序分组。
                String sort_key_primary = cursor
                        .getString(cursor
                                .getColumnIndex(android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY));

                // 获得联系人姓名
                String name = cursor
                        .getString(cursor
                                .getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));

                Contact mContact = new Contact();
                mContact.id = id;
                mContact.name = name;
                mContact.sort_key_primary = sort_key_primary;
                try {
                    mContact.title = PinyinHelper.toHanyuPinyinString(mContact.name, new HanyuPinyinOutputFormat(), "#");
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    mContact.title = "";
                }
                if (mContact.title.length() > 0) {
                    mContact.title = mContact.title.substring(0, 1).toUpperCase();
                }
                // 获得联系人手机号码
                Cursor phone = reslover.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + id, null, null);
                String phones = "";
                if (phone != null) {
                    while (phone.moveToNext()) {
                        int phoneFieldColumnIndex = phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String phoneNumber = phone.getString(phoneFieldColumnIndex);
                        //phoneNumbers.add(phoneNumber);
                        phones = phoneNumber;
                        break;
                    }
                }
                mContact.phoneNumbers = phones;
                contactses.add(mContact);
            }
            resultList.addAll(contactses);
            subscriber.onNext("");
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    adapter.refresh(resultList);
                    lv.setVisibility(View.VISIBLE);
                    total_pb.setVisibility(View.GONE);
                });
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }
    @Override
    public void initEvents() {
        pl_et_search.addTextChangedListener(textWatcher);
        adapter = new CommonAdapter<Contact>(this, resultList, R.layout.item_phone) {
            @Override
            public void convert(CommonViewHolder holder, Contact contact, int position) {
                if (position != 0) {
                    if (!contact.title.equals(contactses.get(position - 1).title)) {
                        holder.setVisible(R.id.item_phone_title, true);
                        holder.setText(R.id.item_phone_title, contact.title);
                    }
                } else {
                    holder.setVisible(R.id.item_phone_title, true);
                    holder.setText(R.id.item_phone_title, contact.title);
                }

                holder.setText(R.id.item_phone_name, contact.name);
                holder.setText(R.id.item_phone_number, contact.phoneNumbers);
            }
        };
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra("tel", contactses.get(position).phoneNumbers.replace(" ", ""));
            setResult(77, intent);
            finish();
        });
    }

    // 读取设备联系人的一般方法。大致流程就是这样，模板化的操作代码。
    private void readContacts() {
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver reslover = this.getContentResolver();

        // 在这里我们给query传递进去一个SORT_KEY_PRIMARY。
        // 告诉ContentResolver获得的结果安装联系人名字的首字母有序排列。
        Cursor cursor = reslover.query(uri, null, null, null,
                android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY);
        while (cursor.moveToNext()) {
            // 联系人ID
            String id = cursor
                    .getString(cursor
                            .getColumnIndex(android.provider.ContactsContract.Contacts._ID));

            // Sort Key，读取的联系人按照姓名从 A->Z 排序分组。
            String sort_key_primary = cursor
                    .getString(cursor
                            .getColumnIndex(android.provider.ContactsContract.Contacts.SORT_KEY_PRIMARY));

            // 获得联系人姓名
            String name = cursor
                    .getString(cursor
                            .getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));

            Contact mContact = new Contact();
            mContact.id = id;
            mContact.name = name;
            mContact.sort_key_primary = sort_key_primary;
            try {
                mContact.title = PinyinHelper.toHanyuPinyinString(mContact.name, new HanyuPinyinOutputFormat(), "#");
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                mContact.title = "";
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            if (mContact.title.length() > 0) {
                mContact.title = mContact.title.substring(0, 1).toUpperCase();
            }
            // 获得联系人手机号码
            Cursor phone = reslover.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + id, null, null);

            // 取得电话号码(可能存在多个号码)
            // 因为同一个名字下，用户可能存有一个以上的号，
            // 遍历。
            //ArrayList<String> phoneNumbers = new ArrayList<String>();
            String phones = "";
            while (phone.moveToNext()) {
                int phoneFieldColumnIndex = phone
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = phone.getString(phoneFieldColumnIndex);
                //phoneNumbers.add(phoneNumber);
                phones = phoneNumber;
                break;
            }
            mContact.phoneNumbers = phones;
            contactses.add(mContact);
        }
        resultList.addAll(contactses);
        adapter.refresh(resultList);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            resultList.removeAll(resultList);
            for (int i = 0; i < contactses.size(); i++) {
                Contact contact = contactses.get(i);
                if (contact.name.contains(pl_et_search.getText().toString().trim()) || contact.phoneNumbers.contains(pl_et_search.getText().toString().trim())) {
                    resultList.add(contact);
                }
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
