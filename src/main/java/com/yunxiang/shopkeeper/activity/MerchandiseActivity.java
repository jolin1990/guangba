package com.yunxiang.shopkeeper.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunxiang.shopkeeper.R;
import com.yunxiang.shopkeeper.TApplication;
import com.yunxiang.shopkeeper.base.BaseTitle;
import com.yunxiang.shopkeeper.biz.CategoryBiz;
import com.yunxiang.shopkeeper.biz.ImageBiz;
import com.yunxiang.shopkeeper.biz.MerchandiseBiz;
import com.yunxiang.shopkeeper.model.Category;
import com.yunxiang.shopkeeper.model.Merchandise;
import com.yunxiang.shopkeeper.utils.AndroidUtils;
import com.yunxiang.shopkeeper.utils.Const;
import com.yunxiang.shopkeeper.utils.DebugUtils;

import java.util.ArrayList;
import java.util.List;

public class MerchandiseActivity extends Activity implements View.OnClickListener{

   /* private static final int[] menuColors = {0xff52c169, 0xffb4c169, 0xffebc44d, 0xff00c44d, 0xffeb114d, 0xffebc4bb, 0xff3bc49d, 0xffabc44d,
            0xfffbc40d, 0xff3bc44d, 0xff9bc44d, 0xff5bc44d, 0xff6bc44d, 0xff7bc44d, 0xff8bc44d, 0xff9bc44d,
            0xffbbc40d, 0xffcbc44d, 0xffdbc44d, 0xff5bb44d, 0xff6ba44d, 0xff7b944d, 0xff8b844d, 0xff9b744d};*/
    private int count = 20;

    private boolean isNew = false;
    private int menuSelected = 0;
    private TextView txtAdd;
    private RadioGroup radioGroup;
    private TextView txtMenu;
    private List<Category> categories;
    private ListView listView;
    private Category category;
    private List<Merchandise> merchandises;
    private MyAdapter adapter;
    private CategoryCallback categoryCallback;
    private LayoutInflater inflater;
    private int merchandiseIndex = 0;
    private boolean isRemmanded = false;//false，取消推荐  true,新增推荐

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        inflater = getLayoutInflater();
        setContentView(R.layout.activity_merchandise);

        BaseTitle.getInstance().setTitle(this, "商品服务");
        addView();
        if(TApplication.versionType == Const.TEST_VERTION){
            categories = new ArrayList<Category>();
            category = new Category("商家推荐");
            categories.add(category);
            category = new Category("凉菜");
            categories.add(category);
            category = new Category("热菜");
            categories.add(category);
            category = new Category("炖菜");
            categories.add(category);
            category = new Category("主食");
            categories.add(category);
            category = new Category("饮料");
            categories.add(category);
            category = new Category("川菜");
            categories.add(category);
            addRadioButton();
            addListView();
        }else {
            addData();
        }

