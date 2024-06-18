package com.api.paytree.mapper;

import com.api.paytree.dto.AgencyDto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyMapper {
    List<Distributor> getDistributorListFilter();

    List<Agency> getAgencyList(Search search);

    AgencyDetail getAgencyDetail(String agencyId);

    int insertAgency(AgencyDetail agencyDetail);

    List<Agency> getBelongAgencyList(String code);

    int updateAgency(AgencyDetail agencyDetail);
}