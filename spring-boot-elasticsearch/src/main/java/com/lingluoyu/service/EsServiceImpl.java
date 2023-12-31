package com.lingluoyu.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EsServiceImpl extends BaseEsService implements EsService {
    @Override
    public boolean checkIndexExists(String index) {
        return checkIndexExistsRequest(index);
    }

    @Override
    public void createIndex(String index) {
        createIndexRequest(index);
    }

    @Override
    public void deleteIndex(String index) {
        deleteIndexRequest(index);
    }

    @Override
    public void reindex(String sourceIndex, String targetIndex) {
        // 如果目标索引不存在，创建新的索引
        if (!checkIndexExists(targetIndex)) {
            createIndexRequest(targetIndex);
        }
        // 把已有的数据复制到新的索引
        // 更新新的索引的数据
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices(sourceIndex);
        request.setDestIndex(targetIndex);
        // 设置版本冲突时继续
        request.setConflicts("proceed");
        // 调用reindex后刷新索引
        request.setRefresh(true);

        ActionListener<BulkByScrollResponse> listener = new ActionListener<BulkByScrollResponse>() {
            @Override
            public void onResponse(BulkByScrollResponse bulkResponse) {
                log.info("reindex success. {}", bulkResponse.getTotal());
            }

            @Override
            public void onFailure(Exception e) {
                log.error("reindex failed. ", e);
            }
        };

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] includes = {};
        String[] excludes = {"isbnNo"};
        searchSourceBuilder.fetchSource(includes, excludes);

        SearchRequest searchRequest = request.getSearchRequest();
        searchRequest.source(searchSourceBuilder);
        // 异步reindex
        client.reindexAsync(request, COMMON_OPTIONS, listener);
    }
}
