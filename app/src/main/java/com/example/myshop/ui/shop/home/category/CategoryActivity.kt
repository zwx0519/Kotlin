package com.example.myshop.ui.shop.home.category

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.home.category.CategoryAttributeAdapter
import com.example.myshop.adapter.shop.home.category.CategoryButtomInfoAdapter
import com.example.myshop.adapter.shop.home.category.CategoryImageAdapter
import com.example.myshop.adapter.shop.home.category.CategoryIssueAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.ActivityCategoryBinding
import com.example.myshop.model.bean.shop.home.category.CategoryBean
import com.example.myshop.model.bean.shop.home.category.Goods
import com.example.myshop.utils.BitmapUtils
import com.example.myshop.viewmodel.shop.home.category.CategoryViewModel
import com.github.chrisbanes.photoview.PhotoView
import com.shop.base.BaseActivity
import com.shop.utils.SpUtils
import com.youth.banner.loader.ImageLoader
import java.util.*
import java.util.regex.Pattern

class CategoryActivity(var lid: Int = R.layout.activity_category) :
    BaseActivity<CategoryViewModel, ActivityCategoryBinding>(lid, CategoryViewModel::class.java) {

    //TODO 商品参数
    var categoryAttributeAdapter: CategoryAttributeAdapter? = null
    var categoryAttributeList: List<CategoryBean.Attribute> = arrayListOf()

    //TODO 常见问题
    var categoryIssueAdapter: CategoryIssueAdapter? = null
    var categoryIssueList: List<CategoryBean.Issue> = arrayListOf()

    //TODO 底部列表
    var categoryButtomInfoAdapter: CategoryButtomInfoAdapter? = null
    var categoryButtomInfoList: List<Goods> = arrayListOf()

    var id: Int? = null
    var currentPos: Int? = null
    private var popupWindow: PopupWindow? = null
    val listUrl = ArrayList<String>()
    var countPos: Int? = null
    override fun initView() {

        val layoutManager = LinearLayoutManager(this)
        mDataBinding!!.mRlvCategoryIssue.layoutManager = layoutManager

        val layoutManager1 = LinearLayoutManager(this)
        mDataBinding!!.mRlvCategoryParameter.layoutManager = layoutManager1


        val layoutManager2 = GridLayoutManager(this, 2)
        mDataBinding!!.mRlvCategoryAll.layoutManager = layoutManager2

        val layoutManager3 = LinearLayoutManager(this)
        mDataBinding.mRlvCategoryBigimage.layoutManager = layoutManager3
    }

    //TODO 获取数据
    override fun initVM() {
        id = SpUtils.instance!!.getInt("Category_id")

        Log.e("TAG", "id：" + id)

        if (id != null) {
            mViewModel.getCategory(id!!)
            mViewModel.getCategoryBottomInfo(id!!)
        } else {
            throw RuntimeException("ID不能为空")
        }

        initAttribute()
        initIssue()
        initButtomInfo()
    }

    override fun initData() {

    }

    override fun initVariable() {
        //数据
        mViewModel!!.getCategory.observe(this, Observer {
            categoryIssueAdapter!!.refreshData(it.get(0).issue)
            categoryAttributeAdapter!!.refreshData(it.get(0).attribute)
            //Banner轮播下的数据
            initInfo(it)
            //Banner轮播
            initBanner(it)
            //图片
            initBigImage(it)

        })

        //底部列表数据
        mViewModel!!.getCategoryBottomInfo.observe(this, Observer {
            categoryButtomInfoAdapter!!.refreshData(it)
        })
    }

    //TODO 图片
    private fun initBigImage(it: List<CategoryBean>?) {
        var goods_desc = it!![0].info.goods_desc
        val img = "<img[\\s\\S]*?>"
        val pattern = Pattern.compile(img)
        val matcher = pattern.matcher(goods_desc)

        while (matcher.find()) {
            val word = matcher.group()
            val start = word.indexOf("\"") + 1
            val end = word.indexOf(".jpg")
            if (end > 0) { //如果是jpg格式的就截取jpg
                var url = word.substring(start, end)
                if (url != null) {
                    url = "$url.jpg"
                    listUrl.add(url)
                } else {
                    return
                }
            } else {
                val end1 = word.indexOf(".png") //如果是png格式的就截取png
                var url = word.substring(start, end1)
                if (url != null) {
                    url = "$url.png"
                    listUrl.add(url)
                } else {
                    return
                }
            }
        }

        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_category_bigimage_item, BR.vmCategoy_bigimage)

        mDataBinding!!.mRlvCategoryBigimage.adapter =
            CategoryImageAdapter(this, listUrl, array, BigImageClick())
    }

    //TODO Banner轮播数据
    private fun initBanner(it: List<CategoryBean>?) {
        mDataBinding.bannerCategory.setImages(it!!.get(0).gallery)
            .setImageLoader(object : ImageLoader() {
                override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                    val bean: CategoryBean.Gallery = path as CategoryBean.Gallery
                    Glide.with(context).load(bean.img_url).into(imageView)
                }
            }).start()
    }

    //TODO Banner轮播下的数据
    private fun initInfo(it: List<CategoryBean>) {
        mDataBinding.tvCategoryInfoTitle.setText(it.get(0).info.name)
        mDataBinding.tvCategoryInfoDesc.setText(it.get(0).info.goods_brief)
        mDataBinding.tvCategoryInfoPrice.setText("￥" + it.get(0).info.retail_price)
    }

    //TODO 底部列表数据
    private fun initButtomInfo() {
        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_category_item, BR.vmCategory_item)
        categoryButtomInfoAdapter =
            CategoryButtomInfoAdapter(this, categoryButtomInfoList, array, ButtonInfoItemClick())
        mDataBinding!!.mRlvCategoryAll.adapter = categoryButtomInfoAdapter
    }

    //TODO 常见问题数据
    private fun initIssue() {
        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_category_issue_item, BR.vmCategory_Issue)
        categoryIssueAdapter =
            CategoryIssueAdapter(this, categoryIssueList, array, IssueItemClick())
        mDataBinding!!.mRlvCategoryIssue.adapter = categoryIssueAdapter
    }

    //TODO 商品参数数据
    private fun initAttribute() {
        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_category_attribute_item, BR.vmCategory_Attribute)
        categoryAttributeAdapter =
            CategoryAttributeAdapter(this, categoryAttributeList, array, AttributeItemClick())
        mDataBinding!!.mRlvCategoryParameter.adapter = categoryAttributeAdapter
    }

    //TODO 底部列表内部类
    inner class ButtonInfoItemClick : IItemClick<Goods> {
        override fun itemClick(data: Goods?) {
            Log.i("TAG", data!!.name)
        }
    }

    //TODO 常见问题内部类
    inner class IssueItemClick : IItemClick<CategoryBean.Issue> {
        override fun itemClick(data: CategoryBean.Issue?) {
            Log.i("TAG", data!!.answer)
        }
    }

    //TODO 商品参数内部类
    inner class AttributeItemClick : IItemClick<CategoryBean.Attribute> {
        override fun itemClick(data: CategoryBean.Attribute?) {
            Log.i("TAG", data!!.name)
        }
    }

    //TODO 大图点击内部类
    inner class BigImageClick : IItemClick<String> {
        override fun itemClick(data: String?) {
            currentPos = SpUtils.instance!!.getInt("category_image")
            countPos = currentPos
            initPopu()
        }
    }

    //TODO PopupWindow查看大图
    private fun initPopu() {
        //PopWindow条目布局
        val popupView: View =
            LayoutInflater.from(this@CategoryActivity).inflate(R.layout.layout_category_popu, null)
        //设置popu
        popupWindow =
            PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        //找到视图
        popupWindow!!.contentView = popupView
        popupWindow!!.isClippingEnabled = false

        //控制点击pw范围以外的空间关闭pw  设置Pw以外的空间可以点击
        popupWindow!!.setOutsideTouchable(true)
        //设置背景  告知pw的范围
        popupWindow!!.setBackgroundDrawable(null)
        popupWindow!!.isFocusable = true

        val mVp: ViewPager = popupView.findViewById(R.id.mVp_big_image)
        val count: TextView = popupView.findViewById(R.id.tv_big_image_count)
        val tv_return: TextView = popupView.findViewById(R.id.tv_big_image_return)

        //返回上一页面
        tv_return.setOnClickListener {
            popupWindow!!.dismiss()//关闭弹窗
        }

        //当ViewPager滑动时
        mVp.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) { //停止时
                currentPos = position
                updatePage(count)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        //ViewPager切换
        mVp.currentItem = currentPos!!
        //适配器
        initList(mVp, count)

        //关闭阴影
        val attributes = window.attributes
        attributes.alpha = 0.5f
        window.attributes = attributes
        //开启阴影
        popupWindow!!.setOnDismissListener {
            val attributes = window.attributes
            attributes.alpha = 1f
            window.attributes = attributes
        }
        //在按钮的下方弹出  无偏移 第一种方式
        popupWindow!!.showAsDropDown(mDataBinding.mClCategory) //开启弹窗
    }

    //TODO 创建适配器
    private fun initList(mVp: ViewPager, count: TextView) {
        mVp!!.adapter = object : PagerAdapter() {

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun getCount(): Int {
                return listUrl.size
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val photoView = PhotoView(this@CategoryActivity)
                var imgpath =listUrl.get(position)
//                var img=BitmapUtils().getScaleBitmap(imgpath!!,160,160)
//                //Bitmap转uri
//                val uri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, img, null, null))
//                //uri转字符串
//                val path = getRealPathFromUri(this@CategoryActivity, uri)

                Glide.with(this@CategoryActivity).load(imgpath).apply(RequestOptions.bitmapTransform(RoundedCorners(20))).into(photoView)
                container.addView(photoView) //添加进入视图
                return photoView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }

        mVp.setCurrentItem(currentPos!!) //通过下标来改变集合里面的ViewPager的页面
    }


    //TODO uri转字符串的方法
    fun getRealPathFromUri(
        context: Context,
        contentUri: Uri?
    ): String? {
        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    //TODO 更换下标
    private fun updatePage(count: TextView) {
        val page: String = (currentPos!! + 1).toString()
        val num:String =(countPos!! + 1).toString()
        count.setText(page+"/"+num)
    }
}