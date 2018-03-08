package com.fengniao.wanandroid.ui


import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.fengniao.wanandroid.R
import com.fengniao.wanandroid.base.BaseListFragment
import com.fengniao.wanandroid.base.FNAdapter
import com.fengniao.wanandroid.bean.ArticleList
import com.fengniao.wanandroid.bean.BannerData
import com.fengniao.wanandroid.net.retrofit.NetObserver
import com.yjkj.chainup.net.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_list_article.view.*
import kotlinx.android.synthetic.main.item_list_banner.view.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseListFragment<Any>() {

    val bannerPics = mutableListOf<String>()

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun enableLazyLoad(): Boolean = false

    override fun loadData() {
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> 0
        else -> 1
    }

    override fun getView(parent: ViewGroup?, viewType: Int): View = when (viewType) {
        0 -> layoutInflater.inflate(R.layout.item_list_banner, parent, false)
        else -> layoutInflater.inflate(R.layout.item_list_article, parent, false)
    }

    override fun bindDataToView(holder: FNAdapter.MyViewHolder?, position: Int) {
        if (holder == null) return
        if (position == 0) {
            holder.itemView.banner.setImgUrl(bannerPics)
        }
        if (mList[position] is ArticleList) {
            holder.itemView.tv_author.text = (mList[position] as ArticleList).author
            holder.itemView.tv_title.text = (mList[position] as ArticleList).title
            holder.itemView.tv_date.text = (mList[position] as ArticleList).niceDate
            holder.itemView.tv_type.text = (mList[position] as ArticleList).chapterName

        }
    }

    override fun onItemClick(holder: FNAdapter.MyViewHolder?, position: Int) {
        if (position == 0) return
    }

    override fun afterCreateView() {
        super.afterCreateView()
        mPage = 0
    }

    private fun getData() {
        HttpClient.instance
                .getBanner()
                .subscribeOn(Schedulers.io())
                .flatMap { t ->
                    if (t.isSuccess) {
                        handleBannerData(t.data)
                    }
                    HttpClient.instance.getArticleList(mPage)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetObserver<MutableList<ArticleList>>() {
                    override fun onHandleSuccess(t: MutableList<ArticleList>?) {
                        if (t != null) {
                            handleArticleData(t)
                        }
                    }
                })
    }


    private fun handleBannerData(banners: MutableList<BannerData>) {
        mList.add(banners)
        banners.forEach { bannerPics.add(it.imagePath) }
    }

    private fun handleArticleData(articles: MutableList<ArticleList>) {
        mList.addAll(articles)
        setListAdapter()
    }

}
