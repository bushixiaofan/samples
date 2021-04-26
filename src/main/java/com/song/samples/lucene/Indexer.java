package com.song.samples.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: songzeqi
 * @Date: 2019-08-23 4:57 PM
 */

public class Indexer {
    // 创建索引存储位置
    private static final String INDEX_LOCATION = "/Users/songzeqi/lucene";
    // 索引文件的目录
    private static final String INDEX_DIR = "/Users/songzeqi/Notes";
    // 设置lucene域名
    private static final String FIELD_CONTENT = "content";
    // 设置lucene域名
    private static final String FIELD_NAME = "name";


    public static void index() throws IOException {
        //这里注意lucene8.0.0    FSDirectory.open() 内的参数是path类型

        // 打开lucene的存储目录
        Directory dirLucene = FSDirectory.open(Paths.get(INDEX_LOCATION));

        // 构建索引
        //注意这里一定要进行配置分词器，默认的分词器是按照英文设置的，这里采用的版本号默认是8.0.0
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        try (IndexWriter indexWriter = new IndexWriter(dirLucene, config)) {

            //创建文档
            Document document = new Document();
            //向文档中添加域
            document.add(new TextField(FIELD_CONTENT, "hello world", Field.Store.YES));

            Document document1 = new Document();
            document1.add(new StoredField(FIELD_CONTENT, "hello china"));

            Document document2 = new Document();
            document2.add(new TextField(FIELD_CONTENT, " I love you", Field.Store.YES));

            Document document3 = new Document();
            document3.add(new TextField(FIELD_CONTENT, "hello zhao ", Field.Store.NO));

            //将文档添加到本地索引中
            indexWriter.addDocument(document);
            indexWriter.addDocument(document1);
            indexWriter.addDocument(document2);
            indexWriter.addDocument(document3);

            indexWriter.commit();
        }
    }

    public static void indexDir(File indexDir) {
        if (!indexDir.isDirectory()) {
            return;
        }
    }

    public static void search(String item) throws IOException {
        //获取lucene本地存储路径
        Directory dir = FSDirectory.open(Paths.get(INDEX_LOCATION));
        //获取索引读取器
        IndexReader indexReader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //读取查询关键词
        TermQuery query = new TermQuery(new Term(FIELD_CONTENT, item));
        //获取前10返回结果
        TopDocs topN = indexSearcher.search(query, 10);

        for (int i = 0; i < topN.totalHits; i++) {
            Document doc = indexSearcher.doc(topN.scoreDocs[i].doc);

            System.out.println("doc" + i + ":\"" + doc.get(FIELD_CONTENT) + "\"");
            System.out.println("score: " + topN.scoreDocs[i].score + "\"");
        }
    }

    public static void main(String[] args) throws IOException {
        //索引只用创建一次
        index();

        search("world");

        search("hello");
    }
}