        addListener();
    }

    private void addListener() {
        txtMenu.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
    }

    private void addView() {
        txtAdd = (TextView) findViewById(R.id.txt_add);
        txtMenu = (TextView)findViewById(R.id.txt_menu);
        listView = (ListView) findViewById(R.id.list);
        radioGroup = (RadioGroup)findViewById(R.id.rg_left_menu);
    }

    /**
     * desc: 获取分类列表和推荐商品
     * auther:jiely
     * create at 2015/11/19 15:52
     */
    private void addData() {

        if(TApplication.merchandiseArray == null){// == null || TApplication.merchandises.size()==0){
            MerchandiseCallback calback = new MerchandiseCallback();
            Handler handler = new Handler(calback);
            MerchandiseBiz.getInstance().selectMerchandises(0, handler);
        }else {
            merchandises = TApplication.merchandiseArray.get(0l);
            addListView();
        }

        if(TApplication.categories == null){
            if(categoryCallback == null){
                categoryCallback = new CategoryCallback();
                Handler handler = new Handler(categoryCallback);
                CategoryBiz.getInstance().selectAll(handler);
            }
        }else {
            categories = TApplication.categories;
            //添加分类列表
            addRadioButton();
        }

    }


    /**
     * desc:
     * auther:jiely
     * create at 2015/11/18 group
     */
    private void addRadioButton() {
        //categories = TApplication.categories;
        radioGroup.removeAllViews();
        for (int i = 0; i < categories.size(); i++) {
            RadioGroup rg = (RadioGroup)inflater.inflate(R.layout.radiobutton_type, null);
            RadioButton radioButton = (RadioButton) rg.findViewById(R.id.rd_menu);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioButton.setId(i);
            radioButton.setText(categories.get(i).getName());
            rg.removeView(radioButton);
            radioGroup.addView(radioButton);
        }
        //switchMenu(0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                menuSelected = checkedId;
                long categoryId = categories.get(menuSelected).getCategoryId();

                MerchandiseCallback calback = new MerchandiseCallback();
                Handler handler = new Handler(calback);
                MerchandiseBiz.getInstance().selectMerchandises(categoryId,handler);
                //switchMenu(checkedId);
            }
        });
    }

    private void addListView() {
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_add://点击右上角的按钮
                if (menuSelected == 0) {
                    final Dialog dialog = new Dialog(this, R.style.dialog);
                    View mView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_menu, null, true);
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    dialog.setContentView(mView);
                    lp.width = (int) (300 * TApplication.displayMetrics.scaledDensity);
                    lp.height = (int) (200 * TApplication.displayMetrics.scaledDensity);
                    TextView txtView = (TextView) mView.findViewById(R.id.txt_click);
                    txtView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.onBackPressed();
                        }
                    });

                    dialog.show();
                } else {
                    //category = categories.get(menuSelected);
                    final Dialog dialog = new Dialog(this, R.style.dialog);
                    View mView = LayoutInflater.from(this).inflate(R.layout.dialog_client_menu, null, true);
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    dialog.setContentView(mView);

                    dialogWindow.setGravity(Gravity.END | Gravity.TOP);
                    lp.x = (int) (2.5 * TApplication.displayMetrics.scaledDensity);
                    lp.y = (int) (45 * TApplication.displayMetrics.scaledDensity);
                    lp.alpha = 0.8f;

                    TextView txtMenu1 = (TextView) dialogWindow.findViewById(R.id.txt_menu1);
                    TextView txtMenu2 = (TextView) dialogWindow.findViewById(R.id.txt_menu2);
                    TextView txtMenu3 = (TextView) dialogWindow.findViewById(R.id.txt_menu3);

                    txtMenu1.setText("编辑类名");
                    txtMenu2.setText("添加商品");
                    txtMenu3.setText("删除类别");

                    txtMenu1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String text = TApplication.categoryList.get(menuSelected-1);
                            isNew = false;
                            setEditDolog("编辑类名");
                            dialog.dismiss();
                        }
                    });

                    txtMenu2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(MerchandiseActivity.this, MerchandiseAddActivity.class);
                            long categoryTd = categories.get(menuSelected).getCategoryId();
                            intent.putExtra("categoryTd", categoryTd);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    txtMenu3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(categoryCallback == null){
                                categoryCallback = new CategoryCallback();
                            }
                            Handler handler = new Handler(categoryCallback);
                            CategoryBiz.getInstance().deleteCategory(category,handler);
                            dialog.dismiss();
                        }
                    });
                    lp.width = (int) (100 * TApplication.displayMetrics.scaledDensity);
                    lp.height = (int) (130 * TApplication.displayMetrics.scaledDensity);
                    dialog.show();
                }
                break;
            case R.id.txt_menu://点击左侧的新建
                isNew = true;
                setEditDolog("新建");
                break;
        }
    }

    /**
     * desc: 新增或修改类别时跳出对话框
     * auther:jiely
     * create at 2015/10/29 9:17
     */
    private void setEditDolog(String title) {

        final Dialog dialog = new Dialog(MerchandiseActivity.this, R.style.dialog);
        View view = inflater.inflate(R.layout.edittext_new, null, false);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (220 * TApplication.displayMetrics.scaledDensity);
        lp.height = (int) (120 * TApplication.displayMetrics.scaledDensity);
        dialog.show();

        final EditText editText = (EditText) view.findViewById(R.id.edt_name);
        if (!isNew) {
            editText.setText(categories.get(menuSelected).getName());
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtTitle.setText(title);
        Button btnSure = (Button) view.findViewById(R.id.btn_sure);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryCallback == null){
                    categoryCallback = new CategoryCallback();
                }
                Handler handler = new Handler(categoryCallback);

                if (isNew) {//增加类别
                    category = new Category();
                    String name = editText.getText().toString();
                    if (name.length() != 0) {
                        category.setName(name);
                        CategoryBiz.getInstance().insertCategory(category,handler);
                        dialog.dismiss();
                    } else {
                        AndroidUtils.show(MerchandiseActivity.this, "不能为空");
                    }
                } else { //修改类名
                    String name = editText.getText().toString();
                    if (name.length() != 0) {
                        category = categories.get(menuSelected);
                        category.setName(name);
                        CategoryBiz.getInstance().updateCategory(category, handler);
                    }
                }

                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter() {
            super();
            inflater = LayoutInflater.from(MerchandiseActivity.this);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View v, final ViewGroup parent) {
            merchandiseIndex = position;

            if (v == null) {
                v = inflater.inflate(R.layout.item_merchandise_info, parent, false);
            }
            TextView txtName = (TextView) v.findViewById(R.id.txt_name);
            TextView txtPrice = (TextView) v.findViewById(R.id.txt_price);
            ImageView imgGoods = (ImageView) v.findViewById(R.id.img_goods);

            if(TApplication.versionType == Const.TEST_VERTION){
                return v;
            }
            final Merchandise merchandise = merchandises.get(position);
            txtName.setText(merchandise.getName());
            txtPrice.setText(String.valueOf(merchandise.getPrice() + "￥/份"));
            String goodsUrl = merchandise.getSmallImageURL();
            ImageBiz imageBiz = new ImageBiz(false);
            imageBiz.execute(goodsUrl, imgGoods);

            //当在商家推荐的列表中，
            TextView txtRecommend = (TextView) v.findViewById(R.id.txt_recommend);
            final TextView txtDown = (TextView) v.findViewById(R.id.txt_down);
            final ImageView imgRecommend = (ImageView) v.findViewById(R.id.img_recommend);
            final RelativeLayout rlSoldOut = (RelativeLayout) v.findViewById(R.id.rl_soldout);

            final RelativeLayout rlDisplay = (RelativeLayout) v.findViewById(R.id.rl_display);

            if (merchandise.isSoldOut()) {//已下架
                rlDisplay.setClickable(false);
                rlSoldOut.setVisibility(View.VISIBLE);//背景变灰
                txtDown.setText("已下架");
                TextView txtFill = (TextView) v.findViewById(R.id.txt_fill);
                txtFill.setOnClickListener(new View.OnClickListener() {//点击立即补货
                    @Override
                    public void onClick(View v) {
                        DebugUtils.d("GoodsFragment", "立即补货");
                        updateSoldout(merchandise);
                        rlSoldOut.setVisibility(View.INVISIBLE); //取消灰色背景
                        rlDisplay.setClickable(true);
                        txtDown.setText("下架");
                    }
                });
            } else {
                rlSoldOut.setVisibility(View.GONE); //取消灰色背景
                rlDisplay.setClickable(true);
                txtDown.setText("下架");
                txtDown.setOnClickListener(new View.OnClickListener() {//点击下架
                    @Override
                    public void onClick(View v) {
                        updateSoldout(merchandise);
                    }
                });
            }

            if (menuSelected == 0) {//商家推荐的列表
                txtRecommend.setClickable(true);
                txtRecommend.setText("不再推荐");
                txtRecommend.setBackgroundColor(getResources().getColor(R.color.orange));
                txtRecommend.setOnClickListener(new View.OnClickListener() {//点击取消推荐
                    @Override
                    public void onClick(View v) {
                        isRemmanded = false;
                        updateRecommand(merchandise);
                    }
                });
            } else {//不在商家推荐的列表
                txtRecommend.setText("推荐");
                if (merchandise.isRecommend()) {//已推荐
                    txtRecommend.setClickable(false);
                    txtRecommend.setBackgroundColor(getResources().getColor(R.color.gray_light_n));
                    imgRecommend.setVisibility(View.VISIBLE);
                } else {
                    txtRecommend.setClickable(true);
                    txtRecommend.setBackgroundColor(getResources().getColor(R.color.orange));
                    imgRecommend.setVisibility(View.INVISIBLE);
                    txtRecommend.setOnClickListener(new View.OnClickListener() {//点击推荐
                        @Override
                        public void onClick(View v) {
                            /*imgRecommend.setVisibility(View.VISIBLE);
                            txtRecommand.setClickable(false);
                            txtRecommand.setBackgroundColor(getResources().getColor(R.color.gray_light_n));*/
                            isRemmanded = true;
                            updateRecommand(merchandise);
                        }
                    });
                }
            }

            //编辑商品的监听
            ImageView imgEdit = (ImageView) v.findViewById(R.id.img_edit);
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MerchandiseActivity.this, MerchandiseAddActivity.class);
                    startActivity(intent);
                }
            });
            return v;
        }
    }

    /**
     * desc: 修改商品的推荐信息
     * auther:jiely
     * create at 2015/11/20 9:58
     */
    private void updateRecommand(Merchandise merchandise) {
        boolean isRecommand = merchandise.isRecommend();
        isRecommand = !isRecommand;
        merchandise.setIsRecommend(isRecommand);
        MerchandiseCallback callback = new MerchandiseCallback();
        Handler handler = new Handler(callback);
        MerchandiseBiz.getInstance().updateRecommend(merchandise, handler);
    }

    /**
     * desc: 修改商品的推荐信息
     * auther:jiely
     * create at 2015/11/20 9:58
     */
    private void updateSoldout(Merchandise merchandise) {
        boolean isSoldout = merchandise.isSoldOut();
        isSoldout = !isSoldout;
        merchandise.setIsSoldOut(isSoldout);
        MerchandiseCallback callback = new MerchandiseCallback();
        Handler handler = new Handler(callback);
        MerchandiseBiz.getInstance().updateSoldOut(merchandise, handler);
    }

    /**
     * desc:更新类别信息
     * auther:jiely
     * create at 2015/11/19 17:02
     */
    class CategoryCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            String desc = message.obj.toString();
            RadioButton radioButton;
            switch (message.what) {
                case Const.MSG_ADD_CATEGORY_SUCCESS:
                    TApplication.categories.add(category);
                    categories = TApplication.categories;
                    RadioGroup rg = (RadioGroup)inflater.inflate(R.layout.radiobutton_type, null);
                    radioButton = (RadioButton) rg.findViewById(R.id.rd_menu);
                    rg.removeView(radioButton);
                    radioButton.setText(category.getName());
                    radioButton.setChecked(true);
                    radioGroup.addView(radioButton);
                    menuSelected = 0;//默认第一个为选中状态
                    categories.add(category);
                    break;
                case Const.MSG_UPDATE_CATEGORY_SUCCESS:
                    radioButton = (RadioButton) radioGroup.getChildAt(menuSelected);
                    radioButton.setText(category.getName());
                    TApplication.categories.get(menuSelected).setName(category.getName());
                    categories = TApplication.categories;
                    break;
                case Const.MSG_DELETE_CATEGORY_SUCCESS:
                    categories.remove(category);
                    radioGroup.removeViewAt(menuSelected);
                    TApplication.categories.remove(category);
                    categories = TApplication.categories;
                    break;
                case Const.MSG_SUCCESS:
                    //添加分类列表
                    categories = TApplication.categories;
                    addRadioButton();
                    break;
                case Const.MSG_FAILURE:
                    AndroidUtils.show(MerchandiseActivity.this, desc);
                    break;
            }
            return true;
        }
    }

    class MerchandiseCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message message) {
            Merchandise tempMerchandise;
            long categoryId;
            List<Merchandise> tempmerchandises;
            switch (message.what) {
                case Const.MSG_ADD_MERCHANDISE_SUCCESS:
                    break;
                case Const.MSG_OBTAIN_MERCHANDISE_SUCCESS:
                    merchandises = TApplication.merchandiseArray.get(categories.get(menuSelected).getCategoryId());
                    count = merchandises.size();
                    addListView();
                    break;
                case Const.MSG_FAILURE:
                    AndroidUtils.show(MerchandiseActivity.this, message.obj.toString());
                    break;
                case Const.MSG_RECOMMEND_SUCCESS://修改商品是否推荐成功
                    tempMerchandise = merchandises.get(merchandiseIndex);
                    categoryId = categories.get(menuSelected).getCategoryId();
                    tempmerchandises = TApplication.merchandiseArray.get(categoryId);
                    if (isRemmanded) {
                        merchandises.add(tempMerchandise);
                        tempmerchandises.add(tempMerchandise);
                    } else {
                        merchandises.remove(merchandiseIndex);
                        tempmerchandises.remove(tempMerchandise);
                    }

                    adapter.notifyDataSetChanged();
                    break;
                case Const.MSG_SOLDOUT_SUCCESS: //修改商品是否售完成功
                    tempMerchandise = merchandises.get(merchandiseIndex);
                    categoryId = categories.get(menuSelected).getCategoryId();
                    tempmerchandises = TApplication.merchandiseArray.get(categoryId);
                    boolean isSoldout = tempMerchandise.isSoldOut();
                    //List<Merchandise> tempmerchandises = TApplication.merchandiseMap.get(menuSelected);
                    tempmerchandises.get(merchandiseIndex).setIsSoldOut(isSoldout);
                    adapter.notifyDataSetChanged();
                    break;
            }
            return true;
        }
    }

}
