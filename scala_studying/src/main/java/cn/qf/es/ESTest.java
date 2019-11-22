package cn.qf.es;/**
 * Description：<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月20日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-20 20:16
 * @ desc: ES 
 **/

public class ESTest {
    private Client client;
    @Before
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "bigdata").build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.8.101"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.8.102"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.8.103"), 9300));

    }

    /**
     * 数据插入
     */
    @Test
    public void createIndexNoMapping() {
        String json = "{" +
                "\"id\":\"1\"," +
                "\"title\":\"基于Lucene的搜索服务器\"," +
                "\"content\":\"它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口\"" +
                "}";
        IndexResponse indexResponse = this.client
                .prepareIndex("blog02", "article", "1")
                .setSource(json, XContentType.JSON)
                .execute()
                .actionGet();

        String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        boolean fragment = indexResponse.isFragment();
        System.out.println("index:" + index + "\ntype:" + type + "\nid:" + id +
                "\nversion" + version + "\nfragment:" + fragment);
        client.close();
    }
    @Test
    public void mapESTest() {
        final HashMap<String, Object> source = new HashMap<>();
        source.put("id", 2);
        source.put("title", "基于TESTful web接口,利用map插入数据");
        source.put("content","利用Map进行存储");

        //创建文档
        IndexResponse indexResponse = client.prepareIndex("blog02", "article", "2").setSource(source)
                .execute()
                .actionGet();
        String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        boolean fragment = indexResponse.isFragment();
        System.out.println("index:" + index + "\ntype:" + type + "\nid:" + id +
                "\nversion" + version + "\nfragment:" + fragment);
        client.close();
    }
    @Test
    public void createDoc_3() throws IOException {
        final XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", 3)
                .field("title", "指定类型")
                .field("content", "利用指定类型存数据")
                .endObject();
        IndexResponse indexResponse = client.prepareIndex("blog02", "article", "3").setSource(source)
                .execute()
                .actionGet();
        String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        boolean fragment = indexResponse.isFragment();
        System.out.println("index:" + index + "\ntype:" + type + "\nid:" + id +
                "\nversion" + version + "\nfragment:" + fragment);
        client.close();
    }
    /**
     * 通过单个索引搜索文档内容
     */
    @Test
    public void getDataByOneIndex() {
        final GetResponse documentFields = client.prepareGet("blog01", "article", "1").get();
        String source = documentFields.getSourceAsString();
        System.out.printf(source);
        client.close();
    }
    /**
     * 通过多个索引搜索文档内容
     */
    @Test
    public void getDataByMoreIndex() {
        final MultiGetResponse mrg = client.prepareMultiGet()
                .add("blog01", "article", "1")
                .add("blog01", "article", "2")
                .add("blog01", "article", "3")
                .get();

        //遍历数据
        for (MultiGetItemResponse item : mrg) {
            final GetResponse response = item.getResponse();
            final String sourceAsString = response.getSourceAsString();
            System.out.print(sourceAsString);
        }
        client.close();
    }
    /**
     * 更新文档
     */
    @Test
    public void updateDoc_1() throws IOException, ExecutionException, InterruptedException {
        final UpdateRequest request = new UpdateRequest();
        request.index("blog01");
        request.type("article");
        request.id("3");
        //指定要更新的数据
        request.doc(XContentFactory.jsonBuilder()
        .startObject()
                .field("id", "3")
                .field("title","更新数据")
                .field("content","更新内容...")
                .endObject()

        );
        //开始更新数据
        UpdateResponse updateResponse = client.update(request).get();
        String index = updateResponse.getIndex();
        String type = updateResponse.getType();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        boolean fragment = updateResponse.isFragment();
        System.out.println("index:" + index + "\ntype:" + type + "\nid:" + id +
                "\nversion" + version + "\nfragment:" + fragment);
    }
    /**
     * 更新文档
     */
    @Test
    public void updateDoc_2() throws IOException, ExecutionException, InterruptedException {
        final UpdateResponse updateResponse = client.update(new UpdateRequest("blog01", "article", "2")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id", "1")
                        .field("title", "第二种更新数据")
                        .field("content", "第二种方法更新内容...")
                        .endObject()
                )).get();
        String index = updateResponse.getIndex();
        String type = updateResponse.getType();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        boolean fragment = updateResponse.isFragment();
        System.out.println("index:" + index + "\ntype:" + type + "\nid:" + id +
                "\nversion" + version + "\nfragment:" + fragment);
    }
    /**
     * 更新文档-3
     */
    @Test
    public void updateDoc_3() throws IOException, ExecutionException, InterruptedException {
        final IndexRequest source = new IndexRequest("blog01", "article", "14")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id", "14")
                        .field("title", "数据")
                        .field("content", "内容...")
                        .endObject()
                );
        //设置更新的数据,如果查询不到则更新
        //更新部分数据
        final UpdateRequest upsert = new UpdateRequest("blog01", "article", "14")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "更新:数据")
                        .field("content", "更新:内容...")
                        .endObject())
                .upsert(source);
        UpdateResponse updateResponse = client.update(upsert).get();
        String index = updateResponse.getIndex();
        String type = updateResponse.getType();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        boolean fragment = updateResponse.isFragment();
        System.out.println("index:" + index + "\ntype:" + type + "\nid:" + id +
                "\nversion" + version + "\nfragment:" + fragment);
    }
    /**
     * 删除数据
     */
    @Test
    public void delectData() {
        final DeleteResponse deleteResponse = client.prepareDelete("blog01", "article", "4").get();
        System.out.println("删除是否成功:" + deleteResponse.status());

    }
    /**
     * 查询文档数据
     */
    @Test
    public void searchData() {
        //QueryString查询
        final SearchResponse searchResponse = client.prepareSearch("blog01")
                //可传多个
                .setTypes("article")
                .setQuery(QueryBuilders.queryStringQuery("引擎"))
                .get();
        //获取相应数据,获取命中次数
        SearchHits hits = searchResponse.getHits();
        System.out.println("查到" + hits.getTotalHits() + "条数据");
        //遍历查询结果
        for (SearchHit hit : hits) {
            //获取整条数据
            System.out.println(hit.getSourceAsString());
            //获取每个字段
            System.out.println("id:" + hit.getSourceAsMap().get("id"));
            System.out.println("title:" + hit.getSourceAsMap().get("title"));
            System.out.println("content:" + hit.getSourceAsMap().get("content"));
        }
        client.close();
    }
    /**
     * 创建一个索引
     */
    @Test
    public void creatIndex() {
        final CreateIndexResponse response = client.admin().indices().prepareCreate("blog02").get();
        System.out.println("是否创建:" + response.isShardsAcked());

        //client.admin().indices().prepareDelete("blog02").get();
    }
    /**
     *创建mapping信息
     */
    @Test
    public void createMapping() throws IOException, ExecutionException, InterruptedException {
        final XContentBuilder mappingBuilder = XContentFactory.jsonBuilder()
                .startObject()
                    .startObject("article")
                        .startObject("properties")
                            .startObject("id")
                                .field("type", "integer")
                                //true:分词数据尽量分到一个内存中
                                .field("store", "true")
                            .endObject()
                            .startObject("type")
                                .field("type", "text")
                                .field("store", "true")
                                .field("analyzer", "ik_max_word")
                            .endObject()
                            .startObject("content")
                                .field("type", "text")
                                .field("store", "true")
                                .field("analyzer", "ik_max_word")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();
        //将制定的mapping进行封装
        PutMappingRequest source = Requests.putMappingRequest("blog02")
                .type("article")
                .source(mappingBuilder);
        //开始制定mapping
        client.admin().indices().putMapping(source).get();
        client.close();
    }
    /**
     * 各种查询
     */
    @Test
    public void multiSearchDoc() {
        //QueryString查询
        final SearchResponse searchResponse1 = client.prepareSearch("blog01")
                //可传多个
                .setTypes("article")
                .setQuery(QueryBuilders.queryStringQuery("数"))
                .get();

        //词条查询
        final SearchResponse searchResponse2 = client.prepareSearch("blog02")
                .setTypes("article")
                .setQuery(QueryBuilders.termQuery("content", "引擎"))
                .get();

        //通配符查询 * ?
        final SearchResponse searchResponse3 = client.prepareSearch("blog02")
                .setTypes("article")
                .setQuery(QueryBuilders.wildcardQuery("content", "数?"))
                .get();

        //模糊查询
        final SearchResponse searchResponse4 = client.prepareSearch("blog02")
                .setTypes("article")
                .setQuery(QueryBuilders.fuzzyQuery("content", "数"))
                .get();

        //解析查询,可以指定字段
        final SearchResponse searchResponse5 = client.prepareSearch("blog02")
                .setTypes("article")
                //指定字段
                //.setQuery(QueryBuilders.queryStringQuery("引擎")).fields("title")
                .setQuery(QueryBuilders.queryStringQuery("引擎")).fields("content")
                .get();

        //范围查询
        final SearchResponse searchResponse6 = client.prepareSearch("blog02")
                .setTypes("article")
                .setQuery(QueryBuilders.rangeQuery("id")
                        .gte(1).lt(3))
                .get();

        //获取相应数据,获取命中次数
        SearchHits hits = searchResponse6.getHits();
        System.out.println("查到" + hits.getTotalHits() + "条数据");
        //遍历查询结果
        for (SearchHit hit : hits) {
            //获取整条数据
            System.out.println(hit.getSourceAsString());
            //获取每个字段
            System.out.println("id:" + hit.getSourceAsMap().get("id"));
            System.out.println("title:" + hit.getSourceAsMap().get("title"));
            System.out.println("content:" + hit.getSourceAsMap().get("content"));
        }
        client.close();
    }
}
