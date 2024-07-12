package com.api.paytree.mapper;

import com.api.paytree.dto.CooconDto.*;
import com.api.paytree.dto.SearchDto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CooconMapper {
    List<Coocon> getCooconList(Search search);

    Coocon getCooconDetail(String virtualId);

    List<Bulk> getBulkList(String virtualId);
}