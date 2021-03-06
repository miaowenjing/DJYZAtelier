package com.djyz.service.impl;

import com.djyz.domain.Article;
import com.djyz.domain.Customer;
import com.djyz.mapper.ArticleMapper;
import com.djyz.service.ArticleService;
import com.djyz.util.AjaxRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    /*添加文章*/
    @Override
    public AjaxRes addArticle(Article article) {
        AjaxRes ajaxRes = new AjaxRes();
        try{
            articleMapper.insert(article);
            ajaxRes.setMsg("添加文章成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setMsg("添加文章失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    /*文章点赞*/
    @Override
    public AjaxRes articleSupport(Long aid) {
        AjaxRes ajaxRes = new AjaxRes();
        try{
            Article article = articleMapper.selectByPrimaryKey(aid);
            Long numSupport = article.getNumSupport();
//            numSupport = numSupport + 1;
            numSupport += 1;
            article.setNumSupport(numSupport);
            articleMapper.updateByPrimaryKey(article);

            ajaxRes.setMsg("点赞成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setMsg("点赞失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    /*踩文章*/
    @Override
    public AjaxRes articleNonSupport(Long aid) {
        AjaxRes ajaxRes = new AjaxRes();
        try{
            Article article = articleMapper.selectByPrimaryKey(aid);
            Long numNonsupport = article.getNumNonsupport();
            numNonsupport += 1;
            article.setNumNonsupport(numNonsupport);
            articleMapper.updateByPrimaryKey(article);

            ajaxRes.setMsg("踩成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setMsg("踩失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    /*查询全部文章*/
    @Override
    public List<Article> getAllArticles() {
        return articleMapper.selectAll();
    }

    /*根据用户id查询文章*/
    /*@Override
    public List<Article> getArticlesWithCustId(Customer customer) {
        return articleMapper.getArticlesWithCustId(customer.getCustId());
    }*/
    @Override
    public List<Article> getArticlesWithCustId(Long custId) {
        return articleMapper.getArticlesWithCustId(custId);
    }

    /*根据id删除文章*/
    @Override
    public AjaxRes deleteArticlesWithCustId(Long aid) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            articleMapper.deleteByPrimaryKey(aid);
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("删除成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("删除失败");

        }
        return ajaxRes;
    }


}
