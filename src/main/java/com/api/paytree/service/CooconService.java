package com.api.paytree.service;

import com.api.paytree.dto.CooconDto.*;
import com.api.paytree.dto.SearchDto.*;
import com.api.paytree.exception.ClientException;
import com.api.paytree.mapper.CooconMapper;
import com.api.paytree.utils.ErrorCode;
import com.api.paytree.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CooconService {
    @Autowired
    CooconMapper cooconMapper;

    public CooconList getCooconList(Search search) {
        List<Coocon> cooconList;

        try {
            search.setOffset(Helper.calculateOffset(search.getPage(), search.getRows()));

            cooconList = Optional.of(cooconMapper.getCooconList(search))
                                 .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new CooconList(cooconList);
    }

    public CooconDetail getCooconDetail(String virtualId) {
        Coocon info;
        List<Bulk> bulkList;

        try {
            info = Optional.of(cooconMapper.getCooconDetail(virtualId))
                           .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER));

            bulkList = Optional.of(cooconMapper.getBulkList(virtualId))
                               .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER));
        } catch (Exception e) {
            throw new ClientException(ErrorCode.SERVER_ERROR);
        }

        return new CooconDetail(info, bulkList);
    }
}